<template>
  <div class="header-space"></div>
  <div class="head-label">베스트 라이브</div>
  <el-carousel trigger="click" height="400px">
    <el-carousel-item v-for="item in state.carouselCount" :key="item">
      <el-row class="conference-row">
        <Conference v-for="i in 5" :key="i" v-cloak
        :roomInfo="state.bestRoomList[i+5*(item-1)-1]"
        @click="clickConference(i+5*(item-1))"/>
      </el-row>
    </el-carousel-item>
  </el-carousel>

  <div class="head-label">최신 라이브</div>
  <el-row class="conference-row">
    <Conference v-for="i in state.count" :key="i" v-cloak
    :roomInfo="state.recentRoomList[i-1]"
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
      bestRoomList: [],
      recentRoomList: [],
      count: 0,
      carouselCount: 1,
    })
    
    store.commit('root/startSpinner')
    store.dispatch('root/requestRoomAllBest')
    .then(function (result) {
      state.bestRoomList = result.data
      state.count = result.data.length
      if (state.count > 5) {
        state.carouselCount = 2
      }
    })
    .catch(function (err) {
      alert(err)
    })

    store.dispatch('root/requestRoomAllRecent')
    .then(function (result) {
      state.recentRoomList = result.data
      console.log(result.data)
      store.commit('root/endSpinner')
    })
    .catch(function (err) {
      store.commit('root/endSpinner')
      alert(err)
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
