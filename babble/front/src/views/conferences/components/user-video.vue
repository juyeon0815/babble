<template>
  <div v-if="streamManager">
    <h2>UserVideo</h2>
    <OvVideo :stream-manager="streamManager"/>
    <div><p>{{ clientData }}</p></div>
  </div>
</template>

<script>
import { reactive } from 'vue'
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

    })

    // computed: {
		// clientData () {
    //     const { clientData } = this.getConnectionData();
    //     return clientData;
    //   },
    // },

    // methods: {
    //   getConnectionData () {
    //     const { connection } = this.streamManager.stream;
    //     return JSON.parse(connection.data);
    //   },
    // },

    return { state }
  }
}
</script>

<style>

</style>