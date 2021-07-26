<template>
  <el-row class="navbar">
    <h2 @click="clickLogo">Babble</h2>
    <el-input prefix-icon="el-icon-search" class="search-bar"></el-input>
    <el-button type="info" plain @click="clickCategory">카테고리</el-button>
    <div v-show="!state.isLoggedin">
      <el-button type="primary" plain @click="clickJoin">회원가입</el-button>
      <el-button type="info" plain @click="clickLogin">로그인</el-button>
    </div>
    <div v-show="state.isLoggedin">
      <el-button type="primary" plain @click="clickMyPage">마이페이지</el-button>
      <el-button type="info" plain @click="clickLogout">로그아웃</el-button>
    </div>
  </el-row>
</template>

<style scoped>
  .navbar {
    padding-top: 5px;
    justify-content: space-around;
    align-items: center;
    background-color: #d3c4ed;
  }

  .navbar .search-bar {
    width: 400px;
    display: inline;
    margin: 20px;
  }
</style>

<script>
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { reactive, computed, onMounted } from 'vue'

export default {
  name: 'main-header',

  setup(props, { emit }) {
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      isLoggedin: computed(() => {
        return store.getters['root/getToken']
      })
    })

    const clickLogo = () => {
      store.commit('root/setMenuActive', 0)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[0]
      })
    }

    const clickJoin = function () {
      // console.log('clickJoin')
      emit('openJoinDialog')
    }

    const clickLogin = function () {
      // console.log('clickLogin')
      emit('openLoginDialog')
    }

    const clickCategory = function () {
      store.commit('root/setMenuActive', 1)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[1]
      })
    }

    const clickMyPage = function () {
      store.commit('root/setMenuActive', 2)
      const MenuItems = store.getters['root/getMenus']
      console.log(MenuItems)
      let keys = Object.keys(MenuItems)
      console.log(keys)
      router.push({
        name: keys[2]
      })
    }

    const clickLogout = function () {
      console.log('clickLogout')
      store.dispatch('root/requestLogout')
      .then(()=> store.commit('root/setLogout'))
      .then(()=> router.push('/'))
      console.log(state.isLoggedin)
    }

    return { state, clickLogo, clickJoin, clickLogin, clickCategory, clickMyPage, clickLogout }
  }
}
</script>

<style>

</style>
