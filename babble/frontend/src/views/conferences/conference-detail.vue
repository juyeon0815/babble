<template>
  <el-container>
    <el-main class="video-area">
      <VideoSpace :roomTitle="state.roomTitle" />
    </el-main>
    <button class="sidebar-toggleBtn" @click="clickToggle">
      <i class="fas fa-bars"></i>
    </button>
    <el-aside class="side-bar">
      <Sidebar :roomTitle="state.roomTitle" />
    </el-aside>
  </el-container>
</template>

<style>
.side-bar {
  background-image: linear-gradient(-30deg, #9f05ff69 10%, #4a63cfc2 100%);
  height: 100vh;
}
.sidebar-toggleBtn {
  display: none;
  border: none;
  background: transparent;
  right: 10px;
  font-size: 24px;
  color: #341a63;
}
@media screen and (max-width: 500px) {
  .sidebar-toggleBtn {
    display: block;
    position: absolute;
    z-index: 99;
  }
  .side-bar {
    display: none;
  }
  .side-bar.active {
    display: block;
  }
  .video-area.disabled {
    width: 1px;
    height: 1px;
    margin: -1px;
    clip: rect(0, 0, 0, 0);
    overflow: hidden;
    padding: 0;
  }
}
</style>

<script>
import { reactive, onMounted, onUnmounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import VideoSpace from "./components/video-space";
import Sidebar from "./components/sidebar";

export default {
  name: "conference-detail",
  components: {
    VideoSpace,
    Sidebar
  },
  setup() {
    const store = useStore();
    const route = useRoute();

    const state = reactive({
      conferenceId: "",
      roomTitle: ""
    });
    store.commit("root/joinRoom", route.params.conferenceId);

    const clickToggle = function() {
      let menu = document.querySelector(".side-bar");
      menu.classList.toggle("active");

      let videoArea = document.querySelector(".video-area");
      videoArea.classList.toggle("disabled");
    };

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId;

      store
        .dispatch("root/requestRoomHost", route.params.conferenceId)
        .then(result => {
          state.roomTitle = result.data.title;
        })
        .catch(err => {});
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = "";
    });

    return { state, clickToggle };
  }
};
</script>
