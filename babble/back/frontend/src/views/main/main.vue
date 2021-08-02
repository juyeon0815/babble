<template>

  <el-container class="main-wrapper">
    <main-header
      :height="`70px`"
      @openJoinDialog="onOpenJoinDialog"
      @openLoginDialog="onOpenLoginDialog"/>
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container>
  <join-dialog
    :open="joinDialogOpen"
    @closeJoinDialog="onCloseJoinDialog"/>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';

</style>
<script>
import LoginDialog from './components/login-dialog'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import JoinDialog from './components/join-dialog'

import TheLoader from './components/TheLoader'
import { reactive, computed, ref} from 'vue'
import { mapState } from 'vuex'

export default {
  name: 'Main',
  
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    JoinDialog,
    TheLoader
  },
  data () {
    return {
      isLoading : false,
      loginDialogOpen: false,
      joinDialogOpen: false
    }
  },

  setup(props, { emit }) {
   const state = reactive({
     isLoading : false,
   })
   return{state}
  },
 
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    },
    onOpenJoinDialog(){
      this.joinDialogOpen = true;
    },
    onCloseJoinDialog(){
      this.joinDialogOpen=false;
    }
  },
}
</script>
