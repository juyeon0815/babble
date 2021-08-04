<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane label="참여자 음성설정">
      <h3>Speaker</h3>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane label="채팅">
      <Chats />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Chats from './chats'

export default {
  name: 'sidebar',
  components: {
    Chats
  },
  setup() {
    
    return { }
  }
}
</script>

<style>
  
</style>
