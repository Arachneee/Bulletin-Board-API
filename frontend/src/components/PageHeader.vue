<template>
  <header>
    <p>{{ name }}</p>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/board/list">게시판</router-link> |
      <router-link v-if="!isLogin" to="/login">로그인</router-link> |
      <router-link v-if="!isLogin" to="/member/write">회원가입</router-link> | 
      <a v-if="isLogin" v-on:click="logout()">로그아웃</a>
    </div>
  </header>
  <hr/>
</template>

<script>
import { useStore } from 'vuex'
import { computed, watch, ref } from 'vue'

export default {
  setup(){
        const store = useStore();
        const name = ref(computed(()=> store.state.loginName ));
        const isLogin = ref(computed(()=> store.state.isLogin ));    
        watch(() => store.state.loginName, (newLoginName) => {
          name.value = newLoginName;
        });
        watch(() => store.state.isLogin, (newIsLogin) => {
          isLogin.value = newIsLogin;
        });

        return { name, isLogin } 
  },
  method :{
    logout() {
      const store = useStore();
      () => store.commit('setLogout');
      this.name = computed(()=> store.state.loginName );
      this.isLogin = computed(()=> store.state.isLogin ); 
    }
  },
    computed:{
    check_snackbar(){return this.$store.getters.get_snackbar}
  },
  watch:{
    check_snackbar(val){
      this.snackbar = val
    }
  },

}
</script>

<style scoped>

</style>