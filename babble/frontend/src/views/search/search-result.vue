<template>
  <el-row class="result-container">
    <el-col :offset="1"><h2>"{{ state.searchWord }}"의 검색 결과</h2></el-col>
    <el-col>
      <el-row class="conference-row">
        <div v-if="state.roomList.length == 0">검색 결과에 해당하는 방이 없습니다. 다른 키워드를 검색해보세요!</div>
        <Conference v-for="i in state.count" :key="i" :num="i" v-cloak
        :roomInfo="state.roomList[i-1]"
        @click="clickConference(state.roomList[i-1].id)"/>
      </el-row>
    </el-col>
    <el-col :offset="20">
      <el-button type="text" @click="clickMore" v-if="state.roomList.length >= 10">더보기</el-button>
    </el-col>
  </el-row>
</template>

<script>
import { reactive, computed, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
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
      roomList: [],
      count: 0,
      pageNum: 1,
      searchWord: computed(() => store.getters['menu/getSearchWord']),
    })

    const loadSearchResult = function () {
      const payload = {
        searchName: state.searchWord,
        pageNum: 1
      }
      store.dispatch('menu/requestRoomSearch', payload)
      .then(function (result) {
        state.roomList = result.data
        state.count = result.data.length
      })
      .catch(function (err) {
        alert(err)
      })
    }
    loadSearchResult()

    watch(
      () => state.searchWord,
      (searchWord, prev) => {
        console.log(prev + '--->' + searchWord)
        loadSearchResult()
      }
    )

    const clickMore = function () {
      // 데이터 10개 이상으로 test 시 v-if로 더보기 버튼 안보이게 설정하기
      const payload = {
        searchName: state.searchWord,
        pageNum: state.pageNum + 1
      }
      store.dispatch('menu/requestRoomSearch', payload)
      .then(function (result) {
        if (result.data.length == 0) {
          alert('추가 데이터가 없습니다.')
        } else {
          state.pageNum += 1
          state.roomList.push(...result.data)
          state.count += result.data.length
        }
      })
      .catch(function (err) {
        alert(err)
      })
    }

    const clickConference = function (id) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: id
        }
      })
    }

    return { state, loadSearchResult, clickMore, clickConference }
  },
}
</script>

<style>
  .result-container {
    min-height: 80vh;
  }
  .tab {
      margin-left: 50px;
    }
  .conference-row {
    justify-content: center;
    align-items: center;
  }
</style>
