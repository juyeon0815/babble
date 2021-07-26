<template>
  <h1>???</h1>
  <div class="switch-order">
    <el-radio v-model="state.radio" label="popular">인기순 정렬</el-radio>
    <el-radio v-model="state.radio" label="new">최신순 정렬</el-radio>
  </div>
</template>

<script>
import { reactive } from 'vue'

export default {
  name: 'CategoryResult',
  setup () {
    const state = reactive({
      radio: 'popular',
    })

    return { state }
  }
}
</script>

<style>
  .switch-order {
    text-align: end;
    margin-right: 50px;
  }
</style>