<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">친구 목록</div>
    <div class="friend-list-wrapper">
      <div><i class="bi bi-people-fill"></i> 10명</div>

      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">hong</div>
        <div class="friend-onoff friend-online"><i class="bi bi-circle-fill"></i>접속중</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
        <ul class="dropdown-menu">
          <li>hong</li>
          <li>모꼬지</li>
          <li>"오늘도 파이팅!!"</li>
        </ul>
      </div>

      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임1</div>
        <div class="friend-onoff friend-online"><i class="bi bi-circle-fill"></i>접속중</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임1</div>
        <div class="friend-onoff friend-online"><i class="bi bi-circle-fill"></i>접속중</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임1</div>
        <div class="friend-onoff friend-online"><i class="bi bi-circle-fill"></i>접속중</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임2</div>
        <div class="friend-onoff friend-offline"><i class="bi bi-circle-fill"></i>접속해제</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임2</div>
        <div class="friend-onoff friend-offline"><i class="bi bi-circle-fill"></i>접속해제</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임2</div>
        <div class="friend-onoff friend-offline"><i class="bi bi-circle-fill"></i>접속해제</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>
      <div class="friend-list-detail">
        <img src="@/assets/img/기본프로필_갈색.jpg">
        <div>닉네임2</div>
        <div class="friend-onoff friend-offline"><i class="bi bi-circle-fill"></i>접속해제</div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
      </div>

    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.friend-list-wrapper {
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  max-height: 430px;
  margin: 0px 80px;
  padding: 0px 20px;
}
.friend-list-detail {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding: 10px;

  img {
    width: 40px;
    border-radius: 50%;
    margin-right: 10px;
  }

  div {
    margin-right: 10px; /* Adjust the margin as needed */
  }

  :nth-child(1) {
    flex-basis: 10%;
  }
  :nth-child(2) {
    flex-basis: 40%;
  }
  :nth-child(3) {
    flex-basis: 23%;
  }
  :nth-child(4) {
    flex-basis: auto;
  }

  .dropdown-menu {
    padding: 10px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
  }

  @mixin friend-status {
    border: 1px solid #ccc;
    background-color: #f4f4f4;
    border-radius: 30px;
    text-align: center;
  }
  .friend-onoff {
    @include friend-status;

    i {
      font-size: 0.7rem;
      position: relative;
      top: -3px;
      padding-right: 6px;
    }
  } 

  .friend-online {
    i {
      color: rgb(0, 176, 0);
    }
  }
  
  .friend-offline {
    i {
      color: rgb(228, 0, 0);
    }
  }
}
</style>