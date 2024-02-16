<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 정보</div>

    <div class="info-wrapper">
      <div class="info-detail-wrapper">
        <div class="info-label">프로필</div>
        <div class="info-content">
          <img
            v-if="userStore.loginUserInfo.userPicture"
            :src="profileImage + '?v=' + new Date().getTime()"
            class="profile-img"
          />
          <img v-else src="@/assets/img/default.jpg" class="profile-img" />
        </div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이름</div>
        <div class="info-content">{{ userStore.loginUserInfo.userName }}</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">아이디</div>
        <div class="info-content">{{ userStore.loginUserInfo.userId }}</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">비밀번호 변경</div>
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
        <button class="btn common-btn" @click="changePw" :disabled="changePWFlag">
          수정
        </button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">닉네임</div>
        <div class="info-content">
          {{ userStore.loginUserInfo.userNickname }}
        </div>
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
          <input
            type="text"
            id="nickname"
            class="form-control"
            :value="nickname"
            @input="onInputNick"
          />
          <label for="floatingInput">닉네임 변경</label>
        </div>
        <button class="btn common-btn" @click="existNickname(nickname)">중복확인</button>
        <button class="btn common-btn" @click="changeNickname" :disabled="!nicknameFlag">
          수정
        </button>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">이메일</div>
        <div class="info-content">{{ maskedEmail }}</div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">생년월일</div>
        <div class="info-content">
          {{ userStore.loginUserInfo.userBirthday }}
        </div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">전화번호</div>
        <div class="info-content">
          {{ userStore.loginUserInfo.userPhonenumber }}
        </div>
      </div>

      <div class="info-detail-wrapper">
        <div class="info-label">포인트</div>
        <div class="info-content">{{ userStore.loginUserInfo.userPoint }}P</div>
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
import { ref, watch, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
import axios from "axios";
import MyPageDeleteUserModal from "./MyPageDeleteUserModal.vue";

const router = useRouter();
const userStore = useUserStore();
const profileImage = ref("");

onMounted(() => {
  if (userStore.loginUserInfo.userId != null) {
    profileImage.value = userStore.loginUserInfo.userPicture;
  }
});

//이메일 마스킹 처리
const maskedEmail = computed(() => {
  const email = userStore.loginUserInfo.userEmail;
  // const email = userInfo.value.email;
  const [username, domain] = email.split("@");
  const maskedUsername = username.substring(0, 3) + "*".repeat(username.length - 3);
  return maskedUsername + "@" + domain;
});

//비밀번호 변경
const curPassword = ref("");
const newPassword = ref("");
const passwordCheck = ref("");
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
    newPassword.value === "" ||
    passwordCheck.value === "" ||
    isValidPw.value === false ||
    isSamePw.value === false
  );
});

//비빌번호 변경 axios
const changePw = function () {
  const userBody = {
    sign: "modifyPassword",
    userPassword: curPassword.value,
    newPassword: newPassword.value,
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, userBody, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code === 1000) {
        //성공
        alert("비밀번호가 변경되었습니다. 다시 로그인 해주세요.");
        userStore.deleteLoginUserInfo();
        const body = {
          sign: "logout",
        };
        axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body);
        //성공 시 홈으로
      } else {
        //실패
        alert(json.message);
      }
    });
  router.push({
    name: "login",
  });
};

//닉네임 변경
const nickname = ref("");
const isValidNickname = ref(false);
const isDuplicateNickname = ref(false);
const dupNicknameClicked = ref(false);
const nicknameFlag = computed(() => isValidNickname.value && isDuplicateNickname.value);

//닉네임 한글이슈
const onInputNick = function (event) {
  nickname.value = event.currentTarget.value;
};

watch(nickname, (newNickname) => {
  dupNicknameClicked.value = false;
  isDuplicateNickname.value = false;
  checkNickname(newNickname);
});

//닉네임 유효성 검사(특수문자 불가능 2~8 글자)
const checkNickname = function (name) {
  const validateNickname = /^[a-zA-Z가-힣1-9]{2,8}$/;
  isValidNickname.value = validateNickname.test(name);
};

//닉네임 중복확인
const existNickname = async function (checkNickname) {
  dupNicknameClicked.value = true;
  const body = {
    sign: "isExistNickname",
    userNickname: checkNickname,
  };

  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code == 1000) {
        // 중복 아님
        isDuplicateNickname.value = true;
        alert("사용 가능한 닉네임입니다.");
      } else {
        // 중복임
        isDuplicateNickname.value = false;
        alert("이미 존재하는 닉네임입니다.");
        nickname.value = ""; //닉네임 텍스트 초기화
      }
    });
};

const changeNickname = function () {
  const body = {
    sign: "modifyNickname",
    newNickname: nickname.value,
  };

  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code == 1000) {
        // 성공
        alert("닉네임이 변경되었습니다.");
        userStore.getLoginUserInfo();
      } else {
        // 실패
        alert(json.message);
      }
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
    .info-content > .profile-img {
      width: 100px;
      height: 100px;
    }
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
