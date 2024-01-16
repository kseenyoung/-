<template>
  <div class="container d-flex flex-column">
    <nav class="mypage-wrapper-router">
      <RouterLink :to="{ name : 'myPageSchedule' }">내 스케줄</RouterLink>
      <RouterLink :to="{ name : 'myPageFriend' }">내 친구</RouterLink>
      <RouterLink :to="{ name : 'myPageInformation' }">내 정보</RouterLink>
    </nav>
    <div class="mypage-wrapper-content d-flex">
      <MyPageProfile/>
      <img src="@/assets/img/notespring2.png" class="notespring">
      <div class="mypage-content flex-fill">
        <RouterView/>
      </div>
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
  top: 5px;
}
.container {
  margin: 70px auto;
}
.mypage-wrapper-router > a {
  border-width: 1px 1px 0px 1px;
  border-style: solid;
  border-color: black;
  margin: 0px 10px;
  position: relative;
  top: 2px;
  left: 30%;
}
.router-link-exact-active {
  background-color: tomato;
}
.mypage-wrapper-content {
  background-color: #f1f7fb;
  border: 1px solid black;
  min-height: 580px;
}
</style>