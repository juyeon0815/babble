<template>
  <el-dialog title="Babble" v-model="state.dialogVisible" @close="handleClose">
    <el-row>
      <el-col :offset="1"><h2>방 제목 : </h2></el-col>
      <el-col :offset="1"><h2>방 설명 : </h2></el-col>
      <el-col :offset="1"><h2>방송 시작 시간 : </h2></el-col>
      <el-col :offset="1"><p>아래와 같은 화면으로 방에 입장될 예정입니다.</p></el-col>
      <el-col :offset="3" :span="18"><div class="testCam"></div></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickEnterRoom">방 입장하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'conference-dialog',
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
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      dialogVisible: computed(() => props.open),
    })

    const clickEnterRoom = function () {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: props.roomId
        }
      })
    }


    const handleClose = function () {
      emit('closeConferenceDialog')
    }

    return { state, clickEnterRoom, handleClose }
  }
}

</script>

<style>
  .testCam {
    border-radius: 4px;
    min-height: 300px;
    background-color: grey;
  }
</style>
