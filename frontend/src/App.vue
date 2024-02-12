<template>
  <TheHeaderNav />
  <div id="wrapper" class="parent">
    <RouterView />
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted } from "vue";
import { RouterView } from "vue-router";
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
</script>

<style lang="scss" scoped>
#wrapper {
  height: cover;
  min-height: 100%;
  // padding-bottom: $footer-height;
}

// .parent {
//   overflow: scroll;
//   height: 100vh;
//   scroll-snap-type: y mandatory;
//   text-align: center;
//   margin: 0 auto;
// }

</style>
