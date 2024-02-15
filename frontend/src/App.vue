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
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user'

const route = useRoute();
const currentPath = computed(() => {
  return route.path;
});
const userStore = useUserStore();

onMounted(() =>{
  if(userStore.loginUserInfo.userId == undefined || userStore.loginUserInfo.userId == null){
      localStorage.removeItem("dagakStore");
  }

});

</script>

<style lang="scss" scoped>
#wrapper {
  height: cover;
  min-height: 100%;
}
</style>
