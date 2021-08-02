<template>
  <el-dialog custom-class="join-dialog" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="joinForm" :label-position="state.form.align">

      <el-form-item prop="email" label="이메일" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
        <el-button @click="checkEmail">인증하기</el-button>
      </el-form-item>
       <el-form-item prop="emailConfirm" label="이메인인증번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.emailConfirm" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button  type="primary" @click="clickJoin" :disabled="state.isDisabled">가입하기</el-button>
      </span>
      <the-loader v-if="state.isLoading"></the-loader>
    </template>
  </el-dialog>
</template>
<style>
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

import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'join-dialog',
  components:{
    TheLoader
  },
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const joinForm = ref(null)
    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */

    const state = reactive({
      isLoading : false,
      isDisabled : true,
      isDisabled: computed(()=>{
        if(state.form.email.length!=0 && state.form.password.length!=0
      && state.form.emailConfirm.length!=0 ) return state.isDisabled=false;
      else return state.isDisabled=true;
      }),
      form: {
        email:'',
        emailConfirm:'',
        authNum:'',
        password: '',
        align: 'left'
      },
      rules: {
        email: [
          { required: true, message: '필수로 입력해주세요.', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '필수로 입력해주세요.', trigger: 'blur' },
          {message: '최소 9글자를 입력해야 합니다.', min:9, trigger:'blur'},
          {message: '최대 16자까지 입력 가능합니다.', max:16, trigger:'blur'},
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
        ],
        emailConfirm:[
          {required: true, message: '인증번호를 입력해주세요.', trigger: 'blur'},
          {trigger: 'blur',
          validator (rule, value, callback) {
              if (value==state.authNum) {
                callback();
              } else {
                callback(new Error('인증번호가 일치하지않습니다. 다시 입력해주세요'));
              }
            }
          }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickJoin = function () {
      // 회원가입 버튼 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      joinForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          store.dispatch('root/requestJoin', {
            email: state.form.email, password: state.form.password })
          .then(function (result) {
            alert("회원 가입이 완료되었습니다.")
            emit('closeJoinDialog')
          })
          .catch(function (err) {
            state.form.id = ''
            state.form.emailConfirm=''
            state.form.password = ''
          })
        } else {
          alert('Validate error!')
        }
      });
    }

    const handleClose = function () {
      state.form.email = ''
      state.form.password = ''
      state.form.emailConfirm=''
      emit('closeJoinDialog')
    }

    const checkEmail = function(){ //이메일 인증
      alert("인증번호 전송이 완료되었습니다.")
      store.dispatch('root/emailConfirm', state.form.email)
      .then(function(result){
          console.log(result)
          console.log(result.data.message)
          state.authNum = result.data.message;
      }).catch(function (error){
        console.log(error)
      })
    }

    return { joinForm, state, clickJoin, handleClose, checkEmail }
  }
}
</script>
