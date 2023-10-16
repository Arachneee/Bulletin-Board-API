<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button v-if="editButton" type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">수정</button>&nbsp;
      <button v-if="editButton" type="button" class="w3-button w3-round w3-red" v-on:click="fnDelete">삭제</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>&nbsp;
    </div>
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
    <div v-if="imageUrls !== undefined">
      <div v-for="url in imageUrls" :key="url">
        <img :src="fnImageView(url)" width="400" height="300">
      </div>
    </div>
    <div class="board-contents">
      <span>{{ content }}</span>
    </div>
    <h3>추천수 : {{ empathyCount }}</h3>&nbsp;
    <button v-if="empathyButton" type="button" class="w3-button w3-round w3-blue" v-on:click="fnPostEmpathy">추천</button>
    <button v-if="!empathyButton && !editButton" type="button" class="w3-button w3-round w3-red" v-on:click="fnPostEmpathyDelete">추천 취소</button>

    <div v-if="Object.keys(bestComment).length !== 0 && bestComment.empathyCount !== 0">
        <h5>베스트 댓글</h5>
        <table class="w3-table-all" border="1">
          <td @click="reply(bestComment.id)"><span>{{ formatDate(bestComment.createdDate) }}</span></td>
          <td @click="reply(bestComment.id)">{{ bestComment.name }}</td>
          <td @click="reply(bestComment.id)">{{ bestComment.content }}</td>
          <td @click="reply(bestComment.id)">{{ bestComment.empathyCount }}</td>
          <td v-if="bestComment.empathyButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathy(bestComment.id)">공감</button></td>
          <td v-if="!bestComment.empathyButton && !bestComment.editButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathyDelete(bestComment.id)">공감 취소</button></td>
          <td v-if="bestComment.editButton"><button type="button" class="w2-button w3-round w3-blue-gray" v-on:click="fnCmUpdate(bestComment.id, bestComment.content)">수정</button>&nbsp;</td>
          <td v-if="bestComment.editButton"><button type="button" class="w2-button w3-round w3-red" v-on:click="fnCmDelete(bestComment.id)">삭제</button>&nbsp;</td>
        </table>
      </div>
    <h4>댓글</h4>
    <select v-model="commentSort" @change="fnPage(0)">
        <option value="">- 선택 -</option>
        <option value="createdDate,desc">최신순</option>
        <option value="createdDate,asc">오래된순</option>
        <option value="empathiesCount,desc">추천순</option>
    </select>
        &nbsp;
    <table class="w3-table-all" border="1">
      <ul class="comment">
      <tr v-for="(row, id) in list" :key="id">
        <td @click="reply(row.id)"><span>{{ formatDate(row.createdDate) }}</span></td>
        <td @click="reply(row.id)">{{ row.name }}</td>
        <td @click="reply(row.id)">{{ row.content }}</td>
        <td @click="reply(row.id)">{{ row.empathyCount }}</td>
        <td v-if="row.empathyButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathy(row.id)">공감</button></td>
        <td v-if="!row.empathyButton && !row.editButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathyDelete(row.id)">공감 취소</button></td>
        <td v-if="row.editButton"><button type="button" class="w2-button w3-round w3-blue-gray" v-on:click="fnCmUpdate(row.id, row.content)">수정</button>&nbsp;</td>
        <td v-if="row.editButton"><button type="button" class="w2-button w3-round w3-red" v-on:click="fnCmDelete(row.id)">삭제</button>&nbsp;</td>

        <ul v-if="row.replies.length !== 0">
          <tr v-for="(r, i) in row.replies" :key="i">
            <td @click="reply(row.id)"><span>ㄴ {{ formatDate(r.createdDate) }}</span></td>
            <td @click="reply(row.id)">{{ r.name }}</td>
            <td @click="reply(row.id)">{{ r.content }}</td>
            <td @click="reply(row.id)">{{ r.empathyCount }}</td>
            <td v-if="r.empathyButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathy(r.id)">공감</button></td>
            <td v-if="!r.empathyButton && !r.editButton"><button  type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathyDelete(r.id)">공감 취소</button></td>
            <td ><button v-if="r.editButton" type="button" class="w2-button w3-round w3-blue-gray" v-on:click="fnCmUpdate(r.id, r.content)">수정</button>&nbsp;</td>
            <td ><button v-if="r.editButton" type="button" class="w2-button w3-round w3-red" v-on:click="fnCmDelete(r.id)">삭제</button>&nbsp;</td>
          </tr>
        </ul>
        <div v-if="replyId === row.id">
          <h4>대댓글 쓰기</h4>
          <textarea id="" cols="30" rows="3" v-model="commentContent" class="w3-input w3-border" style="resize: none;"></textarea>
          <div class="common-buttons">
            <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnReplySave(row.id)">쓰기</button>&nbsp;
          </div>
        </div>
    
      </tr>
    </ul>
    </table>

    <div class="pagination w3-bar w3-padding-16 w3-small">
      <span class="pg">
      <a href="javascript:;" @click="fnPage(0)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
      <a href="javascript:;" v-if="!cmFirst" @click="fnPage(`${cmNumber-1}`)"
         class="prev w3-button w3-bar-item w3-border">이전</a>
      <a class="prev w3-bar-item w3-border">{{ cmNumber + 1}} / {{ cmTotalPages }}</a>
      <a href="javascript:;" v-if="!cmLast"
         @click="fnPage(`${cmNumber+1}`)" class="next w3-button w3-bar-item w3-border">다음</a>
      <a href="javascript:;" @click="fnPage(`${cmTotalPages-1}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>
      </span>
    </div>
    <h4>댓글 쓰기</h4>
    <textarea id="" cols="30" rows="3" v-model="commentContent" class="w3-input w3-border" style="resize: none;">
      </textarea>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnCmSave">쓰기</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>&nbsp;
    </div>

  </div>
</template>

<script>
import dayjs from "dayjs";

export default {
  data() { 
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      cmRequestBody: {},

      title: '',
      name: '',
      viewCount: 0,
      empathyCount: 0,
      content: '',
      createdDate: '',
      editButton: false,
      empathyButton: false,
      imageUrls: [],
 

      list: {},

      commentPage: this.$route.query.commentPage ? this.$route.query.commentPage : 0,
      commentSize: this.$route.query.commentSize ? this.$route.query.commentSize : 10,
      commentSort: this.$route.query.commentSort ? this.$route.query.commentSort : 'createdDate,desc',

      cmNumber: 0,
      cmFirst: true,
      cmLast: false,
      cmTotalElements: 0,
      cmTotalPages: 0,

      commentContent: '',
      replyId: '',
      bestComment: {}
    }
  },
  mounted() {
    this.fnGetView()
    this.fnCmGetView()
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
        this.editButton = res.data.editButton
        this.empathyButton = res.data.empathyButton
        this.empathyCount = res.data.empathyCount
        this.viewCount = res.data.viewCount
        this.imageUrls = res.data.imageUrls
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnCmGetView() {
      this.cmRequestBody = { 
          page: this.commentPage,
          size: this.commentSize,
          sort: this.commentSort
      }

      this.$axios.get(this.$serverUrl + '/posts/' + this.idx + '/comments', {
          params: this.cmRequestBody
        }).then((res) => {
          this.list = res.data.content  
          console.log('Detail의 fnGetView 응답' + res.data.number)
          this.cmNumber = res.data.number
          this.cmFirst = res.data.first
          this.cmLast = res.data.last
          this.cmTotalElements = res.data.totalElements
          this.cmTotalPages = res.data.totalPages

          if (this.cmTotalElements !== 0) {
            this.fnGetBestCmView()
          }
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
    },
    fnGetBestCmView() {
      this.$axios.get(this.$serverUrl + '/posts/' + this.idx + '/comments/top').then((res) => {
        console.log("베스트 댓글" + JSON.stringify(res.data))
        this.bestComment = res.data  
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
        alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnImageView(url) {
      return this.$serverUrl + url;
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
      this.requestBody.imageUrls = this.imageUrls

      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return

      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx, {})
          .then(() => {
            alert('삭제되었습니다.')
            this.fnList();
          }).catch((err) => {
        console.log(err);
      })
    },
    fnPostEmpathy() {
      this.$axios.post(this.$serverUrl + '/posts/' + this.idx + '/empathy', {})
        .then(() => {
          alert('공감했습니다.')
          this.fnGetView();
        }).catch((err) => {
           console.log(err);
      })
    },
    fnPostEmpathyDelete() {
      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx + '/empathy', {})
        .then(() => {
          alert('공감 취소했습니다.')
          this.fnGetView();
        }).catch((err) => {
           console.log(err);
      })
    },
    fnCmEmpathy(id) {
      this.$axios.post(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id + '/empathy', {})
        .then(() => {
          alert('공감했습니다.')
          this.fnCmGetView();
          this.fnGetBestCmView()
        }).catch((err) => {
           console.log(err);
      })
    },
    fnCmEmpathyDelete(id) {
      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id + '/empathy', {})
        .then(() => {
          alert('공감 취소했습니다.')
          this.fnCmGetView();
          this.fnGetBestCmView();
        }).catch((err) => {
           console.log(err);
      })
    },
    fnCmSave() {
      this.cmRequestBody = {
        commentContent: this.commentContent,
      }

      this.$axios.post(this.$serverUrl + '/posts/' + this.idx + '/comments', this.cmRequestBody)
          .then(() => {
            alert('댓글이 작성되었습니다.')
            this.fnCmGetView();
          }).catch((err) => {
        console.log(err);
      })
    },
    fnCmUpdate(id, content) {
      this.requestBody.commentContent = content
      this.requestBody.commentId = id
      this.requestBody.idx = this.idx

      this.$router.push({
        path: '/comment/write',
        query: this.requestBody
      })
    },
    fnCmDelete(id) {
      if (!confirm("삭제하시겠습니까?")) return

      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id, {})
          .then(() => {
            alert('삭제되었습니다.')
            this.fnCmGetView();
          }).catch((err) => {
        console.log(err);
      })
    },

    formatDate(date) {
      return dayjs(date).format("YYYY-MM-DD hh:ss");
    },
    fnPage(n) {
      console.log('cmPage' + n)
      this.commentPage = n
      this.fnCmGetView()
    },
    reply(id) {
      this.replyId = this.reply === id ? 0 : id
    },
    fnReplySave(id) {
      this.cmRequestBody = { 
        commentContent: this.commentContent,
      }

      this.$axios.post(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id, this.cmRequestBody)
          .then(() => {
            alert('대댓글이 작성되었습니다.')
            this.fnCmGetView();
          }).catch((err) => {
        console.log(err);
      })
    }

  }
}
</script>
<style scoped>
.comment {
  display: flex;
  flex-direction: column;
}
</style>