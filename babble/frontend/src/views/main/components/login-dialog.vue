<template>
  <el-dialog
    title="Login"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
    v-if="!state.findPassword"
  >
    <el-form
      :model="state.form"
      :rules="state.rules"
      ref="loginForm"
      :label-position="state.form.align"
      @change="isValid"
    >
      <el-form-item label="이메일" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="비밀번호" :label-width="state.formLabelWidth">
        <el-input
          v-model="state.form.password"
          autocomplete="off"
          show-password
          @keyup.enter="clickLogin"
        ></el-input>
      </el-form-item>
    </el-form>
    <div id="msg" v-if="state.isCheck">
      이메일, 비밀번호를 다시 확인해주세요
    </div>
    <el-button
      class="login-btn"
      type="primary"
      round
      @click="clickLogin"
      :disabled="!state.isVal"
      >로그인</el-button
    >
    <a href="#" @click="findPassword" class="find-pwd">비밀번호 찾기</a>
    <template #footer>
      <span class="dialog-footer">
        <el-divider></el-divider>
        <div class="social-login">SNS 계정으로 로그인하기</div>
        <div class="social-icon">
          <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=b571e5a822cacd3d5f0fdec309364338&redirect_uri=https://i5a308.p.ssafy.io/oauth/callback/kakao&response_type=code"
          >
            <img
              class="kakao-img"
              :src="require('@/assets/images/kakao-icon.png')"
            />
          </a>
          <a
            href="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&response_type=code&client_id=229511140118-31d4vp160c7dd1ld4g27180fmq1qesg8.apps.googleusercontent.com&redirect_uri=https://i5a308.p.ssafy.io/login/oauth2/code/google"
          >
            <img :src="require('@/assets/images/google-icon.png')" />
          </a>
        </div>
      </span>
    </template>
  </el-dialog>
  <el-dialog
    title="비밀번호 찾기"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
    v-if="state.findPassword"
  >
    <div class="findPassword">
      <el-input placeholder="Email" v-model="state.email"></el-input>
      <el-button class="checkEmail" type="primary" @click="checkEmail"
        >이메일확인</el-button
      >
    </div>
    <div id="msg" v-if="state.isCheck">이메일을 다시 확인해주세요</div>
    <template #footer>
      <el-button
        class="temporaryPassword"
        @click="temporaryPassword"
        :disabled="!state.isOnlyEmail"
        >임시비밀번호발급</el-button
      >
    </template>
  </el-dialog>
</template>

<style>
.el-dialog__header {
  /* background-color: #9370db3b; */
  text-align: center;
}
.el-dialog__body {
  /* background-color: #9370db3b; */
  padding: 30px;
  padding-top: 10px;
}
.login-btn {
  background-color: #a8a0ff;
  border-color: #a0cfff00;
  width: 100%;
}
.find-pwd {
  float: right;
  margin-top: 10px;
  text-decoration: none;
  color: grey;
}
.el-dialog__footer {
  /* background-color: #9370db3b; */
  text-align: center;
}
.social-login {
  text-align: center;
  font-size: 14px;
  color: gray;
  padding-bottom: 10px;
}
.social-icon {
  display: flex;
  justify-content: center;
  align-items: center;
}
.social-icon img {
  margin: 0 10px 0;
  width: 50px;
  height: 50px;
}
.social-icon .kakao-img {
  /* border-radius: 50%; */
}
#msg {
  color: red;
  margin-bottom: 10px;
}
.findPassword {
  display: flex;
}
.el-button.el-button--primary.checkEmail {
  margin-left: 5px;
}
.temporaryPassword {
  width: 100%;
}
</style>

<script>
import { reactive, computed, ref, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "login-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    // 마운트 이후 바인딩
    const loginForm = ref(null);

    // Element UI Validators
    // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
    const state = reactive({
      isOnlyEmail: false,
      findPassword: false,
      email: "",
      isCheck: false,
      form: {
        email: "",
        password: "",
        align: "left"
      },
      rules: {
        email: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" }
        ],
        password: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" }
        ]
      },
      isVal: false,
      dialogVisible: computed(() => props.open),
      formLabelWidth: "25%",
      setEmail: computed(() => {
        return store.getters["auth/getEmail"];
      }),
      setToken: computed(() => {
        return store.getters["auth/getToken"];
      })
    });

    const isValid = function() {
      loginForm.value.validate(valid => {
        if (valid) {
          state.isVal = true;
        } else {
          state.isVal = false;
        }
      });
    };

    const clickLogin = function() {
      //유효성 체크 후 -> 로그인 API 호출 or 경고창 표시
      loginForm.value.validate(valid => {
        if (valid) {
          console.log("submit");
          store
            .dispatch("auth/requestLogin", {
              email: state.form.email,
              password: state.form.password
            })
            .then(function(result) {
              console.log(result);
              localStorage.setItem("jwt", result.data.accessToken);
              console.log("이메일과 토큰 store에 저장");
              store.commit("auth/setEmail", state.form.email);
              store.commit("auth/setToken", result.data.accessToken);
              store.commit("auth/setProvider", "babble");
              emit("closeLoginDialog");
            })
            .catch(function(err) {
              console.log(err);
              state.form.email = "";
              state.form.password = "";
              state.isCheck = true;
              // alert(err);
            });
        } else {
          alert("Validate error!");
        }
      });
    };

    const handleClose = function() {
      state.form.email = "";
      state.form.password = "";
      state.isCheck = false;
      state.findPassword = false;
      emit("closeLoginDialog");
    };

    // 카카오 로그인 후 실행
    //(구글 로그인 시 해당 코드로 들어는 가나 auth/requestKakaoToken에서 걸려서 빠져나와 밑에 구글 코드 실행됨)
    let kakaoCode = new URL(window.location.href).searchParams.get("code");
    if (kakaoCode != null) {
      console.log("카카오로그인 시도");
      store
        .dispatch("auth/requestKakaoToken", kakaoCode)
        .then(function(result) {
          console.log("카카오 result: ", result.data.accessToken);
          console.log("카카오 email : ", result.data.email);
          localStorage.setItem("jwt", result.data.accessToken);
          store.commit("auth/setToken", result.data.accessToken);
          store.commit("auth/setEmail", result.data.email);
          store.commit("auth/setProvider", "kakao"); // 로그아웃할때 방식 다 달라서 구분용
          router.push({
            path: "/"
          });
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    //구글 로그인 후 실행
    //카카오 로그인 시, 해당 코드에도 접근해 문제 발생 >> 구글에서 받아온 토큰이 있어야만 다음 코드 실행되게끔 처리
    let googleCode = new URL(window.location.href).searchParams.get("code");
    if (googleCode != null) {
      console.log("구글로그인 시도");
      store
        .dispatch("auth/requestGoogleToken", googleCode)
        .then(function(result) {
          console.log(result);
          if (result.data.idToken) {
            console.log("구글 result: ", result.data.idToken);
            console.log("구글 email : ", result.data.email);
            localStorage.setItem("jwt", result.data.idToken);
            store.commit("auth/setToken", result.data.idToken);
            store.commit("auth/setEmail", result.data.email);
            store.commit("auth/setProvider", "google"); // 로그아웃할때 방식 다 달라서 구분용
          }
          router.push({
            path: "/"
          });
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    const findPassword = function() {
      console.log("하이하이");
      state.findPassword = true;
    };

    const checkEmail = function() {
      store
        .dispatch("auth/requestFindPassword", state.email)
        .then(function(result) {
          state.isCheck = false;
          state.isOnlyEmail = true;
        })
        .catch(function(err) {
          state.email = "";
          state.isCheck = true;
          state.isOnlyEmail = false;
        });
    };

    const temporaryPassword = function() {
      swal({
        text:
          "임시 비밀번호가 발급되었습니다. 해당 이메일로 전송된 비밀번호로 로그인해주세요!",
        icon: "success"
      });
      state.findPassword = false;
      store
        .dispatch("auth/requestTemporaryPassword", { email: state.email })
        .then(function(result) {
          state.isOnlyEmail = false;
        })
        .catch(function(err) {
          console.error(err);
        });
    };

    return {
      loginForm,
      state,
      isValid,
      clickLogin,
      handleClose,
      findPassword,
      checkEmail,
      temporaryPassword
    };
  }
};
</script>
