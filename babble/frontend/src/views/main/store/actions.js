// API
import axios from 'axios'
import $axios from 'axios'



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




// 방 생성, 정보(카테고리, 검색), 대기실
export function requestRoomCreate ({ state }, payload) {
  console.log('requestRoomCreate', state, payload)
  return $axios.post('/room/create', payload)
}

export function requestRoomCategoryOrder ({ state }, payload) {
  return $axios.get(`/room/${payload.linkName}/${payload.orderName}/${payload.pageNum}`)
}

export function requestRoomSearch ({ state }, payload) {
  return $axios.get(`/room/${payload.searchName}/${payload.pageNum}`)
}

export function requestRoomDialog ({ state }, payload) {
  return $axios.get(`/room/${payload.roomId}`)
}
