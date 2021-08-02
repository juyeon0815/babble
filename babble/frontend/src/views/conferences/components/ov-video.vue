<template>
	<video ref="el" autoplay/>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

  setup(props) {
		const el = ref(null)

    onMounted(() => {
		  props.streamManager.addVideoElement(el.value)
	  })

		return { el }
  }
}
</script>