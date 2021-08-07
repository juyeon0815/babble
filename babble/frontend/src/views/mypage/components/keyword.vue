<template>
  <div class="tab">
    <div class="title">
      <h3>{{ state.email }}님의 키워드</h3>
    </div>
    <div class="inputGroup">
      <el-input
        placeholder="관심있는 키워드를 입력해주세요(최대 5개)"
        ref="saveTagInput"
        v-model="state.inputValue"
        @blur="handleInputConfirm"
        :disabled="state.count == 5"
        class="input"
      >
        <template #append>
          <el-button icon="el-icon-plus"></el-button>
        </template>
      </el-input>
    </div>
    <el-tag
      :key="tag"
      v-for="tag in state.userHashtags"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)"
      class="tag"
    >
      {{ tag }}
    </el-tag>
    <div>
      <h5 class="text">해당 키워드로 알림을 받아보시겠습니까?</h5>
      <el-switch
        v-model="state.alarmValue"
        active-text="On"
        inactive-text="Off"
        @change="clickAlarm"
      ></el-switch>
    </div>
  </div>
</template>

<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "Keyword",
  setup(props, { emit }) {
    const store = useStore();
    const saveTagInput = ref(null);

    onMounted(() => {
      // console.log(saveTagInput)
    });

    const state = reactive({
      email: computed(() => {
        return store.getters["auth/getEmail"];
      }),
      userHashtags: computed(() => {
        return store.getters["auth/getUserHashtag"];
      }),
      alarmValue: computed(() => {
        return store.getters["auth/getAlarm"];
      }),
      count: computed(() => {
        return store.getters["auth/getUserHashtagLength"];
      }),
      inputValue: ""
    });

    store
      .dispatch("auth/requestUserHashtag", { email: state.email })
      .then(function(result) {
        console.log("해시태그를 가져올거야");
        console.log(result.data[0]);
        if (result.data[0] !== null) {
          store.commit("auth/setUserHashtag", result.data);
        }
      });

    store
      .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
      .then(function(result) {
        console.log(result.data.alarm);
        store.commit("auth/setDefaultAlarm", result.data.alarm);
      });

    const handleClose = function(tag) {
      let hashtagIndex = state.userHashtags.indexOf(tag);
      store.commit("auth/setUserHashtagDelete", hashtagIndex);
      // console.log(tag, '태그 안에서 내용을 어떻게 찾지?')
      // console.log(typeof tag)
      store
        .dispatch("auth/requestUserHashtagDelete", {
          email: state.email,
          name: tag
        })
        .then(function(result) {
          console.log(result, "해시태그 db에서 삭제완료!");
        });
    };

    const handleInputConfirm = function() {
      if (state.inputValue == "") {
        alert("빈 키워드는 입력되지 않아요!");
      } else {
        store.commit("auth/setUserHashtagPush", state.inputValue);
        store
          .dispatch("auth/requestUserHashtagPush", {
            email: state.email,
            name: state.inputValue
          })
          .then(function(result) {
            console.log(result, "해시태그 db에 push!");
          });
        state.inputValue = "";
      }
    };

    const clickAlarm = function() {
      store.commit("auth/setAlarm");
      store
        .dispatch("root/requestChangeAlarm", { email: state.email })
        .then(function(result) {
          console.log(result, "알림 설정 상태가 바뀌었다!");
        });
    };

    return { state, saveTagInput, handleClose, handleInputConfirm, clickAlarm };
  }
};
</script>

<style>
.tab {
  margin-left: 60px;
}
.title {
  display: block;
  padding-bottom: 10px;
}
.inputGroup {
  display: block;
}
.input {
  width: 370px;
  padding-bottom: 20px;
}
/* .tag {
    padding-bottom: 20px;
  } */
.text {
  display: inline;
  padding-right: 20px;
}
</style>
