<template>
  <el-dialog
    title="Ba:bble"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="45%"
  >
    <el-row class="description">
      <el-col :offset="1">
        <h2>방 제목 : {{ state.title }}</h2>
      </el-col>
      <el-col :offset="1">
        <h2>방 설명 : {{ state.content }}</h2>
      </el-col>
      <el-col :offset="1">
        <h2>방송 시작 시간 : {{ state.createTime }}</h2>
      </el-col>
      <el-col v-if="state.isLoggedin" class="instruction">
        <p>아래와 같은 화면으로 방에 입장될 예정입니다.</p>
        <el-button-group class="btn-group">
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
        </el-button-group>
      </el-col>

      <el-col v-if="state.isLoggedin" :offset="3" :span="18">
        <div id="video-container" class="col-md-6">
          <UserVideo
            :stream-manager="state.publisher"
            :profile="state.profile"
          />
          <!-- <UserVideo :stream-manager="state.publisher" /> -->
        </div>
      </el-col>
      <el-col v-else :offset="3" :span="18" style="text-align : center">
        <img
          src="https://images.vexels.com/media/users/3/134988/isolated/preview/33601d079a5f05c6ec5b34b277be201b-camera-off-button-circle-icon-by-vexels.png"
          style="width : 50%"
        />
        <h3 style="color:#F56C6C">비회원은 관전자 모드로 입장합니다.</h3>
      </el-col>
      <el-col style="text-align:center">
        <el-button type="primary" plain @click="clickEnterRoom">
          방 입장하기
        </el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
import { reactive, computed, onMounted, onUnmounted, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../../conferences/components/user-video.vue";
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "BABBLE";

export default {
  name: "conference-dialog",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    roomId: {
      type: Number
    }
  },
  components: {
    UserVideo
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      dialogVisible: computed(() => props.open),
      title: "",
      content: "",
      createTime: "",

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      videoStatus: true,
      audioStatus: true,
      isLoggedin: computed(() => {
        return store.getters["auth/getToken"];
      }),
      profile: []
    });

    watch(
      () => state.dialogVisible,
      (dialogVisible, prev) => {
        state.videoStatus = true;
        state.audioStatus = true;
        if (state.dialogVisible) {
          const payload = {
            roomId: props.roomId
          };
          store
            .dispatch("root/requestRoomDialog", payload)
            .then(function(result) {
              state.title = result.data.title;
              state.content = result.data.content;
              state.createTime =
                result.data.createTime.slice(0, 10) +
                " " +
                result.data.createTime.slice(11, 19);
            });

          if (state.isLoggedin) {
            state.OV = new OpenVidu();
            state.session = state.OV.initSession();

            state.session.on("exception", ({ exception }) => {
              console.warn(exception);
            });

            const createSession = function() {
              return new Promise((resolve, reject) => {
                axios
                  .post(
                    `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
                    JSON.stringify({}),
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
                      resolve(error);
                    } else {
                      console.warn(
                        `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
                      );
                      if (
                        window.confirm(
                          `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                        )
                      ) {
                        location.assign(
                          `${OPENVIDU_SERVER_URL}/accept-certificate`
                        );
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
                  .catch(error =>
                    reject("createTokenError!!!!!!" + error.response)
                  );
              });
            };

            const getToken = function(mySessionId) {
              return createSession(mySessionId).then(sessionId =>
                createToken(sessionId)
              );
            };

            // 방 생성시 호출되지 않음.
            getToken().then(token => {
              state.session
                .connect(token, { clientData: state.myUserName })
                .then(() => {
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
                });
            });
          }
        }
      }
    );

    // 페이지 진입시 불리는 훅
    onMounted(() => {});

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      // 반영되는지 확인, 안될경우 leaveSession으로 통합
    });

    const clickEnterRoom = function() {
      const payload = {
        email: store.getters["auth/getEmail"],
        roomId: props.roomId
      };
      store.dispatch("root/requestRoomEnter", payload);
      store.commit("root/setIsHost", false);
      handleClose();
      router.push({
        name: "conference-detail",
        params: {
          conferenceId: props.roomId
        }
      });
    };

    const handleClose = function() {
      if (state.session) state.session.disconnect();
      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      emit("closeConferenceDialog");
    };

    const onOffVideo = function() {
      if (state.videoStatus) {
        state.publisher.publishVideo(false);
        state.videoStatus = false;
        state.profile = { url: require("@/assets/images/icon.png") };
        store.commit("root/setUserVideoStatus", false);
      } else {
        state.publisher.publishVideo(true);
        state.videoStatus = true;
        store.commit("root/setUserVideoStatus", true);
      }
    };

    const onOffAudio = function() {
      if (state.audioStatus) {
        state.publisher.publishAudio(false);
        state.audioStatus = false;
        store.commit("root/setUserAudioStatus", false);
      } else {
        state.publisher.publishAudio(true);
        state.audioStatus = true;
        store.commit("root/setUserAudioStatus", true);
      }
    };

    return { state, clickEnterRoom, handleClose, onOffVideo, onOffAudio };
  }
};
</script>

<style>
.description h2 {
  padding: 0;
  margin-top: 0;
}
.description p {
  font-size: 15px;
}
.instruction {
  display: flex;
  margin: 5px;
  justify-content: space-around;
  text-align: center;
}
.btn-group {
  margin-top: 5px;
}

@media screen and (max-width: 480px) {
  .description h2 {
    font-size: 15px;
    margin-left: 5%;
  }

  .instruction p {
    font-size: 12px;
    margin-left: 3%;
    width: 40%;
  }

  .instruction .btn-group {
    width: 35%;
  }
}
</style>
