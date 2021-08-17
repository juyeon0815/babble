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
    const el = ref(null);

    onMounted(() => {
      console.log("OV-VIDEO");
      props.streamManager.addVideoElement(el.value);
    });

    return { el };
  }
};
</script>
<style scoped>
.vid {
  border-radius: 10px;
  width: 90%;
}
</style>
