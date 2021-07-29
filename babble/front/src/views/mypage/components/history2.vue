<template>
<div class="tab">
  <h3>{{state.email}}님의 방 생성이력</h3>
  <div>{{state.historyData}}</div>
</div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'History2',
  setup (props, {emit}) {
    const store = useStore()
    const state = reactive({
      email: computed(() => {
        return store.getters['root/getEmail']
      }),
      historyData: [],


    })
    store.dispatch('root/requestViewHistory2', {email: state.email})
    .then(function (result) {
      console.log(result)
      state.historyData = result.data
    })
    .catch(function (err) {
      alert(err)
    })




  return {state}
  }

}
</script>

<style>
  .tab {
    margin-left: 60px;
  }
</style>
