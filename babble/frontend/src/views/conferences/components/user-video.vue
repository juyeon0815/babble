<template>
  <div v-if="streamManager">
    <div v-if="streamManager.stream.videoActive">
      <OvVideo :stream-manager="streamManager" />
    </div>
    <div v-else>
      카메라 꺼짐 이미지 대체
    </div>

    <div>
      <p>{{ state.clientData.clientData }}</p>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import OvVideo from "./ov-video";

export default {
  name: "UserVideo",
  components: {
    OvVideo
  },
  props: {
    streamManager: Object
  },

  setup(props) {
    const state = reactive({
      clientData: computed(() => {
        return getConnectionData();
      })
    });

    const getConnectionData = function() {
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    };

    return { state, getConnectionData };
  }
};
</script>

<style></style>
