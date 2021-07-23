// API
import $axios from 'axios'
import { storeKey } from 'vuex'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  return $axios.post('/auth/login', payload)
}
