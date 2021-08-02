<template>
  <div v-if="streamManager">
    <OvVideo :stream-manager="streamManager"/>
    <div><p>{{ state.clientData.clientData }}</p></div>
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

  setup(props) {
    const state = reactive ({
      clientData: computed(() => {
        return getConnectionData()
      }),
    })

    const getConnectionData = function () {
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    }

    return { state, getConnectionData }
  }
}
</script>

<style>

</style>