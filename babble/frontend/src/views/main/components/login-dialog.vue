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
    <a href="#" class="google-signup" @click="handleLogin">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" aria-hidden="true"><title>Google</title><g fill="none" fill-rule="evenodd"><path fill="#4285F4" d="M17.64 9.2045c0-.6381-.0573-1.2518-.1636-1.8409H9v3.4814h4.8436c-.2086 1.125-.8427 2.0782-1.7959 2.7164v2.2581h2.9087c1.7018-1.5668 2.6836-3.874 2.6836-6.615z"></path><path fill="#34A853" d="M9 18c2.43 0 4.4673-.806 5.9564-2.1805l-2.9087-2.2581c-.8059.54-1.8368.859-3.0477.859-2.344 0-4.3282-1.5831-5.036-3.7104H.9574v2.3318C2.4382 15.9832 5.4818 18 9 18z"></path><path fill="#FBBC05" d="M3.964 10.71c-.18-.54-.2822-1.1168-.2822-1.71s.1023-1.17.2823-1.71V4.9582H.9573A8.9965 8.9965 0 0 0 0 9c0 1.4523.3477 2.8268.9573 4.0418L3.964 10.71z"></path><path fill="#EA4335" d="M9 3.5795c1.3214 0 2.5077.4541 3.4405 1.346l2.5813-2.5814C13.4632.8918 11.426 0 9 0 5.4818 0 2.4382 2.0168.9573 4.9582L3.964 7.29C4.6718 5.1627 6.6559 3.5795 9 3.5795z"></path></g></svg>
            Google
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
import { reactive, computed, ref} from "vue";
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

  methods: {
    async handleLogin() {
      try {
        const GoogleUser = await this.$gAuth.signIn();
        if (!GoogleUser.isSignedIn()) throw new Error('로그인에 실패했습니다.');
        console.log(GoogleUser);
        console.log("1",GoogleUser.isSignedIn());
        console.log("2",GoogleUser.getBasicProfile().getName());
        console.log("3",GoogleUser.getBasicProfile().getImageUrl());
        console.log("4",GoogleUser.getBasicProfile().getEmail());
        console.log("5",GoogleUser.Zb.access_token);
        console.log("6",GoogleUser.Zb.idpId);
        useStore.dispatch("auth/requestUserInfo", GoogleUser.Zb.access_token)
      .then(function(result) {
        console.log(result);
      });
      } catch (e) {
        console.error(e);
      }
    },



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
              alert("로그인 성공");
              emit("closeLoginDialog");
              // router.go()
              // window.location.reload()
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


  // 카카오 로그인 후 딱 한번만 실행되어야한다. 위치가 여기 맞나유..?
    let code = new URL(window.location.href).searchParams.get("code");
    console.log(code);
    if(code!=null){
      store.dispatch("auth/requestToken", code)
      .then(function(result){
        console.log("result: ", result.data.accessToken)
        console.log("email : ",result.data.email )
        localStorage.setItem("jwt", result.data.accessToken);
        store.commit("auth/setToken", result.data.accessToken);
        store.commit("auth/setEmail", result.data.email);
        store.commit("auth/setProvider","kakao"); // 로그아웃할때 방식 다 달라서 구분용
        alert("로그인 성공");
        emit("closeLoginDialog");
      }).catch(function(error){
        console.log(error)
      })
    }

    return { loginForm, state, isValid, clickLogin, handleClose};
  }
};
</script>

<style></style>
