<template>
  <video class="vid" ref="el" autoplay />
</template>

<script>
import { ref, onMounted } from "vue";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object
  },

  setup(props) {
    console.log(props.streamManager.stream.videoActive);
    const el = ref(null);

    onMounted(() => {
      props.streamManager.addVideoElement(el.value);
    });

    return { el };
  }
};
</script>
<style scoped>
	.vid {
		width: 90%
	}
</style>
