<template>
  <div class="d-flex flex-column mypage-view-wrapper">
    <nav class="mypage-wrapper-router">
      <RouterLink :to="{ name: 'myPageSchedule' }">일정</RouterLink>
      <RouterLink :to="{ name: 'myPageFriend' }">친구</RouterLink>
      <RouterLink :to="{ name: 'myPageQnA' }">질문</RouterLink>
      <RouterLink :to="{ name: 'myPageAlarm' }">알림</RouterLink>
      <RouterLink :to="{ name: 'myPageInventory' }">보관함</RouterLink>
      <RouterLink :to="{ name: 'myPageInformation' }">정보</RouterLink>
    </nav>
    <div class="mypage-wrapper-content d-flex">
      <MyPageProfile />
      <div class="mypage-wrapper-spring">
        <img
          src="@/assets/img/mypage/notespring_pixel2.png"
          class="notespring"
        />
      </div>
      <transition name="flip" mode="out-in">
        <div class="mypage-content flex-fill" :key="$route.fullPath">
          <RouterView />
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup>
import MyPageProfile from '@/components/mypage/MyPageProfile.vue';
</script>

<style lang="scss" scoped>
.notespring {
  height: 580px;
  position: relative;
  top: 50px;
  left: -28px;
}
.container {
  margin: 115px auto 0px;
}
.mypage-view-wrapper {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  min-height: 100vh;
  padding: 100px 140px;
}
.mypage-wrapper-router > a {
  border-width: 1px 1px 0px 1px;
  border-style: solid;
  border-color: #999;
  border-radius: 3px 3px 0px 0px;
  margin: 0px 5px;
  position: relative;
  top: -4px;
  left: 40%;
  padding: 5px 20px;
  background-color: tomato;
  transition: none;
}
.router-link-exact-active {
  // transform: scale(2, 0.5);
  // font-size: 1.4rem;
  background-color: gold !important;
  box-shadow: 4px 0px 3px #2b2b2b;
}

.mypage-wrapper-spring {
  width: 23px;
}
.mypage-wrapper-content {
  border-radius: 5px;
  background-color: #f1f7fb;
  min-height: 686px;
  padding: 0px 50px;
  // box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}
.mypage-content {
  background-color: #fff;
  margin: 20px auto;
  padding: 20px 40px;
  border-radius: 10px;
  // box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  box-shadow:
    rgba(0, 0, 0, 0.4) 0px 2px 4px,
    rgba(0, 0, 0, 0.3) 0px 7px 13px -3px,
    rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
}
</style>
