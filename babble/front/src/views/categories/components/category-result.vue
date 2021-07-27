<template>
  <h2>Category Results {{ categoryIndex }}</h2>
</template>

<script>
import { reactive } from 'vue'

export default {
  name: 'CategoryResult',
  props: {
    categoryIndex: {
      type: String,
    }
  },
  setup () {
    const state = reactive({

    })

    return { state }
  }
}
</script>

<style>
</style>
