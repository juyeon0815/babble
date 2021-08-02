<template>
  <el-dialog title="회원가입" v-model="state.dialogVisible" @close="handleClose" >
    <el-form :model="state.form" :rules="state.rules" ref="joinForm" :label-position="state.form.align" @change="isValid">
      <el-form-item prop="email" label="이메일" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
        <el-button type="primary" @click="checkEmail">중복체크</el-button>
        <el-button type="default" @click="checkConfirm" :disabled="!state.isOnlyEmail">인증하기</el-button>
      </el-form-item>
      <el-form-item prop="emailConfirm" label="이메일인증번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.emailConfirm" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm" label="비밀번호 확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordConfirm" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickJoin" :disabled="!state.isVal">회원가입</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'join-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운트 이후 바인딩
    const joinForm = ref(null)

    // Element UI Validator
    // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨

    const state = reactive({
      form: {
        email: '',
        emailConfirm: '',
        password: '',
        passwordConfirm: '',
        align: 'left'
      },
      rules: {
        email: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
        ],
        emailConfirm: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (value === state.authNum) {
                callback()
              } else {
                callback(new Error('인증번호가 일치하지 않습니다.'))
              }
            }
          }
        ],
        password: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          { min: 9, message: '최소 9글자를 입력해야 합니다.', trigger: 'blur' },
          { max: 16, message: '최대 16자까지 입력 가능합니다.', trigger: 'blur' },
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/.test(value)) {
                callback()
              } else {
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.'))
              }
            }
          }
        ],
        passwordConfirm: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
          { min: 9, message: '최소 9글자를 입력해야 합니다.', trigger: 'blur' },
          { max: 16, message: '최대 16자까지 입력 가능합니다.', trigger: 'blur' },
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/.test(value)) {
                callback()
              } else {
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.'))
              }
            }
          },
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (value === state.form.password) {
                callback()
              } else {
                callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
              }
            }
          }
        ]
      },
      isVal: false,
      authNum: '',
      isOnlyEmail: false,
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    const isValid = function () {
      joinForm.value.validate((valid) => {
        if (valid) {
          state.isVal = true
        } else {
          state.isVal = false
        }
      })
    }

    const checkEmail = function () {
      store.dispatch('root/requestCheckEmail', state.form.email)
      .then(function (result) {
        if (result.status == 200) {
          alert('사용가능한 이메일입니다.')
          state.isOnlyEmail = true
        } else {
          alert('alert')
        }
      }).catch(function (err) {
        alert(err)
        state.isOnlyEmail = false
      })
    }

    const checkConfirm = function () {
      alert('인증번호가 해당 메일로 전송되었습니다.')
      store.dispatch('root/requestEmailConfirm', state.form.email)
      .then(function (result) {
        console.log(result)
        console.log(result.data.message)
        state.authNum = result.data.message
      }).catch(function (err) {
        alert(err)
      })
    }

    const clickJoin = function () {
      // 유효성 검사 후 -> 로그인 API 호출 or 경고창 표시
      joinForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          store.dispatch('root/requestJoin', { email: state.form.email, password: state.form.password })
          .then(function (result) {
            alert('회원가입이 완료되었습니다.')
            emit('closeJoinDialog')
          })
          .catch(function (err) {
            alert(err)
          })
        } else {
          alert('회원가입에 실패하였습니다.')
        }
      });
    }

    const handleClose = function () {
      state.form.email = ''
      state.form.emailConfirm = ''
      state.form.password = ''
      state.form.passwordConfirm = ''
      emit('closeJoinDialog')
    }

    return { joinForm, state, isValid, checkEmail, checkConfirm, clickJoin, handleClose }
  }
}
</script>

<style>

</style>