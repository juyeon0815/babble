<template>
  <el-dialog custom-class="join-dialog" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="joinForm" :label-position="state.form.align">
      <el-form-item prop="position" label="직책" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.position" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="department" label="소속" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.department" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.id" autocomplete="off"></el-input>
        <el-button @click="CheckId">중복체크</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordCheck" label="비밀번호확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordCheck" autocomplete="off" show-password></el-input>
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
        if(state.form.id.length!=0 && state.form.password.length!=0
      && state.form.passwordCheck!=0 && state.form.name!=0 && state.form.password===state.form.passwordCheck) return state.isDisabled=false;
      else return state.isDisabled=true;
      }),
      form: {
        position:'',
        department:'',
        name :'',
        id: '',
        password: '',
        passwordCheck: '',
        align: 'left'
      },
      rules: {
        position: [
          { message: '최대 30자까지 입력 가능합니다.', trigger: 'blur', max:30 }
        ],
        department: [
          {  message: '최대 30자까지 입력 가능합니다.', trigger: 'blur', max :30   }
        ],
        name: [
          { required: 'true', message: '필수 입력 항목입니다.', trigger: 'blur', max:30 },
          {message: '최대 30자까지 입력 가능합니다.', max:30, trigger:'blur'}
        ],
        id: [
          { required: true, message: 'Please input ID', trigger: 'blur' },
          {message: '최대 16자까지 입력 가능합니다.', max:16, trigger:'blur'}
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' },
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
        passwordCheck:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          {message: '최소 9글자를 입력해야 합니다.', min:9, trigger:'blur'},
          {message: '최대 16자까지 입력 가능합니다.', max:16, trigger:'blur'},
          {message:'입력한 비밀번호와 일치하지 않습니다.', trigger:'blur', chcek:true},
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,16}$/.test(value)) {
                callback();
              } else {
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'));
              }
            }
          },
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (state.form.password==value) {
                callback();
              } else {
                callback(new Error('입력한 비밀번호와 일치하지 않습니다.'));
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
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      joinForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          state.isLoading = true;
          store.dispatch('root/requestJoin', { 
            position : state.form.position, department:state.form.department, name : state.form.name,
            id: state.form.id, password: state.form.password })
          .then(function (result) {
            state.isLoading = false;
            alert("회원 가입이 완료되었습니다.")
            emit('closeJoinDialog')
          })
          .catch(function (err) {
            setTimeout(()=>{
              state.isLoading=false;
              alert("회원 가입에 실패하였습니다.")
            },1000)
            state.form.position='',
            state.form.department='',
            state.form.name='',
            state.form.id = ''
            state.form.password = ''
            state.form.password=''
          })
        } else {
           setTimeout(()=>{
              state.isLoading=false;
              alert("회원 가입에 실패하였습니다.")
            },1000)
          alert('Validate error!')
        }
      });
    }

    const CheckId = function (){
      store.dispatch('root/requestCheckId',state.form.id)
      .then(function(result){
        if(result.status==200) alert("사용가능한 아이디입니다")
        else alert("이미 사용중인 아이디입니다.")
      }).catch(function (err){
        alert("다시확인해주세요")
      })
    }

    const handleClose = function () {
      state.form.position='',
      state.form.department='',
      state.form.name='',
      state.form.id = ''
      state.form.password = ''
      state.form.password=''
      emit('closeJoinDialog')
    }

    return { joinForm, state, clickJoin, handleClose, CheckId }
  }
}
</script>
