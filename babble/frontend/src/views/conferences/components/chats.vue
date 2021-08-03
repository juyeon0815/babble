<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane class="in-tab" label="참여자 음성설정">
      <h3>Speaker</h3>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane class="in-tab" label="채팅">
      <div v-for="(m, idx) in state.prevChat" :key="idx">
        <div v-bind:class="m.style">
        <h5>{{m.nickname}}</h5>
        {{m.content}}
        </div>
      </div>
      <input type="textarea" class="chat" 
        placeholder="채팅을 입력해주세요" 
        v-model="state.chatText"
        @keyup.enter="enterChat">
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { reactive } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'chats',
  setup() {
    const store = useStore()
    const state = reactive ({
      prevChat: [],
      nickname: '익명의' + store.getters["root/getEmail"],
      chatText: '',
      count: 0,
      stompClient: null,
      chatroomId: store.getters["root/getRoomID"]
    })

    // socket 연결
    let socket = new SockJS('http://localhost:8080/ws')
    state.stompClient = Stomp.over(socket)
    state.stompClient.connect({}, frame=>{
      console.log("success", frame)
      state.stompClient.subscribe("/sub/"+ state.chatroomId, res=>{
        console.log('yes!!!!', res)
        let jsonBody = JSON.parse(res.body)
        let m={
          'nickname':jsonBody.nickname,
          'content': jsonBody.content,
          'style': jsonBody.nickname == state.nickname ? 'myMsg':'otherMsg'
        }
        console.log('!!!!!!' + m)
        state.prevChat.push(m)
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

        state.chatText=''
      }
    }

    return { state, enterChat }
  }
}
</script>

<style>
  .in-tab {
    margin: 20px;
  }
  .in-tab > h3 {
    margin-top: 0;
  }
  .chat {
    margin-top: 350px;
    height: 100px;
    width: 250px;
  }
  .myMsg{
  text-align: right;
  color : gray;
  }
  .otherMsg{
    text-align: left;
  }
</style>
