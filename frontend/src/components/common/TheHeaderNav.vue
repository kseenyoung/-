<template>
  <header :class="{ 'header-hidden': headerHidden }">
    <nav style="font-size: 22px">
      <div>
        <RouterLink to="/" class="show-text">다각</RouterLink>
      </div>
      <div class="d-flex align-items-center">
        <RouterLink to="/posts">
          <span class="underline show-text">게시판</span>
        </RouterLink>
        <RouterLink to="/apply">
          <span class="underline show-text">친구/모꼬지 신청</span>
        </RouterLink>
        <RouterLink to="/store">
          <span class="underline show-text">상점</span>
        </RouterLink>
        <Alarm v-if="userStore.loginUserInfo.userId" />
        <RouterLink to="/login" v-if="!userStore.loginUserInfo.userId">
          <span class="underline show-text">로그인</span>
        </RouterLink>

        <div
          class="dropdown-toggle common-pointer show-text"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          data-bs-auto-close="true"
          v-if="userStore.loginUserInfo.userId"
          id="dropdownProfileButton"
        >
          <img class="profile" v-if="userStore.loginUserInfo.userPicture" :src="useImage(profileImage)" />
          <img class="profile" v-else src="@/assets/img/default.jpg" />
        </div>
        <ul class="dropdown-menu" aria-labelledby="dropdownProfileButton">
          <li>
            <div class="d-flex profile-info">
              <div v-if="userStore.loginUserInfo.userPicture">
                <img class="profile" :src="useImage(profileImage)" />
              </div>
              <div v-else-if="userStore.loginUserInfo.userPicture">
                <img class="profile" :src="useImage(profileImage)" />
              </div>
              <div v-else>
                <img class="profile" src="@/assets/img/default.jpg" />
              </div>
              <div>
                <div>{{ userStore.loginUserInfo.userId }}</div>
                <div>{{ userStore.loginUserInfo.userEmail }}</div>
              </div>
            </div>
          </li>
          <li>
            <RouterLink to="/mypage" class="dropdown-item">
              <span class="underline">마이페이지</span>
            </RouterLink>
          </li>
          <li>
            <!-- 모꼬지가 있을때는 길드페이지로, 없으면 친구/모꼬지 신청 페이지로 이동 -->
            <RouterLink
              :to="`/mokkoji/${userStore.loginUserInfo.mokkojiId}`"
              class="dropdown-item"
              v-show="userStore.loginUserInfo.mokkojiId != null"
            >
              <span class="underline">모꼬지</span>
            </RouterLink>
          </li>
        </ul>
        <div v-if="userStore.loginUserInfo.userId" class="show-text">
          <a href="#" class="logout dropdown-item show-text" @click="logout">
            <i class="bi bi-box-arrow-right"></i>
          </a>
        </div>
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
import { cookiesStorage } from '@/utils/CookiesUtil';

const userStore = useUserStore();
const alarmStore = useAlarmStore();
const router = useRouter();
const profileImage = ref("");
const useImage = (url) => {
  return new URL(`${url}`, import.meta.url).href;
};

//로그아웃
const logout = async function () {
  await userStore.deleteLoginUserInfo();
  const body = {
    sign: 'logout',
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body)
    .then((res) => res.data);
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
  if (userStore.loginUserInfo.userId != null) {
    alarmStore.getUnReadAlarmList();
    profileImage.value = userStore.loginUserInfo.userPicture;
  }
});
onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style lang="scss" scoped>
.show-text {
  color: white;
}
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
  color: #ff6347;
  transition: 0.8s;
  padding-bottom: 10px;
  background: linear-gradient(to top, #ff6347 8%, transparent 8%);
}
nav a span:hover {
  transition: 0.4s;
  color: tomato;
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
}

nav a:hover {
  color: tomato !important;
}

.profile {
  width: 50px;
  height: 50px;
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
