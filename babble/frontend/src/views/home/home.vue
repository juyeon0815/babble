<template>
  <div class="header-space"></div>
  <div class="head-label">베스트 라이브</div>
  
  <el-row class="conference-row">
    <Conference
      v-for="i in state.bestRoomCount"
      :key="i"
      v-cloak
      :roomInfo="state.bestRoomList[i - 1]"
      @click="clickConference(state.bestRoomList[i - 1].id)"
    />
  </el-row>

  <div class="head-label">최신 라이브</div>
  <el-row class="conference-row">
    <Conference
      v-for="i in state.recentRoomCount"
      :key="i"
      v-cloak
      :roomInfo="state.recentRoomList[i - 1]"
      @click="clickConference(state.recentRoomList[i - 1].id)"
    />
  </el-row>

  <ConferenceDialog
    :open="state.conferenceDialogOpen"
    :roomId="state.conferenceDialogNum"
    @closeConferenceDialog="onCloseConferenceDialog"
  />
</template>

<style>
  .header-space {
    height: 400px;
    background-color: #d3c4ed;
  }
  .head-label {
    font-size: 20px;
    font-weight: bold;
    margin: 20px 0 20px 50px;
  }
  .conference-row {
    justify-content: center;
    align-items: center;
    margin-top: 15px;
    margin-left: 5%;
    width: 90%;
  }
</style>
<script>
import { reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import Conference from "./components/conference";
import ConferenceDialog from "./components/conference-dialog";

export default {
  name: "Home",

  components: {
    Conference,
    ConferenceDialog
  },

  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      bestRoomList: [],
      recentRoomList: [],
      bestRoomCount: 0,
      recentRoomCount: 0,
      conferenceDialogOpen: false,
      conferenceDialogNum: 0
    });

    const payloadBest = {
      linkName: "all",
      orderName: "best",
      pageNum: 1
    };
    store
      .dispatch("root/requestRoomCategoryOrder", payloadBest)
      .then(function(result) {
        state.bestRoomList = result.data
        state.bestRoomCount = result.data.length
      })
      .catch(function(err) {
        alert(err);
      });

    const payloadRecent = {
      linkName: "all",
      orderName: "recent",
      pageNum: 1
    };
    store
      .dispatch("root/requestRoomCategoryOrder", payloadRecent)
      .then(function(result) {
        state.recentRoomList = result.data
        state.recentRoomCount = result.data.length
      })
      .catch(function(err) {
        alert(err);
      });

    const clickConference = function(id) {
      state.conferenceDialogOpen = true;
      state.conferenceDialogNum = id;
    };

    const onCloseConferenceDialog = function() {
      state.conferenceDialogOpen = false;
    };

    onMounted(() => {
      store.commit("root/setMenuActiveMenuName", "home");
    });

    return { state, clickConference, onCloseConferenceDialog };
  }
};
</script>
