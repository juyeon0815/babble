<template>
  <MainHeader
    @openLoginDialog="onOpenLoginDialog"/>
  <router-view></router-view>
  <MainFooter />
  <LoginDialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>

</template>

<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
</style>

<script>
import MainHeader from './components/main-header'
import MainFooter from './components/main-footer'
import LoginDialog from './components/login-dialog'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter,
    LoginDialog,
  },
  data () {
    return {
      loginDialogOpen: false,
    }
  },

  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    },

  }
}
</script>
