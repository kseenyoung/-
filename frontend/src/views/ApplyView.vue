<template>
  <div class="container">
    <div class="apply-wrapper">
      <ApplyFriend/>
      <ApplyMokkoji/>
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
}
.apply-wrapper {
  display: flex;
  min-height: 650px;

  > div {
    display: flex;
    flex-flow: column nowrap;
    align-items: center;
  }

  .apply-friend-wrapper {
    flex-basis: 50%;
    background-color: aquamarine;
  }
  .apply-mokkoji-wrapper {
    flex-basis: 50%;
    background-color: bisque;
  }

}
</style>