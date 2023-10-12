<template>
  <div class="board-detail">
    <div class="board-contents">
      <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
      <input label="File input" name="images" @change="handleFileChange" type="file" multiple accept="image/*" ref="serveyImage">
      <textarea id="" cols="30" rows="10" v-model="content" class="w3-input w3-border" style="resize: none;"></textarea>
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

      title: '',
      content: '',
      images: '',
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      this.title = this.$route.query.title
      this.content = this.$route.query.content
    },
    fnList() {
      delete this.requestBody.idx
      delete this.requestBody.title
      delete this.requestBody.content

      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnView(idx) {
      delete this.requestBody.title
      delete this.requestBody.content

      this.requestBody.idx = idx
      console.log('fnView 실행')
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnSave() {
      var formData = new FormData();
      formData.append('title', this.title)
      formData.append('content', this.content)

      console.log("이미지 개수 : " + this.images.length)
      
      if (this.images.length > -1) {
        for (let i =0; i < this.images.length; i++) {
          const imageForm = this.images[i]

          formData.append('images', imageForm)
        }
      }

      if (this.idx === undefined) {
        this.$axios.post(this.$serverUrl + '/posts', formData)
            .then(() => {
            alert('글이 저장되었습니다.')
            this.fnList()
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })

      } else {
        this.$axios.patch(this.$serverUrl + '/posts' + '/' + this.idx, this.form)
            .then((res) => {

            console.log('patch 응답완료' + res.data)
            alert('글이 수정되었습니다.' + this.idx)
            this.fnView(this.idx)
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
      }

    },
    handleFileChange() {
      this.images = this.$refs.serveyImage.files
    }
  }
}
</script>
<style scoped>

</style>