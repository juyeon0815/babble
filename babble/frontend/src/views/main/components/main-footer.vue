<template>
  <div v-if="state.activeMenuIndex != -1">
    <h3>Footer</h3>
    {{ state.activeMenuIndex }}
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore()
    const state = reactive ({
      activeMenuIndex: computed(() => {
        return store.getters['menu/getActiveMenuIndex']
      })
    })
    return { state }
  }
}
</script>

<style>

</style>
