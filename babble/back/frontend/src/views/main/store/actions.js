// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

export function requestJoin ({ state }, payload) { //회원가입
  console.log('requestJoin', state, payload)
  return $axios.post('/users/join', payload)
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

export function requestCheckId({ state }, payload) {
  console.log('requestCheckId', state, payload)
  const url = '/users/'
  let body = payload;
  return $axios.get(url+body)
}

export function emailConfirm({state},payload){ //이메일 인증
  console.log('emailConfirm',state,payload)
  return $axios.post('/users/emailConfirm',payload)
}
