<template>
  <div class="mypage-container">
    <el-tabs class="tab" v-model="state.activeName" @tab-click="handleClick">
      <el-tab-pane label="키워드 알림 설정" name="keyword"></el-tab-pane>
      <el-tab-pane label="시청 이력" name="history1"></el-tab-pane>
      <el-tab-pane label="방 생성 이력" name="history2"></el-tab-pane>
      <el-tab-pane label="회원정보" name="user-info"></el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>

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
      activeName: 'keyword',
    })

    const handleClick = (tab, event) => {
      router.push({
        // path: '/mypage/user-info'
        path: `/mypage/${tab.props.name}`
      })

    }

  return { state, handleClick }
  }
}
</script>

<style>
  .mypage-container {
    min-height: 80vh;
  }

  .mypage-container .tab {
    margin-top: 40px;
  }

  .el-tabs__active-bar {
    background-color: #8860D8;
  }

  .el-tabs__item.is-active{
    color: #8860D8;
  }

  .el-tabs__item:hover {
    color: #8860D8;
  }

  @media screen and (max-width: 480px) {
    .mypage-container .tab {
      margin-left: 5%;
      width: 90%;
    }

  }

</style>
