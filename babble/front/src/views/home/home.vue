<template>
  <el-carousel trigger="click" height="150px">
      <el-carousel-item v-for="item in 4" :key="item">
        <div style="display: inline">Test Blocks</div>
        <div style="display: inline">Test Blocks</div>
        <div style="display: inline">Test Blocks</div>
        <div style="display: inline">Test Blocks</div>
      </el-carousel-item>
    </el-carousel>
</template>
<style>

</style>
<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Home',

  components: {
  },

  setup () {
    const router = useRouter()


    return {  }
  }
}
</script>
