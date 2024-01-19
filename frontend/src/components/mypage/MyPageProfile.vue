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
    <div>
      <span>"상태메시지"</span>
      <i class="bi bi-pencil-fill common-pointer" data-bs-toggle="collapse" data-bs-target="#statusmsg-accordion" aria-expanded="true" aria-controls="collapseOne"></i>
      <div id="statusmsg-accordion" class="accordion-collapse collapse" aria-labelledby="headingOne">
        <div class="accordion-body input-group">
          <input type="text" id="statusmsg">
          <button class="btn common-btn">수정</button>
        </div>
      </div>
    </div>
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

.accordion-collapse {
  padding: 0px 10px;
}
.accordion-body {
  display: flex;
  margin-top: 5px;
  justify-content: center;

  > input {
    width: 65%;
    font-size: 1rem;
    border: 1px solid rgb(43, 43, 43);
    border-top-left-radius: var(--bs-border-radius);
    border-bottom-left-radius: var(--bs-border-radius);
  }
  > input:focus {
    outline:none;
  }
}
</style>