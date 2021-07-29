<template>
  <div v-loading="loading">
    <MainHeader
      @openLoginDialog="onOpenLoginDialog"
      @openJoinDialog="onOpenJoinDialog"/>
    <router-view></router-view>
    <MainFooter />
    <LoginDialog
      :open="loginDialogOpen"
      @closeLoginDialog="onCloseLoginDialog"/>
    <JoinDialog
      :open="joinDialogOpen"
      @closeJoinDialog="onCloseJoinDialog"/>
  </div>
</template>

<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap');
</style>

<script>
import MainHeader from './components/main-header'
import MainFooter from './components/main-footer'
import LoginDialog from './components/login-dialog'
import JoinDialog from './components/join-dialog'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter,
    LoginDialog,
    JoinDialog,
  },
  data() {
    return {
      loginDialogOpen: false,
      signUpDialogOpen: false
    };
  },
  computed: {
    loading: function() {
      return this.$store.state.root.loadingStatus;
    }
  },

  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    },
    onOpenJoinDialog () {
      this.joinDialogOpen = true
    },
    onCloseJoinDialog () {
      this.joinDialogOpen = false
    },

  }
}
</script>
