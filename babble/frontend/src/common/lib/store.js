import { createStore } from "vuex";
import root from '@/views/main/store';
import auth from '@/views/main/store/auth';
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: { root,auth },
  plugins: [
    createPersistedState({paths:['auth']}),
  ]
}
);
