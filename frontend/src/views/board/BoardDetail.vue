<template>
  <div class="board-detail">
    <div class="board-contents">
      <h3>{{ title }}</h3>
      <div>
        <strong class="w3-large">글쓴이 : {{ name }}</strong>
        <br>
        <span>조회수 : {{ viewCount }}</span>
        <br>
        <span>작성일 : {{ createdDate }}</span>
      </div>
    </div>
    <div class="board-contents">
      <span>{{ content }}</span>
    </div>
    <div class="common-buttons">
    <div v-if="editButton">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">수정</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-red" v-on:click="fnDelete">삭제</button>&nbsp;
    </div>
    <br>
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
    </div>
  </div>
</template>

<script>
import dayjs from "dayjs";

export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: '',
      name: '',
      viewCount: 0,
      content: '',
      createdDate: '',
      editButton: false,
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      console.log('Detail의 fnGetView 호출')
      this.$axios.get(this.$serverUrl + '/posts/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        console.log('Detail의 fnGetView 응답')
        this.title = res.data.title
        this.name = res.data.name
        this.content = res.data.content
        this.createdDate = dayjs(res.data.createdDate).format("YYYY-MM-DD hh:ss")
        this.editButton = res.data.editButton,
        this.viewCount = res.data.viewCount
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnUpdate() {
      this.requestBody.title = this.title
      this.requestBody.content = this.content
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return

      this.$axios.delete(this.$serverUrl + '/board/' + this.idx, {})
          .then(() => {
            alert('삭제되었습니다.')
            this.fnList();
          }).catch((err) => {
        console.log(err);
      })
    }
  }
}
</script>
<style scoped>


</style>