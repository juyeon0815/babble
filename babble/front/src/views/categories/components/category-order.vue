<template>
  <div class="switch-order">
    <el-radio v-model="state.radio" label="best" @change="changetoBest">인기순 정렬</el-radio>
    <el-radio v-model="state.radio" label="recent" @change="changetoRecent">최신순 정렬</el-radio>
  </div>
  <router-view></router-view>
</template>

<script>
import { reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'CategoryOrder',
  setup () {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      radio: store.getters['root/getRadioStatus']
    })

    const changetoBest = function () {
      store.commit('root/setRadioState', 'best')
      console.log(store.getters['root/getRadioStatus'])

      router.push({
        name: `${route.params.categoryIndex}best`,
        params: {
          categoryIndex: route.params.categoryIndex
        }
      })
    }

    const changetoRecent = function () {
      store.commit('root/setRadioState', 'recent')
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
