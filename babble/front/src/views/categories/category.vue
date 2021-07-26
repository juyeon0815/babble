<template>
  <div class="head-label">Category</div>
  <el-tabs v-model="activeName" @tab-click="handleClick" class="tab">
    <el-tab-pane label="전체보기" name="all"></el-tab-pane>
    <el-tab-pane label="스포츠" name="sports"></el-tab-pane>
    <el-tab-pane label="요리" name="cooking"></el-tab-pane>
    <el-tab-pane label="수공예" name="handcraft"></el-tab-pane>
    <el-tab-pane label="음악" name="music"></el-tab-pane>
    <el-tab-pane label="금융" name="finance"></el-tab-pane>
    <el-tab-pane label="게임" name="game"></el-tab-pane>
    <el-tab-pane label="영화" name="movie"></el-tab-pane>
    <el-tab-pane label="그림" name="drawing"></el-tab-pane>
    <el-tab-pane label="독서" name="book"></el-tab-pane>
    <el-tab-pane label="반려동물" name="pet"></el-tab-pane>
  </el-tabs>
  <router-view></router-view>
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

export default {
  name: 'Category',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      activeName: 'all',
      radio: 'popular',
    })

    const handleClick = function (tab) {
      store.commit('root/setRadioState', 'best')
      console.log(store.getters['root/getRadioStatus'])
      router.push({
        name: `${tab.index}best`,
        params: {
          categoryIndex: tab.index,
        }
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

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'category')
    })

    return { state, handleClick, clickConference }
  }
}
</script>
