<template>
  <div v-if="streamManager">
    <h2>UserVideo</h2>
    <OvVideo :stream-manager="streamManager"/>
    <div><p>{{ state.clientData }}</p></div>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import OvVideo from './ov-video';

export default {
  name: 'UserVideo',
	components: {
		OvVideo,
	},
	props: {
		streamManager: Object,
	},

  setup() {
    const state = reactive ({
      clientData: computed(() => {
        return getConnectionData()
      }),
    })

    const getConnectionData = function () {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    }

    return { state, getConnectionData }
  }
}
</script>

<style>

</style>