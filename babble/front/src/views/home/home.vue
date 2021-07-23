<template>
  <div class="header-space"></div>
  <div class="head-label">베스트 라이브</div>
  <el-carousel trigger="click" height="400px">
    <el-carousel-item v-for="item in 2" :key="item">
      <el-row class="conference-row">
        <Conference v-for="i in 5" :key="i" :num="i+5*(item-1)" v-cloak
        @click="clickConference(i+5*(item-1))"/>
      </el-row>
    </el-carousel-item>
  </el-carousel>
  
  <div class="head-label">최신 라이브</div>
  <el-row class="conference-row">
    <Conference v-for="i in 10" :key="i" :num="i" v-cloak
    @click="clickConference(i)"/>
  </el-row>
</template>

<style>
  .header-space {
    height: 400px;
    background-color: #d3c4ed;
  }
  .head-label {
    font-size: 20px;
    font-weight: bold;
    margin: 20px 0 20px 50px;
  }
  .conference-row {
    justify-content: center;
    align-items: center;
  }
</style>
<script>
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import Conference from './components/conference'

export default {
  name: 'Home',

  components: {
    Conference
  },

  setup () {
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      
    })

    const clickConference = function (id) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: id
        }
      })
    }

    return { state, clickConference }
  }
}
</script>
