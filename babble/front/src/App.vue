<template>
  <!-- main 으로 시작 -->
  <h1>app.vue</h1>
  <Main/>
</template>

<script>
import Main from './views/main/main.vue'

export default {
  name: 'App',

  components: {
    Main,
  },

  data: () => ({
    //
  }),
}
</script>
