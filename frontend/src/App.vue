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

const leave = function (event) {
  console.log('이곳에 메서드 작성');
  event.preventDefault();
  event.returnValue = '';
};
onMounted(() => {
  window.addEventListener('beforeunload', leave);
});
onBeforeUnmount(() => {
  window.removeEventListener('beforeunload', leave);
});
</script>
<style lang="scss" scoped>
#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: $footer-height;
}
</style>
