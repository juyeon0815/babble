<template>
  <h3>
    {{ roomTitle }} <i class="el-icon-user-solid"></i>
    {{ state.subscribers.length + 1 }}명
  </h3>
  {{ state.maxViewers }}
  <div class="container">
    <!-- 1차) Main Video 제외 -->
    <!-- <div class="main-video">
      <UserVideo :streamManager="state.mainStreamManager" />
    </div> -->

    <div class="video-container" :class="state.videoGrid">
      <UserVideo
        :stream-manager="state.publisher"
        @click="updateMainVideoStreamManager(publisher)"
      />
      <UserVideo
        v-for="sub in state.subscribers"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        @click="unpublish(sub)"
      />
    </div>
  </div>

  <div class="nav-icons">
    <el-button-group>
      <el-button type="info" plain @click="onOffAudio">
        <i
          v-if="state.audioStatus"
          style="color :red"
          class="el-icon-microphone"
        />
        <i v-else class="el-icon-turn-off-microphone" />
      </el-button>
      <el-button type="info" plain @click="onOffVideo">
        <i
          v-if="state.videoStatus"
          style="color:red"
          class="el-icon-video-camera"
        />
        <i v-else type="danger" class="el-icon-video-camera" />
      </el-button>
      <el-button type="info" plain @click="findStreamIdBySessionId">
        <i class="el-icon-thumb"></i
      ></el-button>
      <el-button type="info" plain> <i class="el-icon-star-on"></i></el-button>
      <el-button type="info" plain @click="leaveSession">
        <i class="el-icon-error"></i
      ></el-button>
    </el-button-group>
  </div>
</template>

<script>
import { computed, reactive, onMounted, onUnmounted, watch } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./user-video";
import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io";
const OPENVIDU_SERVER_SECRET = "BABBLE";

export default {

  name: "video-space",
  props: {
    roomTitle: {
      type: String
    },
    hostId: {
      type: Number
    }
  },
  components: {
    UserVideo
  },
  setup(props) {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      OV: computed(() => store.getters["root/getOV"]),
      session: computed(() => store.getters["root/getSession"]),
      mainStreamManager: computed(
        () => store.getters["root/getMainStreamManager"]
      ),
      publisher: computed(() => store.getters["root/getPublisher"]),
      subscribers: computed(() => store.getters["root/getSubscribers"]),
      videoStatus: store.getters["root/getPublisher"],
      audioStatus: store.getters["root/getPublisher"],

      myUserName: computed(() => store.getters["root/getUserName"]), // DB 동물이름으로 교체
      mySessionId: store.getters["root/getRoomID"],
      myId: "",
      maxViewers: 1,
      // videoGrid: computed(() => store.getters["root/getSubscribers"]).length <= 3 ? 'less4':'more4'
      stompClient: null, // 추가
      isLoggedin: computed(() => {
        return store.getters["auth/getToken"];
      }), // 추가
   });

    watch(
      () => state.subscribers.length,
      (newCount, prev) => {
        if (state.maxViewers < newCount + 1) {
          state.maxViewers = newCount + 1;
        }
      }
    );

    // socket 연결
    // let socket = new SockJS("https://localhost:8443/ws")
    let socket = new SockJS("http://localhost:8080/ws")
    let authorization = state.isLoggedin;
    state.stompClient = Stomp.over(socket)
    console.log(">>>>>>>>>>>>> 토큰  ", authorization);
    state.stompClient.connect({authorization}, frame=>{
      console.log(">>>>>>>>>> video-space success", frame)
      state.stompClient.subscribe("/sub/emoji/"+ state.chatroomId, res=>{
        let jsonBody = JSON.parse(res.body)
        let m={
          'nickname': jsonBody.nickname,
          'img': jsonBody.img
        }
      })
    }, err=>{
      console.log("fail", err)
    })
    // 추가

    const getRandomName = function() {
      store
        .dispatch("root/requestRandomName")
        .then(result => {
          store.commit("root/setUserName", result.data);
        })
        .catch(err => {
          console.log(err);
        });
    };
    getRandomName();

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      // 새로고침 방지
      if (state.videoStatus === undefined) {
        state.videoStatus = true;
        state.audioStatus = true;
      } else {
        state.videoStatus =
          store.getters["root/getPublisher"].stream.videoActive;
        state.audioStatus =
          store.getters["root/getPublisher"].stream.audioActive;
      }

      store
        .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
        .then(function(result) {
          state.myId = result.data.id;
        });

      store.commit("root/setMenuActive", -1);

      store.commit("root/setOV", new OpenVidu());

      store.commit("root/setSession", state.OV.initSession());

      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        // state.subscribers.push(subscriber);
        store.commit("root/setSubscribers", subscriber);
      });

      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });

      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      const createSession = function(sessionId) {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
              JSON.stringify({
                customSessionId: sessionId
              }),
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET
                }
              }
            )
            .then(response => response.data)
            .then(data => resolve(data.id))
            .catch(error => {
              if (error.response.status === 409) {
                resolve(sessionId);
              } else {
                console.warn(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
                );
                if (
                  window.confirm(
                    `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                  )
                ) {
                  location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                }
                reject("createSessionError!!!!!!" + error.response);
              }
            });
        });
      };

      const createToken = function(sessionId) {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
              {
                role: "MODERATOR"
              },
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET
                }
              }
            )
            .then(response => response.data)
            .then(data => resolve(data.token))
            .catch(error => reject("createTokenError!!!!!!" + error.response));
        });
      };

      const getToken = function(mySessionId) {
        return createSession(mySessionId).then(sessionId =>
          createToken(sessionId)
        );
      };

      getToken(state.mySessionId).then(token => {
        state.session
          .connect(token, { clientData: state.myUserName })
          .then(() => {
            if (state.publisher === undefined) {
              let publisher = state.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false // Whether to mirror your local video or not
              });

              // state.mainStreamManager = publisher;
              store.commit("root/setMainStreamManager", publisher);

              // state.publisher = publisher;
              store.commit("root/setPublisher", publisher);

              // --- Publish your stream ---
              state.session.publish(publisher);
            } else {
              state.session.publish(state.publisher);
            }
          })
          .catch(error => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      // 반영되는지 확인, 안될경우 leaveSession으로 통합
      if (state.session) state.session.disconnect();
      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;

      store.commit("root/setSession", undefined);
      store.commit("root/setMainStreamManager", undefined);
      store.commit("root/setPublisher", undefined);
      store.commit("root/setClearSubscribers", []);
      store.commit("root/setOV", undefined);
    });

    const leaveSession = function() {
      // 호스트일 경우 방 삭제(max 보내기)
      if (props.hostId == state.myId) {
        const payload = {
          roomId: state.mySessionId,
          maxViewers: state.maxViewers
        };
        store.dispatch("root/requestRoomDelete", payload);
      } else {
        const payload = {
          email: store.getters["auth/getEmail"],
          roomId: state.mySessionId
        };
        store.dispatch("root/requestRoomExit", payload);
      }

      store.commit("root/setActiveCategory", null);
      store.commit("root/setMenuActive", 0);
      const MenuItems = store.getters["root/getMenus"];
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[0]
      });
    };

    //메인 화면으로 옮기기 // 아직 비활성화임.
    const updateMainVideoStreamManager = function(stream) {
      // if (state.mainStreamManager === stream) return;
      // state.mainStreamManager = stream;
      console.log(stream);
    };

    const onOffVideo = function() {
      if (state.videoStatus) {
        state.publisher.publishVideo(false);
        state.videoStatus = false;
      } else {
        state.publisher.publishVideo(true);
        state.videoStatus = true;
      }
    };

    const onOffAudio = function() {
      if (state.audioStatus) {
        state.publisher.publishAudio(false);
        state.audioStatus = false;
      } else {
        state.publisher.publishAudio(true);
        state.audioStatus = true;
      }
    };

    // 강퇴
    const unpublish = function(stream) {
      let cId = stream.stream.connection.connectionId;
      axios
        .delete(
          `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}/connection/${cId}`,
          {
            auth: {
              username: "OPENVIDUAPP",
              password: OPENVIDU_SERVER_SECRET
            }
          }
        )
        .then(response => console.log(response))
        .catch(error => console.log(error));
    };

    // 권한 수정
    const patchRole = function(stream) {
      let cId = stream.stream.connection.connectionId;
      axios
        .patch(
          `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}/connection/${cId}`,
          {
            role: "SUBSCRIBER",
            record: false
          },
          {
            auth: {
              username: "OPENVIDUAPP",
              password: OPENVIDU_SERVER_SECRET
            }
          }
        )
        .then(response => console.log(response))
        .then(data => console.log(data))
        .catch(error => console.log(error));
    };

    return {
      state,
      leaveSession,
      updateMainVideoStreamManager,
      onOffVideo,
      onOffAudio,
      unpublish,
      patchRole,
      getRandomName
    };
  }
};
</script>

<style>
.nav-icons {
  margin-top: 10px;
  text-align: center;
}
.nav-icons i {
  font-size: 25px;
}

.container {
  display: flex;
  justify-content: center;
  /* display: grid;
  height: 80vh; */
}
/* .less4 {
  grid-template-columns: 1fr 1fr;
	grid-template-rows: 1fr 1fr;
  gap: 20px;
}
.more4 {
  grid-template-columns: 1fr 1fr 1fr 1fr;
	grid-template-rows: 1fr 1fr 1fr 1fr;
  gap: 10px;
} */
</style>
