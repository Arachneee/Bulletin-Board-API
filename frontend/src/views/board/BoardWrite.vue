<template>
  <div class="board-detail">
    <div class="board-contents">
      <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
      <input label="File input" name="images" @change="handleFileChange" type="file" multiple accept="image/*" ref="serveyImage">
      <div v-if="imageUrls !== undefined">
        <div v-for="url in imageUrls" :key="url">
          <div v-if="!deleteImageUrls.includes(url)">
            <img :src="fnImageView(url)" width="400" height="300">&nbsp;
            <button type="button" class="w3-button w3-round w3-red" v-on:click="fnImageDelete(url)">X</button>
          </div>
        </div>
      </div>
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
      imageUrls: [],
      deleteImageUrls: [],
      a: ''
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      this.title = this.$route.query.title
      this.content = this.$route.query.content
      this.imageUrls = this.$route.query.imageUrls
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
      delete this.requestBody.imageUrls

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
      
      if (this.images.length > 0) {
        for (let i = 0; i < this.images.length; i++) {
          const imageForm = this.images[i]

          formData.append('images', imageForm)
        }
      }

      const deleteImageIds = this.deleteImageUrls.map((url) => url.slice(url.lastIndexOf("/") + 1))

      if (deleteImageIds.length > 0) {
        for (let i = 0; i < deleteImageIds.length; i++) {
          const imageId = deleteImageIds[i]

          formData.append('deleteImageIds', imageId)
        }
      }

      if (this.idx === undefined) {
        console.log("post로 전송")
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
        console.log("patch 전송")
        this.$axios.patch(this.$serverUrl + '/posts' + '/' + this.idx, formData)
            .then(() => {
            alert('글이 수정되었습니다.')
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
    },
    fnImageView(url) {
      return this.$serverUrl + url;
    },
    fnImageDelete(url) {
      this.deleteImageUrls.push(url)
    }

  }
}
</script>
<style scoped>

</style>