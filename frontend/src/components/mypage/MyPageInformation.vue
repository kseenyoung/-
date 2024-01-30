<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 정보</div>

    <div class="info-wrapper">
      <div class="info-detail-wrapper">
        <div class="info-label">프로필</div>
        <div class="info-content">
          <img src="@/assets/img/기본프로필_갈색.jpg" />
        </div>
        <i
          class="bi bi-pencil-fill common-pointer"
          data-bs-toggle="collapse"
          data-bs-target="#update-profile"
          aria-expanded="true"
          aria-controls="collapseOne"
        ></i>
      </div>
      <!-- 프로필 수정아코디언 -->
      <div
        id="update-profile"
        class="accordion-collapse collapse"
        aria-labelledby="headingOne"
      >
        <div class="accordion-body input-group">
          <input class="form-control" type="file" id="formFile" />
          <button class="btn common-btn">수정</button>
        </div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이름</div>
        <div class="info-content">김싸피</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">아이디</div>
        <div class="info-content">{{ userInfo.userId }}</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">비밀번호</div>
        <div class="info-content"></div>
        <i
          class="bi bi-pencil-fill common-pointer"
          data-bs-toggle="collapse"
          data-bs-target="#update-password"
          aria-expanded="true"
          aria-controls="collapseOne"
        ></i>
      </div>
      <!-- 비밀번호 변경 아코디언 -->
      <div
        id="update-password"
        class="accordion-collapse collapse"
        aria-labelledby="headingOne"
      >
        <div class="form-floating">
          <input
            type="password"
            id="password-cur"
            class="form-control"
            required
            v-model="curPassword"
          />
          <label for="floatingInput">현재 비밀번호</label>
        </div>
        <div class="form-floating">
          <input
            type="password"
            id="password-new"
            class="form-control"
            required
            v-model="newPassword"
            :class="{ 'is-valid': isValidPw, 'is-invalid': !isValidPw }"
          />
          <label for="floatingInput">비밀번호 변경</label>
          <div v-if="isValidPw" class="form-text pw-text">
            사용할 수 있는 비밀번호입니다
          </div>
          <div v-else class="form-text pw-text">
            영문자, 숫자 및 특수문자(@&!%*#?&) 조합의 6~20자리를 사용하세요
          </div>
        </div>
        <div class="form-floating">
          <input
            type="password"
            id="password-check"
            class="form-control"
            required
            v-model="passwordCheck"
            :class="{ 'is-valid': isSamePw, 'is-invalid': !isSamePw }"
          />
          <label for="floatingInput">비밀번호 확인</label>
          <div v-if="isSamePw" class="form-text">비밀번호가 일치합니다.</div>
          <div v-else class="form-text">비밀번호가 일치하지 않습니다</div>
        </div>
        <button
          class="btn common-btn"
          @click="changePw"
          :disabled="changePWFlag"
        >
          수정
        </button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">닉네임</div>
        <div class="info-content">ssafykim</div>
        <i
          class="bi bi-pencil-fill common-pointer"
          data-bs-toggle="collapse"
          data-bs-target="#update-nickname"
          aria-expanded="true"
          aria-controls="collapseOne"
        ></i>
      </div>
      <!-- 닉네임 변경 아코디언 -->
      <div
        id="update-nickname"
        class="accordion-collapse collapse"
        aria-labelledby="headingOne"
      >
        <div class="form-floating">
          <input type="text" id="nickname" class="form-control" />
          <label for="floatingInput">닉네임 변경</label>
        </div>
        <button class="btn common-btn">중복확인</button>
        <button class="btn common-btn">수정</button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이메일</div>
        <div class="info-content">{{ maskedEmail }}</div>
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

      <div class="info-detail-wrapper">
        <div class="info-content">
          <button
            class="btn common-btn"
            data-bs-toggle="modal"
            data-bs-target="#deleteUserModal"
          >
            회원탈퇴
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 회원탈퇴 모달 -->
  <MyPageDeleteUserModal />
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted, watch, computed } from 'vue';
import MyPageDeleteUserModal from './MyPageDeleteUserModal.vue';

onMounted(() => {
  userAxios();
});

//유저정보 axios
const userInfo = ref({});
const userAxios = function () {
  const userBody = {
    sign: 'viewUserInformation',
    userNickname: 'hongaaa',
  };
  axios
    .post('https://localhost:8080/dagak/user', userBody, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then((json) => {
      console.log(json);
      userInfo.value = json.result;
    });
};

//이메일 마스킹 처리
const maskedEmail = computed(() => {
  const email = 'ssafy123@gmail.com';
  // const email = userInfo.value.email;
  const [username, domain] = email.split('@');
  const maskedUsername =
    username.substring(0, 3) + '*'.repeat(username.length - 3);
  return maskedUsername + '@' + domain;
});

//비밀번호 변경
//백에서 session정보에서 userId를 가져오는 중이라 로그인 연동이 되어야 실행됨
const curPassword = ref('');
const newPassword = ref('');
const passwordCheck = ref('');
const isValidPw = ref(false);
const isSamePw = ref(false);

//비밀번호 유효성 && 일치 검사
watch(newPassword, (newPw) => {
  isSamePw.value = false;
  checkPw(newPw);
  samePw();
});
const checkPw = function (pw) {
  const validatePw = /^(?=.*\d)(?=.*[@&!%*#?&])[A-Za-z\d@&!%*#?&]{6,16}$/;
  isValidPw.value = validatePw.test(pw);
};

watch(passwordCheck, () => {
  isSamePw.value = false;
  samePw();
});
const samePw = function () {
  if (passwordCheck.value === newPassword.value) isSamePw.value = true;
};

const changePWFlag = computed(() => {
  return (
    newPassword.value === '' ||
    passwordCheck.value === '' ||
    isValidPw.value === false ||
    isSamePw.value === false
  );
});

//비빌번호 변경 axios
const changePw = function () {
  console.log(
    '현재 pw: ' + curPassword.value + ', 변경 pw: ' + newPassword.value,
  );
  console.log(newPassword.value);
  const userBody = {
    sign: 'changePassword',
    userPassword: curPassword.value,
    newPassword: newPassword.value,
  };
  axios
    .post('https://localhost:8080/dagak/user', userBody, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then((json) => {
      console.log(json);
    });
};
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
