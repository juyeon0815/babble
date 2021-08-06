<template>
  <div class="header-space"></div>
  <div class="head-label">베스트 라이브</div>
  <!-- {{ state.bestRoomList }}
  {{ state.recentRoomList }} -->

  <!-- <el-carousel trigger="click" height="400px">
    <el-carousel-item v-for="item in state.carouselCount" :key="item">
      <el-row class="conference-row">
        <Conference
          v-for="i in 5"
          :key="i"
          v-cloak
          :roomInfo="state.bestRoomList[i + 5 * (item - 1) - 1]"
          @click="
            clickConference(state.bestRoomList[i + 5 * (item - 1) - 1].id)
          "
        />
      </el-row>
    </el-carousel-item>
  </el-carousel> -->

  <!-- <el-row class="conference-row">
    <Conference
      v-for="i in 10"
      :key="i"
      v-cloak
      :roomInfo="state.bestRoomList[i - 1]"
      @click="clickConference(state.bestRoomList[i - 1].id)"
    />
  </el-row> -->

  <div class="head-label">최신 라이브</div>
  <el-row class="conference-row">
    <Conference
      v-for="i in state.count"
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
      count: 0,
      carouselCount: 1,
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
        state.bestRoomList = result.data;
        if (result.data.length > 5) {
          state.carouselCount = 2;
        }
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
        state.count = result.data.length;
        state.recentRoomList = result.data;
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
