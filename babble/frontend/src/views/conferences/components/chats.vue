<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane class="in-tab" label="참여자 음성설정">
      <h3>Speaker</h3>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane class="in-tab" label="채팅">
      <h4>채팅내용</h4>
      <p v-for="i in state.count" :key="i">{{ state.prevChat[i-1] }}</p>
      <input type="textarea" class="chat" 
        placeholder="채팅을 입력해주세요" 
        v-model="state.chatText"
        @keyup.enter="enterChat">
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { reactive } from 'vue'

export default {
  setup() {
    const state = reactive ({
      prevChat: [],
      chatText: '',
      count: 0
    })

    const enterChat = function () {
      state.prevChat.push(state.chatText)
      state.count += 1
      state.chatText = ''
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