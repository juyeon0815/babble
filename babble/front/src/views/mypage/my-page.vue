<template>
  <div class="head-label">마이페이지</div>
  <el-tabs class="tab" v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="키워드 알림 설정" name="keyword"></el-tab-pane>
    <el-tab-pane label="시청 이력" name="history1"></el-tab-pane>
    <el-tab-pane label="방 생성 이력" name="history2"></el-tab-pane>
    <el-tab-pane label="회원정보" name="user-info"></el-tab-pane>
  </el-tabs>
  <router-view></router-view>

</template>

<script>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Keyword from './components/keyword'
import History1 from './components/history1'
import History2 from './components/history2'
import UserInfo from './components/user-info'

export default {
  name: 'MyPage',
  components: {
    Keyword,
    History1,
    History2,
    UserInfo
  },
  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      activeName: 'user-info',
    })

    const handleClick = (tab, event) => {
      console.log('이건 탭')
      console.log(tab)
      console.log(tab.props.name)
      console.log('이건 이벤트')
      console.log(event)
      router.push({
        // path: '/mypage/user-info'
        path: `/mypage/${tab.props.name}`
      })

    }

  return { state, handleClick }
  }
}
</script>

<style scoped>
  .head-label {
    font-size: 20px;
    font-weight: bold;
    margin: 20px 0 20px 50px;
  }
  .tab {
    margin-left: 50px;
  }

</style>
