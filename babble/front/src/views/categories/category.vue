<template>
  <div class="head-label">Category</div>
  <el-button-group class="tab">
    <router-link to="all"><el-button type="primary" plain>전체보기</el-button></router-link>
    <router-link to="sports"><el-button type="primary" plain>스포츠</el-button></router-link>
    <router-link to="cooking"><el-button type="primary" plain>요리</el-button></router-link>
    <router-link to="handcraft"><el-button type="primary" plain>수공예</el-button></router-link>
    <router-link to="music"><el-button type="primary" plain>음악</el-button></router-link>
    <router-link to="finance"><el-button type="primary" plain>금융</el-button></router-link>
    <router-link to="game"><el-button type="primary" plain>게임</el-button></router-link>
    <router-link to="movie"><el-button type="primary" plain>영화</el-button></router-link>
    <router-link to="drawing"><el-button type="primary" plain>그림</el-button></router-link>
    <router-link to="book"><el-button type="primary" plain>독서</el-button></router-link>
    <router-link to="pet"><el-button type="primary" plain>반려동물</el-button></router-link>
  </el-button-group>
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
import { reactive, onMounted, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'
import Conference from '../home/components/conference'

export default {
  name: 'Category',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()

    const state = reactive({
      activeName: 'all',
    })

    // onBeforeRouteUpdate ((to, from) => {
    //   console.log('!!!!!!!!!!!!')
    //   console.log(to.name)
    //   console.log(from.name)
    // })

    router.afterEach((to) => {
      router.go()
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'category')
    })

    return { state }
  },
}
</script>
