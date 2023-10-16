<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">글쓰기</button>
    </div>
    <select class="w3-select" v-model="sort" @change="fnPage(0)">
        <option value="">- 선택 -</option>
        <option value="createdDate,desc">최신순</option>
        <option value="createdDate,asc">오래된순</option>
        <option value="viewCount,desc">조회순</option>
    </select> &nbsp;
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th>
        <th>조회수</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일시</th>
        <th>추천수</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, id) in list" :key="id">
        <td>{{ row.id }}</td>
        <td>{{ row.viewCount }}</td>
        <td><a v-on:click="fnView(row.id)">{{ row.title }}</a></td>
        <td>{{ row.name }}</td>
        <td>
            <span>{{ formatDate(row.createdDate) }}</span>
        </td>
        <td>{{ row.empathyCount }}</td>
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
  data() { 
    return {
      requestBody: {}, 
      list: {}, 

      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,
      sort: this.$route.query.sort ? this.$route.query.sort : 'createdDate,desc',
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
      this.requestBody = { 
        searchCode: this.searchCode,
        searchString: this.searchString,
        page: this.page,
        size: this.size,
        sort: this.sort
      }

      console.log(this.requestBody)

      this.$axios.get(this.$serverUrl + "/posts", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {

        this.list = res.data.content 
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