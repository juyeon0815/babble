<template>
  <el-row class="navbar">
    <h2 @click="clickLogo">Babble</h2>
    <el-input prefix-icon="el-icon-search" class="search-bar"></el-input>
    <el-button type="info" plain @click="clickCategory">카테고리</el-button>
    <div>
      <el-button type="primary" plain @click="clickSignup">회원가입</el-button>
      <el-button type="info" plain @click="clickLogin">로그인</el-button>
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


export default {
  setup() {
    const router = useRouter()
    const store = useStore()

    const clickLogo = () => {
      store.commit('root/setMenuActive', 0)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[0]
      })
    }

    const clickSignup = function () {
      console.log('clickSignup')
    }

    const clickLogin = function () {
      console.log('clickLogin')
    }

    const clickCategory = function () {
      store.commit('root/setMenuActive', 1)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[1]
      })
    }

    return { clickLogo, clickSignup, clickLogin, clickCategory }
  }
}
</script>

<style>

</style>