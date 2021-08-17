// API
import axios from 'axios'
import $axios from 'axios'


// 방 생성, 정보(카테고리, 검색), 대기실
export function requestRoomCreate ({ state }, payload) {
  console.log('requestRoomCreate', state, payload)
  return $axios.post('/room/create', payload)
}

export function requestRoomDialog ({ state }, payload) {
  return $axios.get(`/room/${payload.roomId}`)
}

// 방 입장, 퇴장
export function requestRoomEnter ({ state }, payload) {
  return $axios.post('room/enter', payload)
}

export function requestRoomExit ({ state }, payload) {
  return $axios.patch('room/exit', payload)
}

export function requestRoomDelete({ state }, payload) {
  console.log("DDDDDDDDDDD");
  console.log(payload);
  return $axios.post(`room/${payload.roomId}`, payload.maxViewers)
}

export function requestRoomHost ({ state }, roomId) {
  return $axios.get(`room/host/${roomId}`)
}

export function requestHashtagEmail ({ state },hashtag) {
  return $axios.get(`room/sendEmail`,hashtag)
}

export async function requestRandomName ({ state }) {
  return await $axios.get(`room/random`)
}
