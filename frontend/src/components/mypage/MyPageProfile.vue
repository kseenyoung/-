<template>
  <div class="mypage-profile">
    <div>
      <img src="@/assets/img/기본프로필_갈색.jpg">
      <div>ssafy</div>
      <div>ssafy123@gmail.com</div>
    </div>
    <div>
      <span>10시간 | </span>
      <span>10위 | </span>
      <span>10P</span>
    </div>
    <div>"상태메시지"</div>
    <div>길드: -</div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.mypage-profile {
  background-color: white;
  flex-basis: 300px;
  text-align: center;
  margin: 20px 0px;
  border-radius: 10px;
  // box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
  padding: 120px 0px;

    > div {
      margin-bottom: 30px;
    }
}
img {
  width: 120px;
  border-radius: 50%;
}

</style>