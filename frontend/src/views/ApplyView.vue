<template>
  <div class="container">
    <div class="apply-wrapper">
      <ApplyFriend />
      <ApplyMokkoji />
      <img src="@/assets/img/mypage/notespring2.png" class="img-spring" />
    </div>
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
.apply-wrapper {
  display: flex;
  min-height: 750px;
  background-color: aliceblue;
  box-shadow:
    rgba(0, 0, 0, 0.4) 0px 2px 4px,
    rgba(0, 0, 0, 0.3) 0px 7px 13px -3px,
    rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
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
  .img-spring {
    width: 70px;
    position: absolute;
    left: 724px;
  }
}
</style>
