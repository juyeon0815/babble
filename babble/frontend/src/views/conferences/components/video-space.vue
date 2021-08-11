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
        v-if="state.publisher"
        :stream-manager="state.publisher"
        :id="state.publisher.stream.connection.connectionId"
        @click="updateMainVideoStreamManager(publisher)"
      />
      <UserVideo
        v-for="sub in state.subscribers"
        :id="sub.stream.connection.connectionId"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        style="border:sold"
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
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: computed(() => store.getters["root/getSubscribers"]),
      videoStatus: computed(() => store.getters["root/getUserVideoStatus"]),
      audioStatus: computed(() => store.getters["root/getUserAudioStatus"]),

      myUserName: computed(() => store.getters["root/getUserName"]), // DB 동물이름으로 교체
      mySessionId: store.getters["root/getRoomID"],
      myId: "",

      maxViewers: 1
      // videoGrid: computed(() => store.getters["root/getSubscribers"]).length <= 3 ? 'less4':'more4'
    });

    watch(
      () => state.subscribers.length,
      (newCount, prev) => {
        if (state.maxViewers < newCount + 1) {
          state.maxViewers = newCount + 1;
        }
      }
    );

    const getRandomName = async function() {
      await store
        .dispatch("root/requestRandomName")
        .then(result => {
          store.commit("root/setUserName", result.data);
        })
        .catch(err => {
          store.commit("root/setUserName", "요상한 놈");
        });
    };

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      getRandomName();
      store
        .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
        .then(function(result) {
          state.myId = result.data.id;
        });

      store.commit("root/setMenuActive", -1);
      state.OV = new OpenVidu();

      // 음성감지 초기 설정
      state.OV.setAdvancedConfiguration({
        publisherSpeakingEventsOptions: {
          interval: 50, // Frequency of the polling of audio streams in ms (default 100)
          threshold: -50 // Threshold volume in dB (default -50)
        }
      });
      state.session = state.OV.initSession();

      // 스트림이 생성 되었을 때 -> 기존 참가자 정보 받아오기.
      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        store.commit("root/setSubscribers", subscriber);
      });

      // 누군가 나갈 때
      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        console.log("나가~");
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });

      // 강퇴 당했을 때
      state.session.on("sessionDisconnected", ({ stream }) => {
        console.log("강티당함..");
        const MenuItems = store.getters["root/getMenus"];
        let keys = Object.keys(MenuItems);
        router.push({
          name: keys[0]
        });
      });

      // 누군가의 음성이 감지되었을 때
      state.session.on("publisherStartSpeaking", event => {
        if (document.querySelector(`#${event.connection.connectionId}`)) {
          document.querySelector(
            `#${event.connection.connectionId}`
          ).style.border = "solid";
        }
      });

      // 누군가의 음성이 멈췄을 때
      state.session.on("publisherStopSpeaking", event => {
        if (document.querySelector(`#${event.connection.connectionId}`)) {
          document.querySelector(
            `#${event.connection.connectionId}`
          ).style.border = "none";
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
            // 새로 들어온 참가자
            if (state.publisher === undefined) {
              let publisher = state.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: state.audioStatus, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: state.videoStatus, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false // Whether to mirror your local video or not
              });

              state.mainStreamManager = publisher;
              state.publisher = publisher;

              store.commit("root/setPublisher", publisher);

              console.log("%%%%%%%%%%%%%");
              console.log(state.session);
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
      state.OV = undefined;

      // vueX 초기화
      store.commit("root/setUserVideoStatus", true);
      store.commit("root/setUserAudioStatus", true);
      store.commit("root/setClearSubscribers", []);
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

    // 내 영상 끄기
    const onOffVideo = function() {
      if (state.videoStatus) {
        state.publisher.publishVideo(false);
        store.commit("root/setUserVideoStatus", false);
      } else {
        state.publisher.publishVideo(true);
        store.commit("root/setUserVideoStatus", true);
      }
    };

    // 내 음성 끄기
    const onOffAudio = function() {
      if (state.audioStatus) {
        state.publisher.publishAudio(false);
        store.commit("root/setUserAudioStatus", false);
      } else {
        state.publisher.publishAudio(true);
        store.commit("root/setUserAudioStatus", true);
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

    const videoFilter = function() {
      state.publisher.stream
        .applyFilter("GStreamerFilter", {
          command: "streaktv"
        })
        .then(() => {
          console.log("Video rotated!");
        })
        .catch(error => {
          console.error(error);
        });
    };

    return {
      state,
      leaveSession,
      updateMainVideoStreamManager,
      onOffVideo,
      onOffAudio,
      unpublish,
      patchRole,
      getRandomName,
      videoFilter
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
