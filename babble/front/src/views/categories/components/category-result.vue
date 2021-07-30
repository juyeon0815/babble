<template>
  <el-row>
    <el-col :offset="18">
      <div class="switch-order">
      <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
      <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
      </div>
    </el-col>
    <el-col :offset="1"><h2>{{ state.activeCategory }}</h2></el-col>
    <el-col :offset="1"><h2>Page : {{ state.pageNum }}</h2></el-col>
    <el-col>
      <el-row class="conference-row" v-if="state.radio === 'best'">
        <Conference v-for="i in state.count" :key="i" :num="i" v-cloak
        :roomInfo="state.bestRoomList[i-1]"
        @click="clickConference(state.bestRoomList[i-1].id)"/>
      </el-row>
      <el-row class="conference-row" v-if="state.radio === 'recent'">
        <Conference v-for="i in state.count" :key="i" :num="i" v-cloak
        :roomInfo="state.recentRoomList[i-1]"
        @click="clickConference(state.recentRoomList[i-1].id)"/>
      </el-row>
    </el-col>
    <el-col :offset="20"><el-button type="success" plain @click="clickMore">더보기</el-button></el-col>
  </el-row> 
  <ConferenceDialog 
    :open="state.conferenceDialogOpen"
    :roomId="state.conferenceDialogNum"
    @closeConferenceDialog="onCloseConferenceDialog"/>
</template>

<script>
import { reactive, computed, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import Conference from '@/views/home/components/conference'
import ConferenceDialog from '@/views/home/components/conference-dialog'

export default {
  name: 'CategoryResult',
  components: {
    Conference,
    ConferenceDialog
  },
  setup () {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()

    const state = reactive({
      radio: 'best',
      bestRoomList: [],
      recentRoomList: [],
      count: 0,
      pageNum: 1,
      activeCategory: computed(() => store.getters['root/getActiveCategory']),
      conferenceDialogOpen: false,
      conferenceDialogNum: 0,
    })

    const changetoBest = function () {
      const payload = {
        linkName: state.activeCategory,
        orderName: 'best',
        pageNum: 1
      }
      store.commit('root/startSpinner')
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.pageNum = 1
        state.bestRoomList = result.data
        state.count = result.data.length
        state.radio = 'best'
        store.commit('root/endSpinner')
      })
      .catch(function (err) {
        store.commit('root/endSpinner')
        alert(err)
      })
    }
    // setup시 한번 실행
    changetoBest()

    const changetoRecent = function () {
      const payload = {
        linkName: state.activeCategory,
        orderName: 'recent',
        pageNum: 1
      }
      store.commit('root/startSpinner')
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.pageNum = 1
        state.recentRoomList = result.data
        state.count = result.data.length
        state.radio = 'recent'
        store.commit('root/endSpinner')
      })
      .catch(function (err) {
        alert(err)
        store.commit('root/endSpinner')
      })
    }

    const clickMore = function () {
      // 데이터 10개 이상으로 test 시 v-if로 더보기 버튼 안보이게 설정하기
      const payload = {
        linkName: state.activeCategory,
        orderName: state.radio,
        pageNum: state.pageNum + 1
      }
      store.commit('root/startSpinner')
      store.dispatch('root/requestRoomCategoryOrder', payload)
      .then(function (result) {
        if (result.data.length == 0) {
          alert('추가 데이터가 없습니다.')
          store.commit('root/endSpinner')
        } else {
          state.pageNum += 1
          state.recentRoomList.push(result.data)
          state.count += result.data.length
          store.commit('root/endSpinner')
        }
      })
      .catch(function (err) {
        alert(err)
        store.commit('root/endSpinner')
      })
    }

    const clickConference = function (id) {
      state.conferenceDialogOpen = true
      state.conferenceDialogNum = id
    }

    const onCloseConferenceDialog = function () {
      state.conferenceDialogOpen = false
    }

    watch(
      () => state.activeCategory,
      (activeCategory, prev) => {
        // console.log(prev + '--->' + activeCategory)
        changetoBest()
      }
    )

    return { state, changetoBest, changetoRecent, clickMore, clickConference, onCloseConferenceDialog }
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
