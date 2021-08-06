<template>
  <el-tabs stretch="true" type="card" @tab-click="handleClick">
    <el-tab-pane label="참여자 음성설정">
      <h3>Speaker</h3>
      <div v-for="sub in state.subs">
        이름 : {{ JSON.parse(sub.stream.connection.data).clientData }} 이름 :
        영상 : {{ sub.stream.connection.stream.videoActive }} 음성 :
        {{ sub.stream.connection.stream.audioActive }}
      </div>
      <h3>Listener</h3>
    </el-tab-pane>
    <el-tab-pane label="채팅">
      <Chats />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { computed, reactive, onMounted, onUnmounted } from "vue";
import Chats from "./chats";
import { useStore } from "vuex";

export default {
  name: "sidebar",
  components: {
    Chats
  },
  setup() {
    const store = useStore();

    const state = reactive({
      subs: computed(() => store.getters["root/getSubscribers"])
    });

    return { state };
  }
};
</script>

<style></style>
