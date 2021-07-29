import { createStore } from "vuex";
import root from '@/views/main/store';
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: { root },
  plugins: [
    createPersistedState(),
  ]
}
);
