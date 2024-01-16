<template>
  <div class="container">
    <div class="regist-title">회원가입</div>
    <div class="form-con">
      <div class="input-con row mb-4">
          <label for="id" class="col-sm-2 col-form-label">아이디</label>
          <div class="col-sm-10">
              <input class="form-control" type="text" id="id" placeholder="아이디를 입력하세요" required autocomplete="off">
              <div class="btn-dup">
                  <button class="btn btn-sm btn-primary">중복 확인</button>
                  <span class="form-text">사용할 수 있는 아이디입니다</span>
                  <span class="form-text">중복된 아이디입니다</span>
              </div>
          </div>
      </div>
        
      <div class="input-con row mb-4">
          <label for="password" class="col-sm-2 form-label">비밀번호</label>
          <div class="col-sm-10">
              <input class="form-control" type="password" id="password" placeholder="비밀번호를 입력하세요" required>
              <div class="form-text pw-text">사용할 수 있는 비밀번호입니다</div>
              <div class="form-text pw-text">영문자, 숫자 및 특수문자(@&!%*#?&) 조합의 6~20자리를 사용하세요</div>
              
              <input class="form-control" type="password" id="passwordCheck" placeholder="비밀번호 재확인" required>
              <div class="form-text">비밀번호가 일치합니다.</div>
              <div class="form-text">비밀번호가 일치하지 않습니다</div>
          </div>
      </div>

      <div class="input-con row mb-4">
          <label for="name" class="col-sm-2 form-label">이름</label>
          <div class="col-sm-10">
              <input class="form-control" type="text" id="name" placeholder="이름을 입력하세요" required autocomplete="off">
          </div>
      </div>

      <div class="input-con row mb-4">
          <label for="nickname" class="col-sm-2 form-label">닉네임</label>
          <div class="col-sm-10">
              <input class="form-control" type="text" id="nickname" placeholder="사용하실 닉네임을 입력하세요" required autocomplete="off">
          </div>
      </div>

      <div class="input-con row mb-4">
          <label for="email" class="col-sm-2 form-label">이메일</label>
          <div class="col-sm-10">
              <input class="form-control" type="email" id="email" placeholder="you@example.com" autocomplete="off">
          </div>
      </div>

      <div class="input-con row mb-4">
        <label for="birth" class="col-sm-2 form-label">생년월일</label>
        <div class="col-sm-10">
            <div>
                <Datepicker 
                    locale="ko"
                    weekStart="0"
                    auto-apply
                    placeholder="YYYY-MM-DD (선택)"
                    year-first
                    :format="format"
                />
            </div>
        </div>
      </div>

      <div class="d-grid" @click="shake">
        <button class="btn btn-primary">
            가입
        </button>
        <div class="form-text sub-text">입력 조건을 만족해주세요</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

//비밀번호 변경시간 유의
</script>

<style lang="scss" scoped>
.container {
  margin: 0 auto;
  width: 45%;
  min-width: 500px;
  max-width: 45%;
  letter-spacing: -0.3px;
  margin: 30px auto;
  margin-top: 80px;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  padding: 15px 40px 30px;
  border: 1px solid rgb(226, 226, 226);
}
.regist-title {
  font-size: 2rem;
  margin: 10px 0px 20px;
  border-width: 0px 0px 1px 0px;
  border-color: black;
  border-style: solid;
}
.form-con .input-con {
  margin-bottom: 20px;
}
.sub-text {
  text-align: center;
}
.pw-text {
  margin-bottom: 10px;
}
.btn-dup {
  margin-top: 4px;
}
.btn-dup button {
  margin-right: 5px;
}
.shake {
  animation: shake 0.45s;
}

@keyframes shake {
  0% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  50% { transform: translateX(5px); }
  75% { transform: translateX(-5px); }
  100% { transform: translateX(0); }
}
</style>