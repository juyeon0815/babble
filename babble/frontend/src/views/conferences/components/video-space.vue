<template>
  <h3>
    {{ roomTitle }} <i class="el-icon-user-solid"></i>
    {{ state.subscribers.length + 1 }}
  </h3>

  <div v-if="!state.showMainVideo">
    <!-- 1인 -->
    <div v-if="state.videoGrid == 'alone'" class="video-container less2">
      <UserVideo
        v-if="state.publisher"
        :stream-manager="state.publisher"
        :isMe="true"
        :grid-count="state.videoGrid"
        :profile="state.profile"
        :id="state.publisher.stream.connection.connectionId"
        @toMain="updateMainVideoStreamManager(state.publisher)"
      />
    </div>
    <!-- 2인 -->
    <div v-if="state.videoGrid == 'less2'" class="video-container less2">
      <el-row>
        <el-col :span="12">
          <UserVideo
            v-if="state.publisher"
            :stream-manager="state.publisher"
            :isMe="true"
            :profile="state.profile"
            :id="state.publisher.stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.publisher)"
          />
          <div v-if="!state.isLoggedin" class="nologin-video">
            <img
              class="nologin-profile"
              :src="require('@/assets/images/default_profile.png')"
            />
            <p>비회원으로 방에 참여중입니다.</p>
          </div>
        </el-col>
        <el-col :span="12" v-if="state.subscribers.length != 0">
          <UserVideo
            :stream-manager="state.subscribers[0]"
            :isMe="false"
            :profile="state.profile"
            :id="state.subscribers[0].stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.subscribers[0])"
            @unpublishMe="unpublish(state.subscribers[0])"
          />
        </el-col>
      </el-row>
    </div>
    <!-- 3-4인 -->
    <div v-if="state.videoGrid == 'less4'" class="video-container">
      <el-row class="video-row">
        <el-col :offset="3" :span="8">
          <UserVideo
            v-if="state.publisher"
            :isMe="true"
            :stream-manager="state.publisher"
            :profile="state.profile"
            :id="state.publisher.stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.publisher)"
          />
          <div v-if="!state.isLoggedin" class="nologin-video">
            <img
              class="nologin-profile"
              :src="require('@/assets/images/default_profile.png')"
            />
            <p>비회원으로 방에 참여중입니다.</p>
          </div>
        </el-col>
        <el-col :offset="1" :span="8">
          <UserVideo
            :stream-manager="state.subscribers[0]"
            :isMe="false"
            :profile="state.profile"
            :id="state.subscribers[0].stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.subscribers[0])"
            @unpublishMe="unpublish(state.subscribers[0])"
          />
        </el-col>
      </el-row>
      <el-row class="video-row">
        <el-col :offset="3" :span="8">
          <UserVideo
            :stream-manager="state.subscribers[1]"
            :isMe="false"
            :profile="state.profile"
            :id="state.subscribers[1].stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.subscribers[1])"
            @unpublishMe="unpublish(state.subscribers[1])"
          />
        </el-col>
        <el-col :offset="1" :span="8">
          <UserVideo
            v-if="state.subscribers.length == 3"
            :stream-manager="state.subscribers[2]"
            :isMe="false"
            :profile="state.profile"
            :id="state.subscribers[2].stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.subscribers[2])"
            @unpublishMe="unpublish(state.subscribers[2])"
          />
        </el-col>
      </el-row>
    </div>
    <!-- 5-6인 -->
    <div v-if="state.videoGrid == 'less6'" class="video-container">
      <el-row class="video-row">
        <el-col :span="8">
          <UserVideo
            v-if="state.publisher"
            :stream-manager="state.publisher"
            :isMe="true"
            :profile="state.profile"
            :id="state.publisher.stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(state.publisher)"
          />
          <div v-if="!state.isLoggedin" class="nologin-video">
            <img
              class="nologin-profile"
              :src="require('@/assets/images/default_profile.png')"
            />
            <p>비회원으로 방에 참여중입니다.</p>
          </div>
        </el-col>
        <el-col
          :span="8"
          v-for="sub in state.subscribers.slice(0, 2)"
          :key="sub.stream.connection.connectionId"
        >
          <UserVideo
            :stream-manager="sub"
            :isMe="false"
            :profile="state.profile"
            :id="sub.stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(sub)"
            @unpublishMe="unpublish(sub)"
          />
        </el-col>
      </el-row>
      <el-row class="video-row">
        <el-col
          :span="8"
          v-for="sub in state.subscribers.slice(2)"
          :key="sub.stream.connection.connectionId"
        >
          <UserVideo
            :stream-manager="sub"
            :isMe="false"
            :profile="state.profile"
            :id="sub.stream.connection.connectionId"
            @toMain="updateMainVideoStreamManager(sub)"
            @unpublishMe="unpublish(sub)"
          />
        </el-col>
      </el-row>
    </div>
    <!-- 6인 이상 추가 예정 -->
    <div
      v-if="state.videoGrid == 'more6'"
      class="video-container"
      style="display:flex; flex-wrap:wrap"
    >
      <UserVideo
        v-if="state.publisher"
        :stream-manager="state.publisher"
        :profile="state.profile"
        :isMe="true"
        @toMain="updateMainVideoStreamManager(state.publisher)"
        style="width:20vw"
      />
      <UserVideo
        v-for="sub in state.subscribers"
        :key="sub.stream.connection.connectionId"
        :isMe="false"
        :profile="state.profile"
        :stream-manager="sub"
        @toMain="updateMainVideoStreamManager(sub)"
        @unpublishMe="unpublish(sub)"
        style="width:20vw"
      />
    </div>
  </div>

  <!-- 메인 비디오 크게보기  -->
  <div v-if="state.showMainVideo" class="video-container main-video">
    <el-row align="middle">
      <el-col :span="15">
        <UserVideo
          :key="state.mainStreamManager.stream.connection.connectionId"
          :streamManager="state.mainStreamManager"
          :isMe="true"
          :id="state.mainStreamManager.stream.connection.connectionId"
          @click="state.showMainVideo = false"
        />
      </el-col>
      <el-col :offset="1" :span="8" class="video-scroll">
        <UserVideo
          :key="state.publisher.stream.connection.connectionId"
          :stream-manager="state.publisher"
          :isMe="true"
          :id="state.publisher.stream.connection.connectionId"
          @toMain="updateMainVideoStreamManager(state.publisher)"
        />
        <UserVideo
          v-for="sub in state.subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          :isMe="false"
          :id="sub.stream.connection.connectionId"
          @toMain="updateMainVideoStreamManager(sub)"
          @unpublishMe="unpublish(sub)"
        />
      </el-col>
    </el-row>
  </div>

  <!-- 버튼 -->
  <div v-if="state.isLoggedin" class="nav-icons">
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
        <i v-else class="el-icon-video-camera" />
      </el-button>
      <el-popover
        class="emoji-balloon"
        :width="280"
        placement="top-start"
        trigger="click"
        :visible="state.visible"
      >
        <template #reference>
          <el-button type="info" plain @click="state.visible = !state.visible">
            <i
              v-if="state.visible"
              style="color:yellow"
              class="el-icon-star-on"
            />
            <i v-else class="el-icon-star-on" />
          </el-button>
        </template>
        <div class="emoji-row">
          <button class="btn" @click="clickLike">
            <img
              class="small like"
              :src="require('@/assets/images/emoji_like.png')"
            />
          </button>
          <button class="btn" @click="clickJoy">
            <img
              class="small joy"
              :src="require('@/assets/images/emoji_joy.png')"
            />
          </button>
          <button class="btn" @click="clickWow">
            <img
              class="medium wow"
              :src="require('@/assets/images/emoji_wow.png')"
            />
          </button>
          <button class="btn heart-btn" @click="clickHeart">
            <img
              class="small heart"
              :src="require('@/assets/images/emoji_heart.png')"
            />
          </button>
          <button class="btn" @click="clickSad">
            <img
              class="medium sad"
              :src="require('@/assets/images/emoji_sad.png')"
            />
          </button>
        </div>
      </el-popover>
      <el-button type="info" plain @click="leaveSession">
        <i class="el-icon-error"></i>
      </el-button>
    </el-button-group>
  </div>
  <div v-else class="nav-icons">
    <el-button type="info" plain @click="leaveSession">
      <i class="el-icon-error"></i>
    </el-button>
  </div>
  <div class="emojilog" id="emojis">
    <div v-for="(e, idx) in state.prevEmoji" :key="idx">
      <div class="emoji-bubble">
        <div class="circle"><img :class="e.style" :src="e.img" /></div>
        <p class="nickname">
          <span class="text">{{ e.nickname }}</span>
        </p>
        <!-- <span class="nickname"><p class="text">{{ e.nickname }}</p></span> -->
      </div>
    </div>
  </div>
</template>

<script>
import {
  computed,
  reactive,
  onMounted,
  onUnmounted,
  watch,
  callWithAsyncErrorHandling
} from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./user-video";
import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i5a308.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "BABBLE";

export default {
  name: "video-space",
  props: {
    roomTitle: {
      type: String
    }
  },
  components: {
    UserVideo
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: computed(
        () => store.getters["root/getMainStreamManager"]
      ),
      publisher: undefined,
      subscribers: computed(() => store.getters["root/getSubscribers"]),
      videoStatus: computed(() => store.getters["root/getUserVideoStatus"]),
      audioStatus: computed(() => store.getters["root/getUserAudioStatus"]),

      isHost: computed(() => store.getters["root/getIsHost"]),
      myUserName: computed(() => store.getters["root/getUserName"]), // DB 동물이름으로 교체
      mySessionId: store.getters["root/getRoomID"],
      maxViewers: 1,

      videoGrid: "alone",
      showMainVideo: false,

      visible: false,
      emoji: "",
      prevEmoji: [],
      stompClient: null,
      isLoggedin: computed(() => {
        return store.getters["auth/getToken"];
      }),
      profile: ""
    });

    watch(
      () => state.subscribers.length,
      (newCount, prev) => {
        // maxViewers
        if (state.maxViewers < newCount + 1) {
          state.maxViewers = newCount + 1;
        }
        // videoGrid
        if (newCount == 0) {
          state.videoGrid = "alone";
        } else if (newCount == 1) {
          state.videoGrid = "less2";
        } else if (newCount >= 2 && newCount <= 3) {
          state.videoGrid = "less4";
        } else if (newCount >= 4 && newCount <= 5) {
          state.videoGrid = "less6";
        } else {
          state.videoGrid = "more6";
        }
      }
    );

    if (!state.videoStatus && state.isLoggedin) {
      state.profile = store.getters["auth/getProfile"];
    }

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      store
        .dispatch("root/requestRandomName")
        .then(result => {
          if (state.isHost) {
            store.commit("root/setUserName", result.data + "(호스트)");
          } else {
            store.commit("root/setUserName", result.data);
          }
        })
        .catch(err => {
          store.commit("root/setUserName", "요상한 놈");
        })
        .then(() => {
          store.commit("menu/setMenuActive", -1);
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
            if (index >= 0) {
              state.subscribers.splice(index, 1);
            }
          });

          // 강퇴 당했을 때
          state.session.on("sessionDisconnected", ({ stream }) => {
            const MenuItems = store.getters["menu/getMenus"];
            store.commit("menu/setActiveCategory", null);
            store.commit("menu/setMenuActive", 0);
            let keys = Object.keys(MenuItems);
            router.push({
              name: keys[0]
            });
          });

          // 누군가의 음성이 감지되었을 때
          state.session.on("publisherStartSpeaking", event => {
            if (document.querySelector(`#${event.connection.connectionId}`)) {
              document
                .querySelector(`#${event.connection.connectionId} .vid`)
                .classList.add("gradient-box");
              document
                .querySelector(`#${event.connection.connectionId} .subvid`)
                .classList.add("gradient-box");
            }
          });

          // 누군가의 음성이 멈췄을 때
          state.session.on("publisherStopSpeaking", event => {
            if (document.querySelector(`#${event.connection.connectionId}`)) {
              document
                .querySelector(`#${event.connection.connectionId} .vid`)
                .classList.remove("gradient-box");
              document
                .querySelector(`#${event.connection.connectionId} .subvid`)
                .classList.remove("gradient-box");
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
                      location.assign(
                        `${OPENVIDU_SERVER_URL}/accept-certificate`
                      );
                    }
                    reject("createSessionError!!!!!!" + error.response);
                  }
                });
            });
          };

          // 새로 들어온 참가자
          const createToken = function(sessionId) {
            let userRole = "SUBSCRIBER";
            if (state.isLoggedin) userRole = "PUBLISHER";
            return new Promise((resolve, reject) => {
              axios
                .post(
                  `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
                  {
                    role: userRole
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

          getToken(state.mySessionId).then(token => {
            state.session
              .connect(token, { clientData: state.myUserName })
              .then(() => {
                // 새로 들어온 참가자
                if (state.publisher === undefined && state.isLoggedin) {
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

                  // --- Publish your stream ---
                  state.session.publish(publisher);
                } else {
                  state.session.publish(state.publisher);
                }
              })
              .catch(error => {});
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
      if (state.isHost) {
        // 방장이 떠날 때
        swal({
          text: "호스트 퇴장 시 방이 자동으로 종료됩니다.\n 퇴장하시겠습니까?",
          icon: "warning",
          buttons: true,
          dangerMode: true
        }).then(willExit => {
          if (willExit) {
            const payload = {
              roomId: state.mySessionId,
              maxViewers: state.maxViewers
            };
            store.dispatch("root/requestRoomDelete", payload);
            axios
              .delete(
                `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}`,

                {
                  auth: {
                    username: "OPENVIDUAPP",
                    password: OPENVIDU_SERVER_SECRET
                  }
                }
              )
              .then(response => {
                console.log(response);
              })
              .catch(error => console.log(error));
          }
        });
        // 오픈비두 방 종료
      } else {
        // 시청자가 나갈 때
        const payload = {
          email: store.getters["auth/getEmail"],
          roomId: state.mySessionId
        };
        store.dispatch("root/requestRoomExit", payload);
        store.commit("menu/setActiveCategory", null);
        store.commit("menu/setMenuActive", 0);
        const MenuItems = store.getters["menu/getMenus"];
        let keys = Object.keys(MenuItems);
        router.push({
          name: keys[0]
        });
      }
    };

    const updateMainVideoStreamManager = function(stream) {
      store.commit("root/setMainStreamManager", stream);
      state.showMainVideo = true;
    };

    // 내 영상 끄기
    const onOffVideo = function() {
      if (state.videoStatus) {
        state.publisher.publishVideo(false);
        // state.profile = store.getters["auth/getProfile"]
        store.commit("root/setUserVideoStatus", false);
      } else {
        state.publisher.publishVideo(true);
        // state.profile = ''
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
      if (state.isHost) {
        let cId = stream.stream.connection.connectionId;
        axios.delete(
          `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}/connection/${cId}`,
          {
            auth: {
              username: "OPENVIDUAPP",
              password: OPENVIDU_SERVER_SECRET
            }
          }
        );
      }
    };

    // 권한 수정

    let socket = new SockJS("https://i5a308.p.ssafy.io/ws");
    let authorization = state.isLoggedin;
    state.stompClient = Stomp.over(socket);
    state.stompClient.connect({ authorization }, frame => {
      state.stompClient.subscribe("/sub/emoji/" + state.mySessionId, res => {
        let jsonBody = JSON.parse(res.body);
        let e = {
          nickname: jsonBody.nickname,
          img: jsonBody.img,
          style:
            jsonBody.img.includes("wow") || jsonBody.img.includes("sad")
              ? "medium2"
              : "small2"
        };
        state.prevEmoji.push(e);
        setTimeout(() => {
          state.prevEmoji.shift();
        }, 5000);
      });
    });

    const clickLike = function() {
      let emoji = document.querySelector(".like");
      state.emoji = emoji.src;
      sendEmoji();
      // videoFilter()
    };

    const clickJoy = function() {
      let emoji = document.querySelector(".joy");
      state.emoji = emoji.src;
      sendEmoji();
    };

    const clickWow = function() {
      let emoji = document.querySelector(".wow");
      state.emoji = emoji.src;
      sendEmoji();
    };

    const clickHeart = function() {
      let emoji = document.querySelector(".heart");
      state.emoji = emoji.src;
      sendEmoji();
    };

    const clickSad = function() {
      let emoji = document.querySelector(".sad");
      state.emoji = emoji.src;
      sendEmoji();
    };

    const sendEmoji = function() {
      if (state.stompClient != null) {
        let emojiBox = {
          img: state.emoji,
          roomId: state.mySessionId,
          nickname: state.myUserName
        };
        state.stompClient.send("/pub/emoji", JSON.stringify(emojiBox), {});
        state.emoji = "";
      }
    };

    return {
      state,
      leaveSession,
      updateMainVideoStreamManager,
      onOffVideo,
      onOffAudio,
      unpublish,
      clickLike,
      clickJoy,
      clickWow,
      clickHeart,
      clickSad,
      sendEmoji
    };
  }
};
</script>

<style>
.gradient-box {
  border: 3px solid transparent;
  border-radius: 17px;
  overflow: hidden;
  background: -webkit-linear-gradient(white, white),
    -webkit-linear-gradient(left, #bb16e0 0%, #1aeba2 100%);
  background: -o-linear-gradient(white, white),
    -o-linear-gradient(left, #d388f4 0%, #d467da 100%);
  background: linear-gradient(white, white),
    linear-gradient(to right, #b72ba2 0%, #9d00ff 100%);
  -webkit-background-clip: padding-box, border-box;
  -moz-background-clip: padding-box, border-box;
  background-clip: padding-box, border-box;
  -webkit-background-origin: border-box;
  background-origin: border-box;
}

.nav-icons {
  margin-top: 10px;
  text-align: center;
}
.nav-icons i {
  font-size: 25px;
}

.video-container {
  width: 100%;
  height: 80vh;
}
.less2 {
  display: flex;
  justify-content: center;
  align-items: center;
}

.emoji-row {
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn {
  border: none;
  background: transparent;
  width: 50px;
  height: 50px;
  overflow: hidden;
  transition: all 0.2s linear;
}

.btn:hover {
  transform: scale(1.2);
  cursor: pointer;
}

.heart-btn {
  margin-left: 7px;
}

.small {
  width: 32px;
  margin-top: 5px;
}

.medium {
  width: 47px;
}

.emojilog {
  position: absolute;
  bottom: 5vh;
  width: 300px;
  overflow: hidden;
}

.emoji-bubble {
  display: flex;
  align-items: center;
  height: 40px;
}

.small2 {
  width: 32px;
  margin-top: 5px;
  padding-left: 9px;
}

.medium2 {
  width: 47px;
  object-fit: cover;
}

.emoji-bubble .nickname {
  background: #9f05ff69;
  border-radius: 0 10px 10px 0;
  padding-left: 7px;
}

.emoji-bubble .nickname .text {
  font-size: 89%;
  color: white;
}

.emoji-bubble .circle {
  padding: 0px;
  width: 40px;
  height: 40px;
  border-radius: 70%;
  overflow: hidden;
}

.nologin-video {
  text-align: center;
}

.nologin-video .nologin-profile {
  width: 60%;
  height: 0%;
  object-fit: cover;
}

.video-scroll {
  overflow-y: auto;
  height: 75vh;
}
/* .video-scroll h5 {
  margin: 10px 0 5px 0;
} */
.video-scroll::-webkit-scrollbar {
  width: 10px;
}
.video-scroll::-webkit-scrollbar-thumb {
  background-color: #9f05ff69;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
</style>
