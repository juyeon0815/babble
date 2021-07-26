<template>
  <div class="switch-order">
    <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
    <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
  </div>
  <router-view></router-view>
</template>

<script>
import { reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'

export default {
  name: 'CategoryOrder',
  setup () {
    const route = useRoute()
    const router = useRouter()

    const state = reactive({
      activeName: 'all',
      radio: 'best',
    })

    const changetoBest = function () {
      router.push({
        name: `${route.params.categoryIndex}best`,
        params: {
          categoryIndex: route.params.categoryIndex
        }
      })
    }

    const changetoRecent = function () {
      router.push({
        name: `${route.params.categoryIndex}recent`,
        params: {
          categoryIndex: route.params.categoryIndex
        }
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
</style>