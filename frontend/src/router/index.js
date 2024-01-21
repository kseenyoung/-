import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import StudyRoomView from '../views/StudyRoomView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
    },
    {
      path: '/regist',
      name: 'regist',
      component: () => import('@/components/user/UserRegist.vue'),
    },
    {
      path: '/findid',
      name: 'findId',
      component: () => import('@/components/user/UserFindId.vue'),
    },
    {
      path: '/findpw',
      name: 'findPw',
      component: () => import('@/components/user/UserFindPw.vue'),
    },
    {
      path: '/store',
      name: 'store',
      component: () => import('@/views/StoreView.vue'),
    },
    {
      path: '/mypage',
      name: 'myPage',
      component: () => import('@/views/MyPageView.vue'),
      children: [
        {
          path: '',
          name: 'myPageSchedule',
          component: () => import('@/components/mypage/MyPageSchedule.vue'),
        },
        {
          path: 'mypagefriend',
          name: 'myPageFriend',
          component: () => import('@/components/mypage/MyPageFriend.vue'),
        },
        {
          path: 'mypageinformation',
          name: 'myPageInformation',
          component: () => import('@/components/mypage/MyPageInformation.vue'),
        },
      ],
    },
    {
      path: '/studyroom',
      name: 'studyroom',
      component: StudyRoomView
    },
    {
      path: '/mokkoji',
      name: 'Mokkoji',
      component: () => import('@/views/MokkojiView.vue'),
    },
  ],
});

export default router;
