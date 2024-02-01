<template>
  <TheHeaderNav />
  <div id="wrapper">
    <RouterView />
  </div>
  <TheFooter />
</template>

<script setup>
import { onBeforeUnmount, onMounted } from 'vue';
import { RouterView } from 'vue-router';
import TheHeaderNav from './components/common/TheHeaderNav.vue';
import TheFooter from './components/common/TheFooter.vue';

</script>
<style lang="scss" scoped>
#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: $footer-height;
}
</style>
