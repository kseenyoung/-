<template>
  <TheHeaderNav v-if="currentPath != '/studyRoom' && currentPath != '/studyroom'" />
  <div id="wrapper" class="parent">
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
import { computed, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useDagakStore } from "./stores/dagak";

const route = useRoute();
const dagakStore = useDagakStore();
const currentPath = computed(() => {
  return route.path;
});
const userStore = useUserStore();

onMounted(() => {
  if (
    userStore.loginUserInfo.userId == undefined ||
    userStore.loginUserInfo.userId == null
  ) {
    localStorage.removeItem("dagakStore");
  }
});
watch(
  () => dagakStore.todayDagak,
  async (newVal, oldVal) => {
    console.log(newVal, "tete", oldVal, "tete2");
    if (newVal.value != null || newVal.value != undefined) {
      console.log(newVal, "tete4", oldVal, "tete3");
      await dagakStore.getTodayDagak();
    }
  }
);
</script>

<style lang="scss" scoped>
#wrapper {
  height: cover;
  min-height: 100%;
}
</style>
