<template>
  <el-dialog title="로그인" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align"  @change="isValid">
      <el-form-item prop="email" label="이메일" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickLogin" :disabled="!state.isVal">로그인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'login-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    // 마운트 이후 바인딩
    const loginForm = ref(null)

    // Element UI Validators
    // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
    const state = reactive({
      form: {
        email: '',
        password: '',
        align: 'left'
      },
      rules: {
        email: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
        ]
      },
      isVal: false,
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      // setMyInfo: 0,
      setEmail: computed(() => {
        return store.getters['root/getEmail']
      }),
      setToken: computed(() => {
        return store.getters['root/getToken']
      }),
    })

    const isValid = function () {
      loginForm.value.validate((valid) => {
        if (valid) {
          state.isVal = true
        } else {
          state.isVal = false
        }
      })
    }


    const clickLogin = function () {
      //유효성 체크 후 -> 로그인 API 호출 or 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          store.dispatch('root/requestLogin', { email: state.form.email, password: state.form.password })
          .then(function (result) {
            console.log(result)
            localStorage.setItem('jwt', result.data.accessToken)
            // state.setMyInfo += 1
            // console.log(state.setMyInfo)
            console.log('바꾸기 전', state.setEmail)
            console.log('폼 이메일은', state.form.email)
            // state.setEmail = state.form.email
            store.commit('root/setEmail', state.form.email)
            console.log('바꾼 후', state.setEmail)
            console.log('바꾸기 전', state.setToken)
            store.commit('root/setToken', result.data.accessToken)
            console.log('바꾼 후', state.setToken)
            alert('로그인 성공')


            emit('closeLoginDialog')
            // router.go()
            window.location.reload()
          })
          // .then(function (result ){
          //   store.dispatch('root/requestUserInfo', localStorage.getItem('jwt'))

          //     console.log(res)})

          // })

          .catch(function (err) {
            console.log(err)
            state.form.email = ''
            state.form.password = ''
            alert(err)
          })
        } else {
          alert('Validate error!')
        }
      })
    }

    // watch(() => state.setMyInfo, (newValue, oldValue) => {
    //   console.log('새로운 값', newValue, '예전 값', oldValue)
    //   store.dispatch('root/requestUserInfo', localStorage.getItem('jwt'))
    //   .then((res)=> store.commit('root/setEmail', res.data.email))
    // })


    const handleClose = function () {
      state.form.email = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    return { loginForm, state, isValid, clickLogin, handleClose }
  }
}

</script>

<style>

</style>
