<template>
  <div class="chatlog" id="messages">
    <div v-for="(m, idx) in state.prevChat" :key="idx">
      <div class="chatbubble" :class="m.style">
        <span>{{ m.nickname }}</span>
        <p class="chatmsg">{{ m.content }}</p>
      </div>
    </div>
  </div>

  <input
    v-if="state.isLoggedin"
    type="textarea"
    class="chat"
    placeholder="채팅을 입력해주세요"
    v-model="state.chatText"
    @keyup.enter="enterChat"
  />
  <input
    v-else
    type="textarea"
    class="chat"
    placeholder="로그인이 필요합니다!"
    readonly
  />
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { reactive, watch, nextTick, computed } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const state = reactive({
      prevChat: [],
      nickname: computed(() => store.getters["root/getUserName"]),
      chatText: "",
      count: 0,
      stompClient: null,
      chatroomId: store.getters["root/getRoomID"],
      isLoggedin: computed(() => {
        return store.getters["auth/getToken"];
      })
    });

    // socket 연결

    let socket = new SockJS("https://i5a308.p.ssafy.io/ws");

    let authorization = state.isLoggedin;
    state.stompClient = Stomp.over(socket);
    console.log(">>>> authorization " + authorization);
    if (!authorization) {
      state.stompClient.connect(
        {},
        frame => {
          console.log(">>> socket connect success", frame);
          state.stompClient.subscribe(
            "/sub/message/" + state.chatroomId,
            res => {
              let jsonBody = JSON.parse(res.body);
              let m = {
                nickname: jsonBody.nickname,
                content: jsonBody.content,
                style:
                  jsonBody.nickname == state.nickname ? "myMsg" : "otherMsg"
              };
              state.prevChat.push(m);
              changeScroll();
            }
          );
        },
        err => {
          console.log("fail", err);
        }
      );
    } else {
      state.stompClient.connect(
        { authorization },
        frame => {
          console.log(">>> socket connect success", frame);
          state.stompClient.subscribe(
            "/sub/message/" + state.chatroomId,
            res => {
              let jsonBody = JSON.parse(res.body);
              let m = {
                nickname: jsonBody.nickname,
                content: jsonBody.content,
                style:
                  jsonBody.nickname == state.nickname ? "myMsg" : "otherMsg"
              };
              state.prevChat.push(m);
              changeScroll();
            }
          );
        },
        err => {
          console.log("fail", err);
        }
      );
    }

    const enterChat = function() {
      if (state.chatText.trim() != "" && state.stompClient != null) {
        let chatMessage = {
          content: state.chatText,
          chatroomId: state.chatroomId,
          nickname: state.nickname
        };
        state.stompClient.send("/pub/message", JSON.stringify(chatMessage), {});
        state.chatText = "";
      }
    };

    const changeScroll = async => {
      setTimeout(() => {
        const messageBox = document.getElementById("messages");
        messageBox.scrollTo({ top: messageBox.scrollHeight, behavior: "auto" });
      }, 10);
    };

    return { state, enterChat, changeScroll };
  }
};
</script>

<style>
.chatlog {
  overflow-y: auto;
  height: 75vh;
}
.chatlog h5 {
  margin: 10px 0 5px 0;
}
.chatlog::-webkit-scrollbar {
  width: 10px;
}
.chatlog::-webkit-scrollbar-thumb {
  background-color: grey;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
  visibility: hidden;
}
.chatlog::-webkit-scrollbar-thumb:hover {
  visibility: visible;
}
.chat {
  height: 15vh;
  width: 100%;
  bottom: 0;
  position: fixed;
}
.chatbubble {
  background-color: white;
  margin-bottom: 8px;
  display: flex;
  padding: 10px 10px 0 10px;
  border-radius: 0 6px 6px 0;
  max-width: 60%;
  width: auto;
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.12), 0 2px 4px rgba(0, 0, 0, 0.24);
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
  width: calc(100% - 50px);
}

.chatbubble .chatmsg {
  display: inline-block;
  text-align: start;
  font-size: 11pt;
  line-height: 13pt;
  margin: 0 0 10px;
}
.chatbubble > span {
  font-size: 8pt;
  margin-bottom: 10px;
}

.myMsg {
  float: right;
  border-radius: 6px 0 0 6px;
  color: gray;
  text-align: right;
}
</style>
