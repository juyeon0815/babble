<template>
  <div class="head-label">Category</div>
  <!-- active name 처리해주기!! -->
  <el-tabs class="tab" v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="All" name="first">전체보기
      <el-row class="conference-row">
        <Conference v-for="i in 10" :key="i" :num="i" v-cloak
        @click="clickConference(i)"/>
      </el-row>
    </el-tab-pane>
    <el-tab-pane label="Sports" name="second">스포츠</el-tab-pane>
    <el-tab-pane label="Cooking" name="third">요리</el-tab-pane>
    <el-tab-pane label="Handmade" name="fourth">수공예</el-tab-pane>
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
import { onMounted } from 'vue'
import { useStore } from 'vuex'
import Conference from '../home/components/conference'

export default {
  name: 'Category',
  components: {
    Conference
  },
  setup () {
    const store = useStore()
    const handleClick = (tab, event) => {
        console.log(tab, event);
      }

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'category')
    })

    return { handleClick }
  }
}
</script>
