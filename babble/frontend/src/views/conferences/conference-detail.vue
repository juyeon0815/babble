<template>
  <el-container>
    <el-main>
      <VideoSpace 
        :roomTitle="state.roomTitle"
        :hostId="state.hostId"
      />
    </el-main>
    <el-aside class="side-bar">
      <Sidebar 
        :roomTitle="state.roomTitle"
        :hostId="state.hostId"
      />
    </el-aside>
  </el-container>
</template>

<style>
  .side-bar {
    background-color: #C1C8E4;
    height: 100vh;
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
      hostId: null,
      roomTitle: ""
    });
    store.commit("root/joinRoom", route.params.conferenceId);
    

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId;
      store.dispatch("root/requestRoomHost", route.params.conferenceId)
      .then(result => {
        state.hostId = result.data.hostId
        state.roomTitle = result.data.title
      })
      .catch(err => {
        console.log(err)
      })
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = "";
    });

    return { state };
  }
};
</script>
