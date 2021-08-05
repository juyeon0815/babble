
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from '../lib/store'
// import config from '../config'

const BASE_URL = '/api/v1'
const DEFAULT_ACCEPT_TYPE = 'application/json'

axios.defaults.baseURL = BASE_URL
axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE

axios.interceptors.request.use(
    config => {
        store.commit('root/startSpinner');
        return config;
    },
    error => {
        store.commit('root/startSpinner');
        alert('데이터 요청 실패');
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    response => {
        store.commit('root/endSpinner');
        return response;
    },
    error => {
        store.commit('root/endSpinner');
        alert('데이터 응답 실패');
        return Promise.reject(error);
    }
)


export default { VueAxios, axios }
