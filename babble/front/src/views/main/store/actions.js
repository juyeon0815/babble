// API
import $axios from 'axios'
import { storeKey } from 'vuex'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  return $axios.post('/auth/login', payload)
}

export function requestJoin ({ state }, payload) {
  console.log('requestJoin', state, payload)
  return $axios.post('/users/join', payload)
}

export function requestCheckEmail ({ state }, payload) {
  console.log('requestCheckEmail', state, payload)
  const url = '/users/'
  let body = payload
  return $axios.get(url+body)
}

export function emailConfirm ({ state }, payload) {
  console.log('emailConfirm', state, payload)
  return $axios.post('/users/emailConfirm', payload)
}

export function requestHome ({ state }) {
  console.log('requestHome', state)
  return $axios.get('/')
}
