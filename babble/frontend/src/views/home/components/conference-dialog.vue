<template>
  <el-dialog title="Babble" v-model="state.dialogVisible" @close="handleClose">
    <el-row class="description">
      <el-col :offset="1"
        ><h2>방 제목 : {{ state.title }}</h2></el-col
      >
      <el-col :offset="1"
        ><h2>방 설명 : {{ state.content }}</h2></el-col
      >
      <el-col :offset="1"
        ><h2>방송 시작 시간 : {{ state.createTime }}</h2></el-col
      >
      <el-col :offset="1"
        ><p>아래와 같은 화면으로 방에 입장될 예정입니다.</p></el-col
      >

      <el-col :offset="3" :span="18"><div class="testCam"></div></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickEnterRoom"
          >방 입장하기</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "conference-dialog",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    roomId: {
      type: Number
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      dialogVisible: computed(() => props.open),
      title: "",
      content: "",
      createTime: ""
    });

    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then(function(stream) {
          var video = document.getElementById("video");
          video.srcObject = stream;
          video.play();
        });
    }
    watch(
      () => state.dialogVisible,
      (dialogVisible, prev) => {
        const payload = {
          roomId: props.roomId
        };
        store.commit("root/startSpinner");
        store
          .dispatch("root/requestRoomDialog", payload)
          .then(function(result) {
            state.title = result.data.title;
            state.content = result.data.content;
            state.createTime =
              result.data.createTime.slice(0, 10) +
              " " +
              result.data.createTime.slice(11, 19);
            store.commit("root/endSpinner");
          })
          .catch(function(err) {
            store.commit("root/endSpinner");
            alert(err + "!!!!!!");
          });
      }
    );

    const clickEnterRoom = function() {
      router.push({
        name: "conference-detail",
        params: {
          conferenceId: props.roomId
        }
      });
    };

    const handleClose = function() {
      emit("closeConferenceDialog");
    };

    return { state, clickEnterRoom, handleClose };
  }
};
</script>

<style>
.testCam {
  border-radius: 4px;
  min-height: 300px;
  background-color: grey;
}
.description h2 {
  padding: 0;
  margin-top: 0;
}
</style>
