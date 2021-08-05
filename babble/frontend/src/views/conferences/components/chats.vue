<template>

  <div class="chatlog" id="messages">
    <div v-for="(m, idx) in state.prevChat" :key="idx">
      <div v-bind:class="m.style">
      <h5>{{m.nickname}}</h5>
      {{m.content}}
      </div>
    </div>
  </div>

  <input type="textarea" class="chat"
    placeholder="채팅을 입력해주세요"
    v-model="state.chatText"
    @keyup.enter="enterChat">
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { reactive, watch, nextTick } from 'vue'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore()
    const state = reactive ({
      prevChat: [],
      nickname: '익명의' + store.getters["root/getEmail"],
      chatText: '',
      count: 0,
      stompClient: null,
      chatroomId: store.getters["root/getRoomID"],
    })

    // socket 연결
    let socket = new SockJS('https://localhost:8443/ws')
    state.stompClient = Stomp.over(socket)
    state.stompClient.connect({}, frame=>{
      console.log("success", frame)
      state.stompClient.subscribe("/sub/"+ state.chatroomId, res=>{
        let jsonBody = JSON.parse(res.body)
        let m={
          'nickname':jsonBody.nickname,
          'content': jsonBody.content,
          'style': jsonBody.nickname == state.nickname ? 'myMsg':'otherMsg'
        }
        state.prevChat.push(m)
        changeScroll()
      })
    }, err=>{
      console.log("fail", err)
    })

    const enterChat = function () {
      if(state.chatText.trim() !='' && state.stompClient!=null) {
        let chatMessage = {
          'content': state.chatText,
          'chatroomId' : state.chatroomId,
          'nickname':state.nickname,
        }
        state.stompClient.send("/pub/message", JSON.stringify(chatMessage),{})
        state.chatText = ''
      }
    }

    const changeScroll = async => {
      setTimeout(() => {
        const messageBox = document.getElementById('messages')
        messageBox.scrollTo({ top: messageBox.scrollHeight, behavior: 'auto' })
      }, 10)
    }

    return { state, enterChat, changeScroll }
  }
}
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
  .myMsg{
    text-align: right;
    color : gray;
  }
  .otherMsg{
    text-align: left;
  }
</style>
