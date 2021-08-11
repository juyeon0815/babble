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
      {{ streamManager.stream.connection.connectionId }}
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
    console.log("가즈아");
    console.log(props.streamManager);
    // props.streamManager.on("publisherStopSpeaking", event => {
    //   console.log("User " + event.connection.connectionId + " stop speaking");
    // });
    // console.log(props.streamManager);
    // if (props.streamManager != undefined) {
    //   props.streamManager.on("publisherStopSpeaking", event => {
    //     console.log("User " + event.connection.connectionId + " stop speaking");
    //   });
    // }

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
