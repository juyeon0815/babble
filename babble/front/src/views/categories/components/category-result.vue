<template>
  <div class="switch-order">
    <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
    <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
  </div>
  <h2>Category Results</h2>
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
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import Conference from '@/views/home/components/conference'

export default {
  name: 'CategoryResult',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const route = useRoute()

    const state = reactive({
      radio: 'best',
      bestRoomList: [],
      recentRoomList: [],
      count: 0
    })

    const changetoBest = function () {
      const payload = {
        linkName: route.name,
        orderName: 'best'
      }
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.bestRoomList = result.data
        state.count = result.data.length
        console.log(result.data)
      })
      .catch(function (err) {
        alert(err)
      })
    }

    const changetoRecent = function () {
      const payload = {
        linkName: route.name,
        orderName: 'recent'
      }
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.recentRoomList = result.data
        state.count = result.data.length
        console.log(result.data)

      })
      .catch(function (err) {
        alert(err)
      })
    }

    return { state, changetoBest, changetoRecent }
  }
}
</script>

<style>
  .switch-order {
    text-align: end;
    margin-right: 50px;
  }
  .conference-row {
    justify-content: center;
    align-items: center;
  }
</style>