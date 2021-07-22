<template>
  <el-dialog custom-class="login-dialog" title="로그인" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>
    <div class="text" v-if="state.isCheck">아이디, 비밀번호를 다시확인해주세요</div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickLogin" :disabled="state.isDisabled">로그인</el-button>
      </span>
      <the-loader v-if="state.isLoading"></the-loader>
    </template>
  </el-dialog>
</template>
<style>
.text{
  color: red;
}
.login-dialog {
  width: 400px !important;
  height: 300px;
}
.login-dialog .el-dialog__headerbtn {
  float: right;
}
.login-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.login-dialog .el-form-item {
  margin-bottom: 20px;
}
.login-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.login-dialog .el-input__suffix {
  display: none;
}
.login-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.login-dialog .dialog-footer .el-button {
  width: 120px;
}
</style>
<script>

import TheLoader from './TheLoader'

import { reactive, computed, ref} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'login-dialog',
  components:{
    TheLoader
  },
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  // computed:{
  //   isDisabled(){ //false 버튼활성화, true 버튼비활성화
  //     console.log(this.state.form.id)
  //     if(this.state.form.id.length!=0 && this.state.form.password.length!=0) return this.isDisabled=false;
  //     else return this.isDisabled=true;
  //   }
  // },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null)
    const router = useRouter()
    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      isLoading : false,
      isLoading : false,
      isDisabled : true,
      isDisabled: computed(()=>{ //false 버튼활성화, true 버튼비활성화
      if(state.form.id.length!=0 && state.form.password.length!=0) return state.isDisabled=false;
      else return state.isDisabled=true;
    }),
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      rules: {
        id: [
          { required: true, message: '필수 입력 항목입니다', trigger: 'blur' },
          {message : '최대 16자까지 입력 가능합니다.',max:16, trigger:'blur'}
        ],
        password: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          {message : '최대 16자까지 입력 가능합니다.',max:16, trigger:'blur'},
          {message : '최소 9자를 입력해야 합니다.',min:9, trigger:'blur'},
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,16}$/.test(value)) {
                callback();
              } else {
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'));
              }
            }
          }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'

    })


    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          console.log(state.isLoading)
          state.isLoading = true;
          console.log(state.isLoading)
          store.dispatch('root/requestLogin', { id: state.form.id, password: state.form.password })
          .then(function (result) {
            localStorage.setItem("jwt",result.data.accessToken)
            console.log(result)
            state.isLoading = false;
            //alert("로그인성공");
            emit('closeLoginDialog')

            router.go(); //페이지 새로고침
          })
          .catch(function (err) {
            setTimeout(()=>{
              state.isLoading=false;
              state.isCheck = true;
            },1000)
            state.form.id = ''
            state.form.password = ''
          })
        } else {
          alert('Validate error!')
        }
      });
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    return { loginForm, state, clickLogin, handleClose }
  }
}
</script>
