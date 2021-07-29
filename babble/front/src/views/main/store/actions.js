// API
import $axios from 'axios'

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

export function requestEmailConfirm ({ state }, payload) {
  console.log('requestEmailConfirm', state, payload)
  return $axios.post('/users/emailConfirm', payload)
}

export function requestRoomAllBest ({ state }) {
  return $axios.get('/room/all/best/1')
}

export function requestRoomAllRecent ({ state }) {
  return $axios.get('/room/all/recent/1')
}

export function requestRoomCategoryOrder ({ state }, payload) {
  return $axios.get(`/room/${payload.linkName}/${payload.orderName}/${payload.pageNum}`)
}

export function requestRoomSearch ({ state }, payload) {
  return $axios.get(`/room/${payload.searchName}/${payload.pageNum}`)
}

export function requestLogout ({ state }) {
  console.log('requestLogout', state)
  $axios.defaults.headers.common['Authorization'] = undefined
  localStorage.removeItem('jwt')
  return
}

export function requestUserInfo ({ state }, payload) {
  console.log('requestUserInfo', state)
  console.log('payload:', payload)
  const url = '/users/me'
  return $axios.get(url, {
    headers: {
        Authorization: `Bearer ${payload}`
    }
  })
}

export function requestPasswordCheck ({ state }, payload) {
  console.log('requestPasswordCheck', state, payload)
  return $axios.post('/users/passwordCheck', payload)
}
