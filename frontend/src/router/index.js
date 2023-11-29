import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import CommentWrite from '@/views/board/comment/CommentWrite.vue'
import Login from "@/views/common/LoginForm"
import MemberWrite from '@/views/member/MemberWrite.vue'

const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/login',
    name: 'Login',
    component: Login  //로그인 컴포넌트 추가
  },
  {
    path: '/board/list',
    name: 'BoardList',
    component: BoardList
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail,
    meta: {
        reload: true,
    }
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite
  },
  {
    path: '/comment/write',
    name: 'CommentWrite',
    component: CommentWrite
  },
  {
    path: '/member/write',
    name: 'MemberWrite',
    component: MemberWrite
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router