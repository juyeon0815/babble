<template>
  <el-container>
    <el-main>
      {{ state.conferenceId }}
      <VideoSpace 
      :conferenceId="state.conferenceId"/>
    </el-main>
    <el-aside class="sideChat">
      <Chats 
      :conferenceId="state.conferenceId"/>
    </el-aside>
  </el-container>
</template>

<style>
  
</style>

<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import VideoSpace from './components/video-space'
import Chats from './components/chats'

export default {
  name: 'conference-detail',
  components: {
    VideoSpace,
    Chats
  },
  setup () {
    const route = useRoute()

    const state = reactive({
      conferenceId: '',
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = ''
    })

    return { state }
  }
}
</script>
