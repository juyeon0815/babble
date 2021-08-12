import { createStore } from "vuex";
import root from '@/views/main/store';
import auth from '@/views/main/store/auth';
import menu from '@/views/main/store/menu';
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: { root, auth, menu },
  plugins: [
    createPersistedState({
      paths:['auth', 'menu']
    }),
  ]
}
);
