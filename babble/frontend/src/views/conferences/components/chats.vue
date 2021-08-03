<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane class="in-tab" label="참여자 음성설정">
      <h3>Speaker</h3>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane class="in-tab" label="채팅">
      <h4>채팅내용</h4>
      {{ state.prevChat }}
      <hr>
      <p v-for="i in state.count" :key="i">{{ state.prevChat[i-1] }}</p>
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

export default {
  name: 'chats',
  props: {
    conferenceId: {
      type: Number,
    }
  },
  setup(props) {
    const state = reactive ({
      id: -1,
      prevChat: [],
      nickname: '익명의 너구리 수정필요',
      chatText: '',
      count: 0,
      stompClient: null
    })

    // socket 연결
    let socket = new SockJS('http://localhost:8080/ws')
    state.stompClient = Stomp.over(socket)
    state.stompClient.connect({}, frame=>{
      console.log("success", frame)
      state.stompClient.subscribe("/sub/"+"1", res=>{
        console.log('yes!!!!', res)
        let jsonBody = JSON.parse(res.body)
        let m={
          'senderNickname':jsonBody.senderNickname,
          'content': jsonBody.content,
          'style': jsonBody.senderId == state.id ? 'myMsg':'otherMsg'
        }
        console.log('!!!!!!' + m)
        state.prevChat.msg.push(m)
      })
    }, err=>{
      console.log("fail", err)
    })

    const enterChat = function () {
      if(state.chatText.trim() !='' && state.stompClient!=null) {
        let chatMessage = {
          'content': state.chatText,
          'chatroomId' : props.conferenceId,
          'senderNickname':state.nickname,
          'senderId': state.id,
          'id':"0"
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
</style>