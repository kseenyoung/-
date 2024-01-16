<template>
  <div class="mypage-profile">
    <div><img src="@/assets/img/기본프로필_갈색.jpg"></div>
    <div>닉네임</div>
    <div>example@gmail.com</div>
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
  margin: 20px;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}
img {
  width: 100px;
}
</style>