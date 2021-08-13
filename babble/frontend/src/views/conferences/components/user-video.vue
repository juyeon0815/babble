<template>
  <div v-if="streamManager" class="user-video">
    <div v-if="streamManager.stream.videoActive">
      <OvVideo :stream-manager="streamManager" />
    </div>
    <div v-else class="align">
      <!-- 카메라 꺼짐 이미지 대체 -->
      <div v-if="gridCount">
        <img :src="profile.url" class="image-alone">
      </div>
      <div v-else>
        <img :src="profile.url" class="image">
      </div>
    </div>

    <el-popover
      placement="top"
      :width="160"
      v-model:visible="state.popupVisible"
    >
      <div class="menu">
        <el-button @click="clickToMain">크게 보기</el-button>
        <el-button v-if="state.isHost" type="danger" plain @click="clickOut">강퇴</el-button>
      </div>
      <template #reference>
        <el-button
          @click="state.popupVisible = true"
          type="text"
          class="user-name"
          >{{ state.clientData.clientData }}
        </el-button>
      </template>
    </el-popover>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from 'vuex'
import OvVideo from "./ov-video";

export default {
  name: "UserVideo",
  components: {
    OvVideo
  },
  props: {
    streamManager: Object,
    profile: Object,
    gridCount: String,
  },

  setup(props, { emit }) {
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

    const store = useStore()
    const state = reactive({
      popupVisible: false,
      clientData: computed(() => {
        return getConnectionData();
      }),
      isHost: computed(() => store.getters["root/getIsHost"]),
      clientName: ''
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
  /* font-size: 20px; */
  border: none;
  background-color: white;
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

.image {
  width: 90%;
}

.image-alone {
  width: 60%;
}

/* .fit-to-image {
  display: inline-block;
} */
</style>
