<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th>
        <th>조회수</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, id) in list" :key="id">
        <td>{{ row.id }}</td>
        <td>{{ row.viewCount }}</td>
        <td><a v-on:click="fnView(`${row.id}`)">{{ row.title }}</a></td>
        <td>{{ row.name }}</td>
        <td>
            <span>{{ formatDate(row.createdDate) }}</span>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="pagination w3-bar w3-padding-16 w3-small" v-if="totalElements > 0">
      <span class="pg">
      <a href="javascript:;" @click="fnPage(0)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
      <a href="javascript:;" v-if="!first" @click="fnPage(`${number-1}`)"
         class="prev w3-button w3-bar-item w3-border">이전</a>
      <a class="prev w3-bar-item w3-border">{{ number + 1}} / {{ totalPages }}</a>
      <a href="javascript:;" v-if="!last"
         @click="fnPage(`${number+1}`)" class="next w3-button w3-bar-item w3-border">다음</a>
      <a href="javascript:;" @click="fnPage(`${totalPages-1}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>


        <select v-model="searchCode">
          <option value="TITLE">- 선택 -</option>
          <option value="NAME">작성자</option>
          <option value="TITLE">제목</option>
          <option value="CONTENT">내용</option>
        </select>
        &nbsp;
        <input type="text" v-model="searchString" @keyup.enter="fnPage(0)">
        &nbsp;
        <button @click="fnPage(0)">검색</button>

      </span>
    </div>


</div>
</template>

<script>
import dayjs from "dayjs";

export default {
  data() { //변수생성
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터

      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,
      keyword: this.$route.query.keyword,

      searchCode: "NAME",
      searchString: "",

      number: 0,
      first: true,
      last: false,
      totalElements: 0,
      totalPages: 0,
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
        searchCode: this.searchCode,
        searchString: this.searchString,
        page: this.page,
        size: this.size,
      }

      console.log(this.requestBody)

      this.$axios.get(this.$serverUrl + "/posts", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {

        this.list = res.data.content  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
        this.number = res.data.number
        this.first = res.data.first
        this.last = res.data.last
        this.totalElements = res.data.totalElements
        this.totalPages = res.data.totalPages


      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    formatDate(date) {
      // 날짜 데이터를 `YYYY-MM-DD` 형식으로 변환합니다.
      return dayjs(date).format("YYYY-MM-DD hh:ss");
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    },
    fnPage(n) {
      this.page = n
      this.fnGetList()
    }
  },
}
</script>