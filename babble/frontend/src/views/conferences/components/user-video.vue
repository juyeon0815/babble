<template>
  <div v-if="streamManager" class="user-video">
    <div v-show="streamManager.stream.videoActive">
      <OvVideo :stream-manager="streamManager" />
    </div>
    <div v-show="!streamManager.stream.videoActive" class="align">
      <!-- 카메라 꺼짐 이미지 대체 -->
      <div v-if="gridCount">
        <!-- <img :src="profile.url" class="image-alone"> -->
        <img src="https://i.imgur.com/d6Yug9x.png" class="image-alone subvid" />
      </div>
      <div v-else>
        <!-- <img :src="profile.url" class="image"> -->
        <img src="https://i.imgur.com/d6Yug9x.png" class="image subvid" />
      </div>
    </div>

    <el-popover
      placement="top"
      :width="160"
      v-model:visible="state.popupVisible"
    >
      <div class="menu">
        <el-button @click="clickToMain">크게 보기</el-button>
        <el-button
          v-if="state.isHost && !isMe"
          type="danger"
          plain
          @click="clickOut"
          >강퇴</el-button
        >
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
import { useStore } from "vuex";
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
    isMe: Boolean
  },

  setup(props, { emit }) {
    const store = useStore();
    const state = reactive({
      popupVisible: false,
      clientData: computed(() => {
        return getConnectionData();
      }),
      isHost: computed(() => store.getters["root/getIsHost"]),
      clientName: ""
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
  border-radius: 10px;
}

.image-alone {
  width: 60%;
  border-radius: 10px;
}

/* .fit-to-image {
  display: inline-block;
} */
</style>
