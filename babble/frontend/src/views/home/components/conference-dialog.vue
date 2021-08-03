<template>
  <el-dialog title="Ba:bble" v-model="state.dialogVisible" @close="handleClose">
    <el-row class="description">
      <el-col :offset="1"
        ><h2>방 제목 : {{ state.title }}</h2></el-col
      >
      <el-col :offset="1"
        ><h2>방 설명 : {{ state.content }}</h2></el-col
      >
      <el-col :offset="1"
        ><h2>방송 시작 시간 : {{ state.createTime }}</h2></el-col
      >
      <el-col :offset="1"
        ><p>아래와 같은 화면으로 방에 입장될 예정입니다.</p></el-col
      >

      <el-col :offset="3" :span="18">
        <div id="video-container" class="col-md-6">
          <UserVideo :stream-manager="state.publisher" /></div
      ></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickEnterRoom"
          >방 입장하기</el-button
        >
      </span>
    </template>
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

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io" + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

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
      audioStatus: true
    });

    watch(
      () => state.dialogVisible,
      (dialogVisible, prev) => {
        if (state.dialogVisible) {
          const payload = {
            roomId: props.roomId
          };
          store.commit("root/startSpinner");
          store
            .dispatch("root/requestRoomDialog", payload)
            .then(function(result) {
              state.title = result.data.title;
              state.content = result.data.content;
              state.createTime =
                result.data.createTime.slice(0, 10) +
                " " +
                result.data.createTime.slice(11, 19);
              store.commit("root/endSpinner");
            })
            .catch(function(err) {
              store.commit("root/endSpinner");
              alert(err + "!!!!!!");
            });

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

          getToken().then(token => {
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
      handleClose();
      router.push({
        name: "conference-detail",
        params: {
          conferenceId: props.roomId
        }
      });
    };

    const handleClose = function() {
      console.log("CLOSE DIALOG");
      if (state.session) state.session.disconnect();
      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      emit("closeConferenceDialog");
    };

    return { state, clickEnterRoom, handleClose };
  }
};
</script>

<style>
.testCam {
  border-radius: 4px;
  min-height: 300px;
  background-color: grey;
}
.description h2 {
  padding: 0;
  margin-top: 0;
}
</style>
