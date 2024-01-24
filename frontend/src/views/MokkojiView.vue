<template>
  <div class="container">
    <div class="mok-title">
      "모꼬지명" 방
      <button class="btn common-btn">신청</button>&nbsp;
      <button class="btn common-btn">탈퇴</button>
    </div>
    <div class="mok-content-wrapper">
      <div class="mok-content-list">
        <div class="mok-list-detail">
          <img src="@/assets/img/기본프로필_갈색.jpg">
          <div>hong</div>
          <button class="btn btn-danger">강퇴</button>
        </div>
        <div class="mok-list-detail">
          <img src="@/assets/img/기본프로필_갈색.jpg">
          <div>hong</div>
          <button class="btn btn-danger">강퇴</button>
        </div>
      </div>

      <div class="mok-content-info">
        <div>길드 정보</div>
        <div>길드명</div>
        <div>인원: n명</div>
        <div>길드장</div>
        <div>인사말</div>
        <div>순위: n위</div>
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.container {
  margin: 80px auto;
  padding: 0px 150px;
  // font-size: 1.0rem;
  .mok-title {
    font-size: 1.8rem;
    font-weight: 600;
  }
  .mok-content-wrapper {
    display: flex;
    .mok-content-list {
      margin: 0px 30px;
      flex-basis: 40%;
      border: 1px solid black;
      overflow-y: auto;
      max-height: 500px;
      min-height: 500px;
      .mok-list-detail {
        display: flex;
        align-items: center;
        justify-content: space-around;
        border-bottom: 1px solid #ccc;
        padding: 10px;

        img {
          width: 40px;
          border-radius: 50%;
          margin-right: 10px;
        }
      }
    }
    .mok-content-info {
      flex-basis: 60%;
      border: 1px solid black;
    }
  }
}
</style>