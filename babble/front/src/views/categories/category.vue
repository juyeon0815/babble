<template>
  <div class="head-label">Category</div>
  <router-view></router-view>

  <hr>

  <el-tabs class="tab" v-model="state.activeName" @tab-click="handleClick">
    <el-tab-pane label="All" name="all">전체보기
      <el-row class="conference-row">
        <Conference v-for="i in 10" :key="i" :num="i" v-cloak
        @click="clickConference(i)"/>
      </el-row>
    </el-tab-pane>
    <el-tab-pane label="Sports" name="sports">스포츠</el-tab-pane>
    <el-tab-pane label="Cooking" name="cooking">요리</el-tab-pane>
    <el-tab-pane label="Handcraft" name="handcraft">수공예</el-tab-pane>
    <el-tab-pane label="Music" name="music">음악</el-tab-pane>
    <el-tab-pane label="Finance" name="finance">금융</el-tab-pane>
    <el-tab-pane label="Game" name="game">게임</el-tab-pane>
    <el-tab-pane label="Movie" name="movie">영화</el-tab-pane>
    <el-tab-pane label="Drawing" name="drawing">그림</el-tab-pane>
    <el-tab-pane label="Book" name="book">독서</el-tab-pane>
    <el-tab-pane label="Pet" name="pet">반려동물</el-tab-pane>
  </el-tabs>
</template>

<style scoped>
  .head-label {
    font-size: 20px;
    font-weight: bold;
    margin: 20px 0 20px 50px;
  }
  .tab {
    margin-left: 50px;
  }
  .conference-row {
    justify-content: center;
    align-items: center;
  }
</style>

<script>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Conference from '../home/components/conference'
import CategoryResult from './components/category-result'

export default {
  name: 'Category',
  components: {
    Conference,
    CategoryResult
  },
  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      activeName: 'all',
      radio: 'popular',
    })

    const handleClick = (tab, event) => {
      console.log(tab, event);
      // activeName 값 변경
      // axios 처리
    }

    const clickConference = function (id) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: id
        }
      })
    }

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'category')
    })

    return { state, handleClick, clickConference }
  }
}
</script>
