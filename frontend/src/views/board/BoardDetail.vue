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
    <div class="board-contents">
      <span>{{ content }}</span>
    </div>
    <h4>댓글</h4>
    <select v-model="commentSort" @change="fnPage(0)">
        <option value="createdDate,desc">- 선택 -</option>
        <option value="createdDate,desc">최신순</option>
        <option value="createdDate,asc">오래된순</option>
    </select>
        &nbsp;
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>등록시간</th>
        <th>작성자</th>
        <th>내용</th>
        <th>공감수</th>
        <th>공감</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, id) in list" :key="id">
        <td><span>{{ formatDate(row.createdDate) }}</span></td>
        <td>{{ row.name }}</td>
        <td>{{ row.content }}</td>
        <td>{{ row.empathyCount }}</td>
        <td><button v-if="row.empathyButton" type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathy(row.id)">공감</button></td>
        <td><button v-if="!row.empathyButton && !row.editButton" type="button" class="w2-button w3-round w3-blue" v-on:click="fnCmEmpathyDelete(row.id)">공감 취소</button></td>
        <td><button v-if="row.editButton" type="button" class="w2-button w3-round w3-blue-gray" v-on:click="fnCmUpdate(row.id, row.content)">수정</button>&nbsp;</td>
        <td><button v-if="row.editButton" type="button" class="w2-button w3-round w3-red" v-on:click="fnCmDelete(row.id)">삭제</button>&nbsp;</td>
      </tr>
      </tbody>
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
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      cmRequestBody: {},

      title: '',
      name: '',
      viewCount: 0,
      content: '',
      createdDate: '',
      editButton: false,

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
        this.viewCount = res.data.viewCount
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnCmGetView() {
    this.cmRequestBody = { // 데이터 전송
        page: this.commentPage,
        size: this.commentSize,
        sort: this.commentSort
    }

    this.$axios.get(this.$serverUrl + '/posts/' + this.idx + '/comments', {
        params: this.cmRequestBody
      }).then((res) => {
        this.list = res.data.content  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
        console.log('Detail의 fnGetView 응답' + res.data.number)
        this.cmNumber = res.data.number
        this.cmFirst = res.data.first
        this.cmLast = res.data.last
        this.cmTotalElements = res.data.totalElements
        this.cmTotalPages = res.data.totalPages
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

      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx, {})
          .then(() => {
            alert('삭제되었습니다.')
            this.fnList();
          }).catch((err) => {
        console.log(err);
      })
    },
    fnCmEmpathy(id) {
      this.$axios.post(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id + '/empathy', {})
        .then(() => {
          alert('공감했습니다.')
          this.fnCmGetView();
        }).catch((err) => {
           console.log(err);
      })
    },
    fnCmEmpathyDelete(id) {
      this.$axios.delete(this.$serverUrl + '/posts/' + this.idx + '/comments/' + id + '/empathy', {})
        .then(() => {
          alert('공감 취소했습니다.')
          this.fnCmGetView();
        }).catch((err) => {
           console.log(err);
      })
    },
    fnCmSave() {
      this.cmRequestBody = { // 데이터 전송
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
      // 날짜 데이터를 `YYYY-MM-DD` 형식으로 변환합니다.
      return dayjs(date).format("YYYY-MM-DD hh:ss");
    },
    fnPage(n) {
      console.log('cmPage' + n)
      this.commentPage = n
      this.fnCmGetView()
    }

  }
}
</script>
<style scoped>


</style>