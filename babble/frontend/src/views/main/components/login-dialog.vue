<template>
  <el-dialog
    title="Login"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="23%"
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
    <div id="msg" v-if="state.isCheck">아이디, 비밀번호를 다시확인해주세요</div>
    <el-button type="primary" round @click="clickLogin" :disabled="!state.isVal"
      >로그인</el-button
    >
    <div><el-link href="#" target="_blank">비밀번호 찾기</el-link></div>
    <template #footer>
      <span class="dialog-footer">
        <el-divider></el-divider>
        <div class="socialLogin">SNS 계정으로 로그인하기</div>

        <el-row :gutter="20">
          <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
          <el-col :span="6"
            ><div class="grid-content bg-purple">
              <a
                id="custom-login-btn"
                href="https://kauth.kakao.com/oauth/authorize?client_id=b571e5a822cacd3d5f0fdec309364338&redirect_uri=https://i5a308.p.ssafy.io/oauth/callback/kakao&response_type=code"
              >
                <img
                  :src="require('@/assets/images/kakaolink_btn_medium.png')"
                />
              </a></div
          ></el-col>
          <el-col :span="6"
            ><div class="grid-content bg-purple">
              <a
                id="custom-login-btn"
                href="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&response_type=code&client_id=229511140118-31d4vp160c7dd1ld4g27180fmq1qesg8.apps.googleusercontent.com&redirect_uri=https://i5a308.p.ssafy.io/login/oauth2/code/google"
              >
                <img
                  :src="require('@/assets/images/free-icon-google-plus.png')"
                />
              </a></div
          ></el-col>
          <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
        </el-row>
      </span>
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
.el-button.el-button--primary.is-disabled.is-round {
  background-color: #a8a0ff;
  border-color: #a0cfff00;
  width: 100%;
}
.el-link.el-link--default.is-underline {
  float: right;
  margin-top: 10px;
}
.el-dialog__footer {
  /* background-color: #9370db3b; */
  text-align: center;
}
div.socialLogin {
  text-align: center;
  font-size: 14px;
  color: gray;
  padding-bottom: 10px;
}
img {
  width: -webkit-fill-available;
  border-radius: 50%;
}
#msg {
  color: red;
  text-align: center;
  margin-bottom: 10px;
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
              alert("로그인 성공");
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

    return { loginForm, state, isValid, clickLogin, handleClose };
  }
};
</script>
