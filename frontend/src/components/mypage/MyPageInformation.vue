<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 정보</div>
    
    <div class="info-wrapper">
      <div class="info-detail-wrapper">
        <div class="info-label">프로필</div>
        <div class="info-content">
          <img src="@/assets/img/기본프로필_갈색.jpg">
        </div>
        <i class="bi bi-pencil-fill common-pointer" data-bs-toggle="collapse" data-bs-target="#update-profile" aria-expanded="true" aria-controls="collapseOne"></i>
      </div>
      <!-- 프로필 수정아코디언 -->
      <div id="update-profile" class="accordion-collapse collapse" aria-labelledby="headingOne">
        <div class="accordion-body input-group">
          <input class="form-control" type="file" id="formFile">
          <button class="btn common-btn">수정</button>
        </div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이름</div>
        <div class="info-content">김싸피</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">아이디</div>
        <div class="info-content">ssafy</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">비밀번호</div>
        <div class="info-content"></div>
        <i class="bi bi-pencil-fill common-pointer" data-bs-toggle="collapse" data-bs-target="#update-password" aria-expanded="true" aria-controls="collapseOne"></i>
      </div>
      <!-- 비밀번호 변경 아코디언 -->
      <div id="update-password" class="accordion-collapse collapse" aria-labelledby="headingOne">
        <div class="form-floating">
          <input type="password" id="password" class="form-control">
          <label for="floatingInput">비밀번호 변경</label>
        </div>
        <div class="form-floating">
          <input type="password" id="password-check" class="form-control">
          <label for="floatingInput">비밀번호 확인</label>
        </div>
        <button class="btn common-btn">수정</button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">닉네임</div>
        <div class="info-content">ssafykim</div>
        <i class="bi bi-pencil-fill common-pointer" data-bs-toggle="collapse" data-bs-target="#update-nickname" aria-expanded="true" aria-controls="collapseOne"></i>
      </div>
      <!-- 닉네임 변경 아코디언 -->
      <div id="update-nickname" class="accordion-collapse collapse" aria-labelledby="headingOne">
        <div class="form-floating">
          <input type="text" id="nickname" class="form-control">
          <label for="floatingInput">닉네임 변경</label>
        </div>
        <button class="btn common-btn">중복확인</button>
        <button class="btn common-btn">수정</button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이메일</div>
        <div class="info-content">ssafy@gmail.com</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">생년월일</div>
        <div class="info-content">1950-01-01</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">전화번호</div>
        <div class="info-content">010-1111-1111</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">포인트</div>
        <div class="info-content">10P</div>
      </div>

    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.info-wrapper {
  display: flex;
  flex-direction: column;
  padding: 0px 80px;

  > * {
    font-size: 1.1rem;
  }

  .info-detail-wrapper {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    padding: 10px 5px 30px;
    border-top: 1px solid rgb(190, 190, 190);

  }
  .info-detail-wrapper:last-child {
    border-bottom: 1px solid rgb(190, 190, 190);
  }

  .info-detail-wrapper > div:nth-child(1) {
    font-weight: 600;
    flex-basis: 30%;
  }
  .info-detail-wrapper > div:nth-child(2) {
    flex-grow: 1;

    img {
      width: 50px;
      border-radius: 50%;
    }
  }
}
.accordion-collapse {
  text-align: right;
  padding: 10px 20px 15px;
  background-color: aliceblue;

  .form-floating {
    margin: 10px 0px;
  }

  > button {
    margin-right: 5px;
  }
}

</style>