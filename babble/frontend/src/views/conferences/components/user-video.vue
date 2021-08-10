<template>

  <div v-if="streamManager" class="user-video">
    <OvVideo :stream-manager="streamManager" />
    
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
      <el-button type="text" @click="state.popupVisible = true" class="user-name">{{ state.clientData.clientData }}</el-button>
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
    streamManager: Object
  },

  setup(props, { emit }) {
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

    const clickToMain = function () {
      state.popupVisible = false
      emit('toMain')
    }

    const clickOut = function () {
      state.popupVisible = false
      emit('unpublishMe')
    }

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
</style>
