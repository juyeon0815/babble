<template>
  <h3>Room title <i class="el-icon-user-solid"></i> 0명</h3>
  <h3>{{ conferenceId }}</h3>
  <!-- <div class="temp">
    <div class="dummy"></div>
    <div class="dummy"></div>
  </div>
  <div class="temp">
    <div class="dummy"></div>
    <div class="dummy"></div>
  </div> -->

  <!-- bootstrap -> element plus 예정(grid/style) -->
  <div id="main-video" class="col-md-6">
    <UserVideo :streamManager="state.mainStreamManager" />
  </div>
  <div id="video-container" class="col-md-6">
    <UserVideo
      :stream-manager="state.publisher"
      @click="updateMainVideoStreamManager(publisher)"
    />
    <UserVideo
      v-for="sub in state.subscribers"
      :key="sub.stream.connection.connectionId"
      :stream-manager="sub"
      @click="updateMainVideoStreamManager(sub)"
    />
  </div>

  <div class="nav-icons">
    <el-button-group>
      <el-button type="info" plain>
        <i class="el-icon-microphone"></i
      ></el-button>
      <el-button type="info" plain>
        <i class="el-icon-video-camera"></i
      ></el-button>
      <el-button type="info" plain> <i class="el-icon-thumb"></i></el-button>
      <el-button type="info" plain> <i class="el-icon-star-on"></i></el-button>
      <el-button type="info" plain>
        <i class="el-icon-error" @click="leaveSession"></i
      ></el-button>
    </el-button-group>
  </div>
</template>

<script>
import { reactive, onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./user-video";
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io" + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "video-space",
  props: {
    conferenceId: {
      type: Number
    }
  },
  components: {
    UserVideo
  },
  setup(props) {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      myUserName: "익명의" + "너구리", // DB 동물이름으로 교체
      mySessionId: store.getters["root/getRoomID"]
    });

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.OV = new OpenVidu();
      state.session = state.OV.initSession();

      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        state.subscribers.push(subscriber);
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

      // token
      // const getToken = async function (mySessionId) {
      //   const sessionId = await store.dispatch('root/requestOVSession', mySessionId)
      //   return await store.dispatch('root/requestOVToken', sessionId)
      // }

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
              {},
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
            console.log("############");
            console.log(token);
            // --- Get your own camera stream with the desired properties ---

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

            state.mainStreamManager = publisher;
            state.publisher = publisher;

            // --- Publish your stream ---

            state.session.publish(state.publisher);
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
    });

    const leaveSession = function() {
      console.log("leave");
      store.commit("root/setActiveCategory", null);
      store.commit("root/setMenuActive", 0);
      const MenuItems = store.getters["root/getMenus"];
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[0]
      });
    };

    const updateMainVideoStreamManager = function(stream) {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return { state, leaveSession, updateMainVideoStreamManager };
  }
};
</script>

<style>
.sideChat {
  background-color: lightgrey;
  min-height: 600px;
}

.nav-icons {
  margin-top: 10px;
  text-align: center;
}
.nav-icons i {
  font-size: 25px;
}

.temp {
  text-align: center;
}
.dummy {
  display: inline-block;
  margin: 5px;
  width: 300px;
  height: 250px;
  background-color: lightblue;
}
</style>
