<template>
  <TheHeaderNav
    v-if="currentPath != '/studyRoom' && currentPath != '/studyroom'"
  />
  <div id="wrapper" class="parent">
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
import TheHeaderNav from './components/common/TheHeaderNav.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';
const route = useRoute();
const currentPath = computed(() => {
  return route.path;
});
</script>

<style lang="scss" scoped>
#wrapper {
  height: cover;
  min-height: 100%;
}
</style>
