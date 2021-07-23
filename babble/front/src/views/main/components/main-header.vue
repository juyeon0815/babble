<template>
  <div class="header-space">
    <el-row class="lg-nav">
      <h2>Babble</h2>
      <el-input prefix-icon="el-icon-search" class="search-bar"></el-input>
      <el-button type="info" plain>카테고리</el-button>
      <div>
        <el-button type="primary" plain @click="clickJoin">회원가입</el-button>
        <el-button type="info" plain @click="clickLogin">로그인</el-button>
      </div>
    </el-row>
  </div>
</template>

<style scoped>
  .header-space {
    height: 500px;
    background-color: #d3c4ed;
  }

  .header-space .lg-nav {
    padding-top: 10px;
    justify-content: space-around;
    align-items: center;
  }

  .header-space .lg-nav .search-bar {
    width: 400px;
    display: inline;
    margin: 20px;
  }
</style>

<script>
import { reactive, computed, onMounted } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  setup(props, { emit }) {
    const state = reactive({
      isLoggedin: computed(() => {
        return store.getters['root/getToken']
      })
    })

    const clickJoin = function () {
      console.log('clickJoin')
    }

    const clickLogin = function () {
      // console.log('clickLogin')
      emit('openLoginDialog')
    }

    return { state, clickJoin, clickLogin }
  }
}
</script>

<style>

</style>
