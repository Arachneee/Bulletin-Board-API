<template>
  <h2>회원 가입</h2>
  <div class="board-detail">
    <div class="board-contents">
    <div id="loginForm">
        <form @submit.prevent="fnMemberWrite">
          <p>
            <input class="w3-input" name="loginId" placeholder="Enter ID" v-model="loginId" @input="onLoginChange">
          </p>
          <p>
            <button type="button" class="w3-button w3-blue w3-round" v-on:click="fnIdValidate" v-bind:disabled="loginIdOk">아이디 중복 확인</button>
          </p>
          <p>
            <input name="name" class="w3-input" placeholder="Enter name" v-model="name" @input="onNameChange">
          </p>
          <p>
            <button type="button" class="w3-button w3-blue w3-round" v-on:click="fnNameValidate" v-bind:disabled="nameOk">닉네임 중복 확인</button>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="Enter password" v-model="password" type="password">
          </p>
          <p>
            <input name="passwordRe" class="w3-input" placeholder="Enter password again" v-model="passwordRe" type="password">
          </p>
          
          <p>
            <button type="submit" class="w3-button w3-green w3-round">회원가입</button>
          </p>
        </form>
      </div>
    </div>
    </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      loginId: '',
      name: '',
      password: '',
      passwordRe: '',
      loginIdOk: false,
      nameOk: false,
      passwordOk: false
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      this.loginId = this.$route.query.loginId
      this.name = this.$route.query.name
      this.password = this.$route.query.password
      this.passwordRe = this.$route.query.passwordRe
    },
    fnView() {
      this.$router.push({
        path: '/member/write'
      })
    },
    fnMemberWrite() {
      if (!this.loginIdOk || !this.nameOk) {
        alert('중복 체크해주세요.')
      } else if (this.password !== this.passwordRe) {
        alert('비밀번호가 일치하지 않습니다.')
        this.fnView()
      } else {
        this.form = {
          "loginId": this.loginId,
          "name": this.name,
          "password": this.password
        }
        this.$axios.post(this.$serverUrl + '/members', this.form)
          .then((res) => {
            console.log('patch 응답완료' + res.data)
            alert('회원 가입되었습니다.')
            this.$router.push({
              path: '/'
            })  
            }).catch(() => {
              alert('다시 확인해주세요.')
            this.fnView()
          })
      }
    },
    fnList() {
      this.$router.push({
        path: '/board/list'
      })
    },
    fnIdValidate() {
      this.form = {
        "loginId": this.loginId,
      }
      this.$axios.post(this.$serverUrl + '/members/loginId', this.form)
          .then(() => {
            alert('사용할 수 있는 아이디 입니다.')
            this.loginIdOk = true
            this.fnView()
            }).catch(() => {
                alert('중복된 아이디 입니다.')
              this.loginIdOk = false
              this.fnView()
          })
    },
    fnNameValidate() {
      this.form = {
        "loginId": this.loginId,
      }
      this.$axios.post(this.$serverUrl + '/members/name', this.form)
          .then(() => {
              alert('사용할 수 있는 닉네임 입니다.')
              this.nameOk = true
              this.fnView()
              }).catch(() => {
                alert('중복된 닉네임 입니다.')
              this.nameOk = false
              this.fnView()
          })
    },
    onLoginChange(){
      this.loginIdOk=false
    },
    onNameChange(){
      this.nameOk=false
    },
  }
}
</script>
<style scoped>

</style>