import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue')
    },
    {
      path: '/googleLogin',
      name: 'googleLogin',
      component: () => import('@/views/GoogleLoginView.vue'),
    },
    {
      path: '/kakaoLogin',
      name: 'kakaoLogin',
      component: () => import('@/views/kakaoLoginView.vue'),
    },
    {
      path: '/regist',
      name: 'regist',
      component: () => import('@/components/user/UserRegist.vue')
    },
    {
      path: '/findid',
      name: 'findId',
      component: () => import('@/components/user/UserFindId.vue')
    },
    {
      path: '/findpw',
      name: 'findPw',
      component: () => import('@/components/user/UserFindPw.vue')
    },
    {
      path: '/store',
      name: 'store',
      component: () => import('@/views/StoreView.vue')
    },
    {
      path: '/mypage',
      name: 'myPage',
      component: () => import('@/views/MyPageView.vue'),
      children: [
        {
          path: '',
          name: 'myPageSchedule',
          component: () => import('@/components/mypage/MyPageSchedule.vue')
        },
        {
          path: 'friend',
          name: 'myPageFriend',
          component: () => import('@/components/mypage/MyPageFriend.vue')
        },
        {
          path: 'information',
          name: 'myPageInformation',
          component: () => import('@/components/mypage/MyPageInformation.vue')
        },
        {
          path: 'qna',
          name: 'myPageQnA',
          component: () => import('@/components/mypage/MyPageQnA.vue')
        },
        {
          path: 'alarm',
          name: 'myPageAlarm',
          component: () => import('@/components/mypage/MyPageAlarm.vue')
        },
        {
          path: 'inventory',
          name: 'myPageInventory',
          component: () => import('@/components/mypage/MyPageInventory.vue')
        }
      ]
    },

    {
      path: '/studyroom',
      name: 'studyroom',
      component: () => import('@/views/StudyRoomView.vue'),
      children: [
        {
          path: 'qna',
          name: 'QnAList',
          component: () => import('@/components/room/QnAListView.vue'),
        },
        {
          path: 'studyRate',
          name: 'studyRate',
          component: () => import('@/components/room/StudyRateView.vue'),
        }
      ]
    },
    {
      // :모꼬지pk 나중에 넣자
      path: '/mokkoji/:id',
      name: 'mokkoji',
      component: () => import('@/views/MokkojiView.vue')
    },
    {
      path: '/apply',
      name: 'apply',
      component: () => import('@/views/ApplyView.vue')
    },

    {
      path: '/posts',
      name: 'postList',
      component: () => import('@/views/posts/PostListView.vue')
    },
    {
      path: '/posts/create',
      name: 'postCreate',
      component: () => import('@/views/posts/PostCreateView.vue')
    },
    {
      path: '/posts/:id',
      name: 'postDetail',
      component: () => import('@/views/posts/PostDetailView.vue')
    },
    {
      path: '/posts/:id/edit',
      name: 'postEdit',
      component: () => import('@/views/posts/PostEditView.vue')
    }
  ]
})

export default router
