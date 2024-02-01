<template>
  <header :class="{ 'header-hidden': headerHidden }">
    <nav>
      <div>
        <RouterLink to="/">다각</RouterLink>
      </div>
      <div class="d-flex align-items-center">
        <RouterLink to="/apply">
          <span class="underline">친구/모꼬지 신청</span>
        </RouterLink>
        <RouterLink to="/store">
          <span class="underline">상점</span>
        </RouterLink>
        <Alarm v-if="userStore.loginUserInfo.userId" />
        <RouterLink to="/login" v-if="!userStore.loginUserInfo.userId">
          <span class="underline">로그인</span>
        </RouterLink>
        <div
          class="dropdown-toggle common-pointer"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          v-if="userStore.loginUserInfo.userId"
        >
          <img class="profile" src="@/assets/img/기본프로필_갈색.jpg" />
        </div>
        <ul class="dropdown-menu">
          <div class="d-flex profile-info">
            <div>
              <img class="profile" src="@/assets/img/기본프로필_갈색.jpg" />
            </div>
            <div>
              <div>{{ userStore.loginUserInfo.userId }}</div>
              <div>{{ userStore.loginUserInfo.userEmail }}</div>
            </div>
          </div>
          <RouterLink to="/mypage" class="dropdown-item">
            <span class="underline">마이페이지</span>
          </RouterLink>
          <!-- 모꼬지가 있을때는 길드페이지로, 없으면 친구/모꼬지 신청 페이지로 이동 -->
          <RouterLink :to="`/mokkoji/1`" class="dropdown-item">
            <span class="underline">모꼬지</span>
          </RouterLink>
          <li>
            <a href="#" class="logout dropdown-item" @click="logout">
              <span>로그아웃</span>
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <AlarmModal />
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';
import Alarm from './Alarm.vue';
import AlarmModal from './AlarmModal.vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useAlarmStore } from '@/stores/alarm';

const userStore = useUserStore();
const alarmStore = useAlarmStore();
const router = useRouter();

//로그인할 때 생성한 sessionStorage의 정보
const loginId = ref('');
const getSessionId = function () {
  loginId.value = sessionStorage.getItem('loginSession');
};

//로그아웃
const logout = function () {
  const body = {
    sign: 'logout',
  };
  axios
    .post('https://localhost:8080/dagak/user', body)
    .then((res) => res.data)
  userStore.loginUserInfo = {};
  localStorage.removeItem('useStore');
  //성공 시 홈으로
  router.push({
    name: 'login',
  });
};

// 헤더 스크롤
const headerHidden = ref(false);
let lastScrollTop = 0;

const handleScroll = () => {
  const scrollTop = window.scrollY || document.documentElement.scrollTop;
  headerHidden.value = scrollTop > lastScrollTop && scrollTop > 70;
  lastScrollTop = scrollTop;
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  getSessionId();
  alarmStore.getUnReadAlarmList();
});
onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style lang="scss" scoped>
header {
  position: fixed;
  top: 0;
  z-index: 100;
  width: 100%;
  height: 70px;
  line-height: 70px;
  padding: 0px 80px;
  transition: transform 0.3s ease-in-out;
}

.header-hidden {
  transform: translateY(-100%);
  transition: transform 0.3s ease-in-out;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

nav a.router-link-exact-active {
  // color: var(--color-text);
  color: #ff6347;
}
nav a.router-link-exact-active > .underline {
  transition: 0.8s;
  padding-bottom: 10px;
  background: linear-gradient(to top, #ff6347 8%, transparent 8%);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
}

nav a:hover {
  color: tomato;
}

.profile {
  width: 40px;
  border-radius: 50px;
}

.profile-info {
  padding: 0px 16px;
}

.profile-info > div:nth-child(2) {
  position: relative;
  top: -6px;
  left: 10px;
  margin-right: 10px;
}

.profile-info > div:nth-child(2) > div {
  height: 23px;
}
</style>
