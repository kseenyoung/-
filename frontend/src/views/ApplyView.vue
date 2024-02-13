<template>
  <div class="apply-view-wrapper">
    <div class="apply-wrapper">
      <ApplyFriend />
      <ApplyMokkoji />
    </div>
    <img src="@/assets/img/mypage/notespring_pixel2.png" class="img-spring" />
  </div>
</template>

<script setup>
import ApplyFriend from '@/components/apply/ApplyFriend.vue';
import ApplyMokkoji from '@/components/apply/ApplyMokkoji.vue';
</script>

<style lang="scss" scoped>
.container {
  margin: 80px auto;
  font-size: 1rem;
}
.apply-view-wrapper {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 0px;
}
.apply-wrapper {
  display: flex;
  min-height: 750px;
  background-color: aliceblue;
  box-shadow:
    rgba(0, 0, 0, 0.4) 0px 2px 4px,
    rgba(0, 0, 0, 0.3) 0px 7px 13px -3px,
    rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
  width: 1200px;
  > div {
    display: flex;
    flex-flow: column nowrap;
    align-items: center;
  }
  .apply-friend-wrapper {
    flex-basis: 50%;
    border-right: 10px solid white;
  }
  .apply-mokkoji-wrapper {
    flex-basis: 50%;
    border-left: 10px solid white;
  }
}
.img-spring {
  width: 77px;
  height: 650px;
  position: relative;
  // top: -710px;
  // left: 47%;
  left: -640px;
}
</style>
