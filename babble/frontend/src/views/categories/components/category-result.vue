<template>
  <el-row class="result-container">
    <el-col :offset="18">
      <div class="switch-order">
      <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
      <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
      </div>
    </el-col>
    <el-col>
      <el-row class="conference-row" v-if="state.radio == 'best'">
        <div v-if="state.bestRoomCount == 0">해당 카테고리의 대화중인 방이 없습니다. 만들어보세요!</div>
        <Conference v-for="i in state.bestRoomCount" :key="i" :num="i" v-cloak
        :roomInfo="state.bestRoomList[i-1]"
        @click="clickConference(state.bestRoomList[i-1].id)"/>
      </el-row>
      <el-row class="conference-row" v-if="state.radio == 'recent'">
        <div v-if="state.recentRoomCount == 0">해당 카테고리의 대화중인 방이 없습니다. 만들어보세요!</div>
        <Conference v-for="i in state.recentRoomCount" :key="i" :num="i" v-cloak
        :roomInfo="state.recentRoomList[i-1]"
        @click="clickConference(state.recentRoomList[i-1].id)"/>
      </el-row>
    </el-col>
    <el-col :offset="20">
      <el-button type="text" @click="clickMore" v-if="state.bestRoomCount >= 10 || state.recentRoomCount >= 10">더보기</el-button>
    </el-col>
  </el-row>
  <button v-show="state.showBacktop" class="backtop" @click="clickTop">Top</button>
  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    :roomId="state.conferenceDialogNum"
    @closeConferenceDialog="onCloseConferenceDialog"/>
</template>

<script>
import { reactive, computed, watch, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import swal from 'sweetalert'
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
      bestRoomCount: 0,
      recentRoomCount: 0,
      pageNum: 1,
      activeCategory: computed(() => store.getters['menu/getActiveCategory']),
      conferenceDialogOpen: false,
      conferenceDialogNum: 0,
      showBacktop: false
    })

    const changetoBest = function () {
      const payload = {
        linkName: state.activeCategory,
        orderName: 'best',
        pageNum: 1
      }
      store.dispatch('menu/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.pageNum = 1
        state.recentRoomList = []
        state.recentRoomCount = 0
        state.bestRoomList = result.data
        state.bestRoomCount = result.data.length
        state.radio = 'best'
      })
      .catch(function (err) {
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
      store.dispatch('menu/requestRoomCategoryOrder', payload)
      .then(function (result) {
        state.pageNum = 1
        state.bestRoomList = []
        state.bestRoomCount = 0
        state.recentRoomList = result.data
        state.recentRoomCount = result.data.length
        state.radio = 'recent'
      })
      .catch(function (err) {
        alert(err)
      })
    }

    const clickMore = function () {
      // 데이터 10개 이상으로 test 시 v-if로 더보기 버튼 안보이게 설정하기
      const payload = {
        linkName: state.activeCategory,
        orderName: state.radio,
        pageNum: state.pageNum + 1
      }
      store.dispatch('menu/requestRoomCategoryOrder', payload)
      .then(function (result) {
        if (result.data.length == 0) {
          swal({
            text: "추가 데이터가 없습니다.\n 다른 키워드를 검색해보세요!",
            icon: "info",
          });
        } else {
          state.pageNum += 1
          if (state.radio == 'best') {
            state.bestRoomList.push(...result.data)
            state.bestRoomCount += result.data.length

          } else {
            state.recentRoomList.push(...result.data)
            state.recentRoomCount += result.data.length
          }
        }
      })
      .catch(function (err) {
        alert(err)
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

    onMounted(() => {
      window.addEventListener('scroll', function(e) {
        if (document.documentElement.scrollTop > 200) {
          state.showBacktop = true
        } else {
          state.showBacktop = false
        }
      })
    })

    const clickTop = function () {
      window.scrollTo({
        top: 0,
        behavior: "smooth"
      })
    }

    return { state, changetoBest, changetoRecent, clickMore, clickConference, onCloseConferenceDialog, clickTop }
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
  .backtop {
    position: fixed;
    bottom: 20px;
    right: 30px;
    z-index: 99;
    border: none;
    outline: none;
    background-color: #8860D8;
    color: white;
    cursor: pointer;
    padding: 15px;
    border-radius: 10px;
    font-size: 18px;
  }

  @media screen and (max-width: 480px) {
    .switch-order {
      display: none;
    }
    .backtop {
      display: none;
    }
  }

</style>
