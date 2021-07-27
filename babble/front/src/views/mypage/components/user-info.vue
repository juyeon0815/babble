<template>
  <h1>회원정보</h1>
  <div>
    <h4>{{state.email}}</h4>
    <h5>프로필 사진 변경</h5>
    <div>
      <div>이미지 찾기</div>
      <input type="file">
      <!-- <button>적용</button> -->
    </div>
  </div>
  <div>
    <hr>
    <h5>비밀번호 변경</h5>
    기존 비밀번호: <input type="password" v-model="state.password">
    <button @click="checkPassword">확인</button><br>
    새로운 비밀번호: <input type="password" v-model="state.newPassword"><br>
    새로운 비밀번호 확인: <input type="password" v-model="state.newPasswordConfirm"><br>
    <button>제출</button>
  </div>
  <div>
    <hr>
    <h5>회원 탈퇴</h5>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'UserInfo',

  setup (props, {emit}) {
    const store = useStore()
    const state = reactive({
      email: computed(() => {
        return store.getters['root/getEmail']
      }),
      profile: computed(() => {
        return store.getters['root/getProfile']
      }),
      password: '',
      newPassword: '',
      newPasswordConfirm: ''

    })
    store.dispatch('root/requestUserInfo', localStorage.getItem('jwt'))
    .then((res)=> store.commit('root/setEmail', res.data.email))
    // .then(function (result) {
    //   console.log('여기야 이걸 확인해2')
    //   console.log(result)
    //   console.log(result.data.email)
    //   state.email = result.data.email
    // })

    console.log('내 이메일 들어왔니')
    console.log(state.email)

    const checkPassword = function () {
      store.dispatch('root/requestPasswordCheck', {email: state.email, password: state.password })
      .then(function (result) {
        console.log(result.data.message)
        // alert('')
      })
      .catch(function (err) {
        console.log(err)
      })

    }



  return {state, checkPassword}
  }
}
</script>

<style>

</style>
