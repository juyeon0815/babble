<template>
  <el-dialog title="로그인" v-model="state.dialogVisible" @close="handleClose">
    <el-form
      :model="state.form"
      :rules="state.rules"
      ref="loginForm"
      :label-position="state.form.align"
      @change="isValid"
    >
      <el-form-item
        prop="email"
        label="이메일"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="password"
        label="비밀번호"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.password"
          autocomplete="off"
          show-password
        ></el-input>
      </el-form-item>
    </el-form>
   <a id="custom-login-btn" href="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&response_type=code&client_id=229511140118-31d4vp160c7dd1ld4g27180fmq1qesg8.apps.googleusercontent.com&redirect_uri=http://localhost:8083/login/oauth2/code/google">
    <img
      :src="require('@/assets/images/btn_google_signin_dark_pressed_web.png')"
      width="222"
    />
  </a>
    <br>
   <a id="custom-login-btn" href="https://kauth.kakao.com/oauth/authorize?client_id=b571e5a822cacd3d5f0fdec309364338&redirect_uri=http://localhost:8083/oauth/callback/kakao&response_type=code">
    <img
      src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
      width="222"
    />
  </a>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickLogin" :disabled="!state.isVal"
          >로그인</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, watch} from "vue";
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
      formLabelWidth: "120px",
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
              alert(err);
            });
        } else {
          alert("Validate error!");
        }
      });
    };

    const handleClose = function() {
      state.form.email = "";
      state.form.password = "";
      emit("closeLoginDialog");
    };

  // 카카오 로그인 후 실행
  //(구글 로그인 시 해당 코드로 들어는 가나 auth/requestKakaoToken에서 걸려서 빠져나와 밑에 구글 코드 실행됨)
    let kakaoCode = new URL(window.location.href).searchParams.get("code");
    if(kakaoCode != null){
      console.log('카카오로그인 시도')
      store.dispatch("auth/requestKakaoToken", kakaoCode)
      .then(function(result){
        console.log("카카오 result: ", result.data.accessToken)
        console.log("카카오 email : ",result.data.email )
        localStorage.setItem("jwt", result.data.accessToken);
        store.commit("auth/setToken", result.data.accessToken);
        store.commit("auth/setEmail", result.data.email);
        store.commit("auth/setProvider","kakao"); // 로그아웃할때 방식 다 달라서 구분용
        router.push({
            path: "/"
        })
      }).catch(function(error){
        console.log(error)
      })
    }

  //구글 로그인 후 실행
  //카카오 로그인 시, 해당 코드에도 접근해 문제 발생 >> 구글에서 받아온 토큰이 있어야만 다음 코드 실행되게끔 처리
    let googleCode = new URL(window.location.href).searchParams.get("code");
    if(googleCode != null){
      console.log("구글로그인 시도");
      store.dispatch("auth/requestGoogleToken", googleCode)
      .then(function(result){
        console.log(result)
        if (result.data.idToken) {
          console.log("구글 result: ", result.data.idToken)
          console.log("구글 email : ",result.data.email )
          localStorage.setItem("jwt", result.data.idToken);
          store.commit("auth/setToken", result.data.idToken);
          store.commit("auth/setEmail", result.data.email);
          store.commit("auth/setProvider","google"); // 로그아웃할때 방식 다 달라서 구분용
        }
        router.push({
          path: "/"
        });
      }).catch(function(error){
        console.log(error)
      })
    }


    return { loginForm, state, isValid, clickLogin, handleClose};
  }
};
</script>

<style></style>
