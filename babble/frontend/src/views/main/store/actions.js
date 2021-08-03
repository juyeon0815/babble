// API
import axios from 'axios'
import $axios from 'axios'

// 회원가입, 로그인, 로그아웃
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

export function requestLogout ({ state }) {
  console.log('requestLogout', state)
  $axios.defaults.headers.common['Authorization'] = undefined
  localStorage.removeItem('jwt')
  return
}

// 마이페이지
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

export function requestUpdatePassword ({ state }, payload) {
  console.log('requestUpdatePassword', state, payload)
  return $axios.patch('/users/updatePassword', payload)
}

export function requestDeleteUser ({ state }, payload) {
  console.log('requestDeleteUser', state, payload)
  console.log(payload)
  return $axios.delete(`/users/${payload.email}`)
}

export function requestUserHashtagPush ({ state }, payload) {
  console.log('requestUserHashtagPush', state, payload)
  return $axios.post('/users/hashtag', payload)
}

export function requestUserHashtagDelete ({ state }, payload) {
  console.log('requestUserHashtagDelete', state, payload)
  return $axios.delete('/users/hashtag', {data: payload})
}

export function requestChangeAlarm ({ state }, payload) {
  console.log('requestChangeAlarm', state, payload)
  return $axios.patch('/users/hashtag', payload.email)
}

export function requestViewHistory1 ({ state }, payload) {
  console.log('requestViewHistory1', state, payload)
  return $axios.get(`/users/viewHistory/${payload.email}`)
}

export function requestViewHistory2 ({ state }, payload) {
  console.log('requestViewHistory2', state, payload)
  return $axios.get(`/users/createRoomHistory/${payload.email}`)
}

//수정 필요
export function requestUserProfile ({ state }, payload) {
  console.log('requestUserProfile', state, payload)
  return $axios.get(`/users/${payload.email}`)
}

export function requestUserHashtag ({ state }, payload) {
  console.log('requestUserHashtag', state, payload)
  return $axios.get(`/users/hashtag/${payload.email}`)
}

export function requestUpdateProfile ({ state }, payload) {
  console.log('requestUpdateProfile', state)
  console.log('payload:', payload)
  const url = '/users/upload'
  return $axios.post(url, payload, {
    headers: {
      'Content-Type' : 'multipart/form-data'
    }
  })
}

export function requestRoomCreate ({ state }, payload) {
  console.log('requestRoomCreate', state, payload)
  return $axios.post('/room/create', payload)
}
// 방 정보(카테고리, 검색), 대기실
export function requestRoomCategoryOrder ({ state }, payload) {
  return $axios.get(`/room/${payload.linkName}/${payload.orderName}/${payload.pageNum}`)
}

export function requestRoomSearch ({ state }, payload) {
  return $axios.get(`/room/${payload.searchName}/${payload.pageNum}`)
}

export function requestRoomDialog ({ state }, payload) {
  return $axios.get(`/room/${payload.roomId}`)
}