<template>
  <div class="keyword-box">
    <div class="title">
      <h3>{{ state.email }}님의 키워드</h3>
    </div>
    <div class="inputGroup">
      <el-input
        placeholder="관심있는 키워드를 입력해주세요(최대 5개)"
        ref="saveTagInput"
        v-model="state.inputValue"
        @keyup.enter="handleInputConfirm"
        :disabled="state.count == 5"
        class="input"
        maxlength="15"
      >
        <template #append>
          <el-button icon="el-icon-plus" @click="handleInputConfirm"></el-button>
        </template>
      </el-input>
    </div>
    <div class="tag-group">
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
    </div>
    <div class="alarm">
      <h5 class="keyword-text">해당 키워드로 알림을 받아보시겠습니까?</h5>
      <el-switch
        v-model="state.alarmValue"
        active-text="On"
        inactive-text="Off"
        @change="clickAlarm"
        class="alarm-switch"
      ></el-switch>
    </div>
  </div>
  <div class="keyword-circle">
    <el-popover
      placement="top-start"
      title="Let's Ba:bble"
      trigger="hover"
      content="당신을 위한 오늘의 추천 키워드는 싸피입니다!"
       >
      <template #reference>
        <img class="keyword-profile" :src="require('@/assets/images/default_profile.png')" />
      </template>
    </el-popover>
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
      inputValue: "",
      provider: computed(() => {
        return store.getters["auth/getProvider"];
      }),
    });

    store
      .dispatch("auth/requestUserHashtag", { email: state.email })
      .then(function(result) {
        console.log("해시태그를 가져올거야");
        // console.log(result.data[0]);
        if (result.data[0] !== null) {
          store.commit("auth/setUserHashtag", result.data);
        }
      });


    if (state.provider == 'google' || state.provider == 'kakao') {
      store
        .dispatch("auth/requestSocialUserInfo", {email: state.email})
        .then(function (result) {
          console.log(result, '소셜 로그인 유저 정보 받아오기')
          store.commit("auth/setDefaultAlarm", result.data.alarm);
        })
    } else {
       store
        .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
        .then(function(result) {
          console.log(result.data.alarm);
          store.commit("auth/setDefaultAlarm", result.data.alarm)
        })
        .catch(function (err) {
          if (err) {
            console.log(err, '키워드에서 axios날리며 받은 캐치')
          }
        })
    }

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
        swal({
          text: "빈 키워드는 입력되지 않아요!",
          icon: "warning",
        });
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
        .dispatch("auth/requestChangeAlarm", { email: state.email })
        .then(function(result) {
          console.log(result, "알림 설정 상태가 바뀌었다!");
        });
    };

    return { state, saveTagInput, handleClose, handleInputConfirm, clickAlarm };
  }
};
</script>

<style>
.keyword-box {
  width: 40vw;
  margin-top: 8%;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.keyword-box .title {
  display: block;
  padding-bottom: 3%;
}

.keyword-box .inputGroup {
  display: flex;
  flex-direction: row;
  width: 100%;
}

.keyword-box .inputGroup .input {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  padding-bottom: 4%;
}

.keyword-box .tag-group {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: center;
}

.keyword-box .tag-group .tag {
  height: 5vh;
  margin-right: 1%;
  background-image: linear-gradient(130deg, #9f05ff69 20%, #4a63cfc2 100%);
  color: white;
  line-height: 35px;
}

.el-tag .el-tag__close {
  color: white;
}

.keyword-box .alarm {
  width: 80%;
  margin-top: 6%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.keyword-box .alarm .keyword-text {
  display: inline;
  padding-right: 3%;
  color: black;
}

.keyword-box .alarm .alarm-switch {
  display: inline;
  margin-bottom: 1%;
  color: purple;
}

.el-switch.is-checked .el-switch__core {
  background-color: rgba(113, 28, 250, 0.671);
  border-color: rgba(113, 28, 250, 0.671);
}

.el-switch__label.is-active {
  color: rgba(161, 28, 250, 0.692);
}

@keyframes rotate {
  from {
    -webkit-transform: rotate(0deg);
    -o-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  to {
    -webkit-transform: rotate(360deg);
    -o-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}

.keyword-circle {
  margin-left: 3%;
  border-radius: 50%;
}

.keyword-profile {
  /* position: absolute; */
  position: fixed;
  bottom: 3%;
  width: 15%;
  height: 25%;

  object-fit: cover;
  animation: rotate 3s infinite
}

@media screen and (max-width: 480px) {
  .keyword-box {
    width: 80vw;
  }

  .keyword-box .inputGroup .input {
    width: 90%;
    font-size: 11px;
  }

  .keyword-box .tag-group {
    margin-top: 5%;
  }

  .keyword-box .alarm {
    margin-top: 10%;
    display: flex;
    flex-direction: column;
  }

  .keyword-profile {
    position: fixed;
    bottom: 3%;
    width: 30%;
    height: 20%;
    border-radius: 30%;
    object-fit: cover;
    animation: rotate 3s infinite
  }

}
</style>
