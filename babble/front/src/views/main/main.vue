<template>
  <MainHeader />
  <router-view></router-view>
  <MainFooter />
</template>

<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
</style>

<script>
import MainHeader from './components/main-header'
import MainFooter from './components/main-footer'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter
  },
  data () {
    return {
    }
  },

  methods: {

  }
}
</script>
