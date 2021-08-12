<template>
  <div v-if="streamManager" class="user-video">
    <div v-if="streamManager.stream.videoActive">
      <OvVideo :stream-manager="streamManager" />
    </div>
    <div v-else class="align">
      <!-- 카메라 꺼짐 이미지 대체 -->
      <div class="off-cam">
        <!-- <img src="https://picsum.photos/200" class="image"> -->
        <img :src="profile" class="image">
      </div>
    </div>

    <el-popover
      placement="top"
      :width="160"
      v-model:visible="state.popupVisible"
    >
      <div class="menu">
        <el-button @click="clickToMain">크게 보기</el-button>
        <el-button type="danger" plain @click="clickOut">강퇴</el-button>
      </div>
      <template #reference>
        <el-button
          type="text"
          @click="state.popupVisible = true"
          class="user-name"
          >{{ state.clientData.clientData }}</el-button
        >
      </template>
    </el-popover>
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
    streamManager: Object,
    profile: String
  },

  setup(props, { emit }) {
    console.log("가즈아");
    console.log(props.streamManager);
    console.log(props.profile, '왔니이')
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
      popupVisible: false,
      clientData: computed(() => {
        return getConnectionData();
      })
    });

    const getConnectionData = function() {
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    };

    const clickToMain = function() {
      state.popupVisible = false;
      emit("toMain");
    };

    const clickOut = function() {
      state.popupVisible = false;
      emit("unpublishMe");
    };

    return { state, getConnectionData, clickToMain, clickOut };
  }
};
</script>

<style>
.user-video {
  text-align: center;
}
.user-name {
  margin: 0;
  color: black;
}
.menu {
  display: flex;
  justify-content: center;
}
.align {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.off-cam {
  width: 18vw;
  height: 33vh;
  border-radius: 50%;
  background-color: black;
  overflow: hidden;
}


.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
