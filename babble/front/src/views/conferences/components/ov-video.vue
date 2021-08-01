<template>
	<h3>OV Video</h3>
	<video autoplay/>
</template>

<script>
import { onMounted } from 'vue'

export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

  setup() {
    onMounted(() => {
		  // props.streamManager.addVideoElement(this.$el);
	  })
  }
}
</script>