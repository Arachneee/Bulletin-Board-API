import { createStore } from "vuex";


export default createStore({
  state: {
    memberId: null,
    loginName: '익명',
    isLogin: false,
  },
  mutations: {
    setLogin(state, value) {
      state.memberId = value.id;
      state.loginName = value.name;
      state.isLogin = true;
      console.log("변경됨")
    },
    setLogout(state) {
      state.memberId = null;
      state.loginName = '익명';
      state.isLogin = false;
    }
  }
})