<template>
  <el-container>
    <el-main>
      {{ $route.params.conferenceId + '번 방 상세 보기 페이지' }}
    </el-main>
    <el-aside><Chats /></el-aside>
  </el-container>
  
</template>
<style>
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import Chats from './components/chats'

export default {
  name: 'conference-detail',
  components: {
    Chats
  },
  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      conferenceId: ''
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
