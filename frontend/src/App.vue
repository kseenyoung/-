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

const leave = async (event) => {
  await axios.post(
          // 로그인 콜백
          'https://i10a404.p.ssafy.io/openvidu/api/signal',
          {
            session: stream.data,
            type: 'signal:login-callBack',
            data: this.myUserName,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU',
            },
          },
        );

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
