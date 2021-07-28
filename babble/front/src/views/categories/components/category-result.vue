<template>
  <el-row>
    <el-col :offset="18">
      <div class="switch-order">
      <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
      <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
      </div>
    </el-col>
    <el-col :offset="1"><h2>{{ state.pageName }}</h2></el-col>
    <el-col>
      <el-row class="conference-row" v-if="state.radio === 'best'">
        <Conference v-for="i in state.count" :key="i" :num="i" v-cloak
        :roomInfo="state.bestRoomList[i-1]"
        @click="clickConference(i)"/>
      </el-row>
      <el-row class="conference-row" v-if="state.radio === 'recent'">
        <Conference v-for="i in state.count" :key="i" :num="i" v-cloak
        :roomInfo="state.recentRoomList[i-1]"
        @click="clickConference(i)"/>
      </el-row>
    </el-col>
    <el-col :offset="20"><el-button type="success" plain @click="clickMore">더보기</el-button></el-col>
  </el-row> 
  
</template>

<script>
import { reactive, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'
import Conference from '@/views/home/components/conference'

export default {
  name: 'CategoryResult',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()

    const state = reactive({
      radio: 'best',
      pageName: route.name,
      bestRoomList: [],
      recentRoomList: [],
      count: 0,
    })

    // store에 카테고리를 저장해서 걔가 바뀌는거를 computed 해서 바뀌면 axios

    const changetoBest = function () {
      const payload = {
        linkName: route.name,
        orderName: 'best'
      }
      store.commit('root/startSpinner')
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.bestRoomList = result.data
        state.count = result.data.length
        store.commit('root/endSpinner')
      })
      .catch(function (err) {
        store.commit('root/endSpinner')
        alert(err)
      })
    }
    changetoBest()

    const changetoRecent = function () {
      const payload = {
        linkName: route.name,
        orderName: 'recent'
      }
      store.commit('root/startSpinner')
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.recentRoomList = result.data
        state.count = result.data.length
        store.commit('root/endSpinner')
      })
      .catch(function (err) {
        alert(err)
        store.commit('root/endSpinner')
      })
    }

    const clickMore = function () {
      console.log('더보기!')
    }

    return { state, changetoBest, changetoRecent, clickMore }
  },
  
}
</script>

<style>
  .tab {
    margin-left: 50px;
  }
  .conference-row {
    justify-content: center;
    align-items: center;
  }
</style>
