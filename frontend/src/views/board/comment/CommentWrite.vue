<template>
  <div class="board-detail">
    <div class="board-contents">
      <h2>{{ idx }}</h2>
      <textarea id="" cols="30" rows="10" v-model="commentContent" class="w3-input w3-border" style="resize: none;">
      </textarea>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      commentId: this.$route.query.commentId,

      commentContent: '',
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      this.commentContent = this.$route.query.commentContent
    },
    fnList() {
      this.$router.push({
        path: '/board/detail',
        query: this.requestBody
      })
    },
    fnView(idx) {
      delete this.requestBody.content
      this.requestBody.idx = idx

      console.log('fnView 실행')
      this.$router.push({
        path: '/board/detail',
        query: this.requestBody
      })
    },

    fnSave() {
      // this.requestBody.commentContent = this.commentContent
      this.form = {
        "commentContent": this.commentContent,
      }
      this.$axios.patch(this.$serverUrl + '/posts/' + this.idx + '/comments/' + this.commentId, this.form)
        .then((res) => {
                console.log('patch 응답완료' + res.data)
                alert('댓글이 수정되었습니다.')
                this.fnView(this.idx)
            }).catch((err) => {
            if (err.message.indexOf('Network Error') > -1) {
                alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
            }
        })
    }
  }
}
</script>
<style scoped>

</style>