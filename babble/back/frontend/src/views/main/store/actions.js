// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) { //로그인 //payload에 이메일, 비밀번호 // 백엔드에서 성공, 실패 값 반환
  console.log('requestLogin', state, payload)
  return $axios.post('/auth/login', payload)
}

export function requestJoin ({ state }, payload) { //회원가입 //payload에 이메일, 비밀번호 //백엔드에서 성공 실패 값 반환
  console.log('requestJoin', state, payload)
  return $axios.post('/users/join', payload)
}

export function emailConfirm({state},payload){ //이메일 인증 //payload에 이메일 // 백엔드에서 이메일 인증번호 반환
  console.log('emailConfirm',state,payload)
  return $axios.post('/users/emailConfirm',payload)
}

export function getInfo({state}, payload) {
   console.log('requestInfo', state)
  const url = '/users/me'
  return $axios.get(url, {
    headers: {
      Authorization : `Bearer ${payload}`
    }
  })
}

export function requestCheckEmail({ state }, payload) {
  console.log('requestCheckId', state, payload)
  const url = '/users/'
  let body = payload;
  return $axios.get(url+body)
}


