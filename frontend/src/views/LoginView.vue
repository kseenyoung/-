<template>
  <div class="login-view-wrapper">
    <div class="card">
      <div class="card-header">
        <div class="card-header-logo">다각</div>
      </div>
      <div class="card-body">
        <div class="mb-3">
          <div class="input-group">
            <span class="input-group-text"><i class="bi bi-person"></i></span>
            <input
              :disabled="disableInputId"
              type="text"
              class="form-control no-outline"
              id="id"
              placeholder="아이디"
              required
              autofocus
              v-model="id"
            />
          </div>
        </div>
        <div class="mb-3">
          <div class="input-group">
            <span class="input-group-text"><i class="bi bi-lock"></i></span>
            <input
              :disabled="disableInputPassword"
              type="password"
              class="form-control no-outline"
              id="password"
              placeholder="비밀번호"
              required
              v-model="password"
              @keyup.enter="login"
            />
          </div>
        </div>
        <div class="mb-3 form-check">
          <input
            :disabled="disableCheckId"
            type="checkbox"
            class="form-check-input no-outline"
            id="rememberId"
          />
          <label class="form-check-label" for="rememberId">아이디 저장</label>
        </div>
        <vue-recaptcha
          v-show="true"
          sitekey="6Lcufl8pAAAAAN7h2t1u9Dgm1_zo9wKoaYRX59H6"
          @verify="recaptchaVerified"
          @expire="recaptchaExpired"
          @fail="recaptchaFailed"
          @error="recaptchaError"
          class="recaptcha"
        ></vue-recaptcha>
        <button
          class="btn btn-primary common-btn"
          :disabled="disableLoginButton"
          @click="login"
        >
          로그인
        </button>
        <div class="or-seperator"><i>또는</i></div>
        <div class="text-center social-btn">
          <img
            src="@/assets/img/login/googleLoginImg.png"
            alt="구글로그인"
            @click="googleLogin()"
          />
          <img
            src="@/assets/img/login/kakaoLoginImg.png"
            alt="카카오로그인"
            @click="kakaoLogin()"
          />
        </div>
      </div>
      <div class="sub-card">
        <RouterLink to="/findid">아이디 찾기</RouterLink><span>&nbsp;|&nbsp;</span>
        <RouterLink to="/findpw">비밀번호 찾기</RouterLink><span>&nbsp;|&nbsp;</span>
        <RouterLink to="/regist">회원가입</RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import vueRecaptcha from "vue3-recaptcha2";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useAlarmStore } from "@/stores/alarm";

const userStore = useUserStore();
const alarmStore = useAlarmStore();
const router = useRouter();
const id = ref("");
const password = ref("");
// const rememberId = ref(false);

// reCAPTCHA
const disableInputId = ref(true);
const disableInputPassword = ref(true);
const disableCheckId = ref(true);
const disableLoginButton = ref(true);

// 구글 로그인 페이지로 이동
const googleLogin = async function () {
  window.location.replace(
    "https://accounts.google.com/o/oauth2/v2/auth?client_id=273219571369-d3f2u10s1447t28d54ut6v359m5kfmp6.apps.googleusercontent.com&redirect_uri=https://localhost:5173/googleLogin&response_type=code&scope=email"
  );
};

// 카카오 로그인 페이지로 이동
const kakaoLogin = async function () {
  window.location.replace(
    "https://kauth.kakao.com/oauth/authorize?client_id=891949d64302e510fe79f05131e7d972&redirect_uri=https://localhost:5173/kakaoLogin&response_type=code"
  );
};

// 로그인
const login = async function () {
  const body = {
    sign: "login",
    userId: id.value,
    userPassword: password.value,
  };
  const res = await axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
    headers: {
      "Content-Type": "application/json",
    },
  });
  if (res.data.code === 1000) {
    //성공 시 유저정보 + 안읽은 알림 불러오기

    await userStore.getLoginUserInfo();
    console.log("tete loginSuccess", userStore.loginUserInfo);
    alarmStore.getUnReadAlarmList();
    console.log("tete");
    //홈으로 이동
    router.push({
      name: "home",
    });
  } else if (res.data.code === 1405) {
    alert(res.data.result, "asdasd");
  }
  id.value = "";
  password.value = "";
};

const recaptchaExpired = async function (response) {
  disableInputId.value = true;
  disableInputPassword.value = true;
  disableCheckId.value = true;
  ``;
  disableLoginButton.value = true;
  const body = {
    recaptchaResponse: "만료",
  };
  await axios.post(`${import.meta.env.VITE_API_BASE_URL}user/recaptcha`, body, {
    headers: {
      "Content-Type": "application/json",
    },
  });
};

const recaptchaVerified = async function (response) {
  disableInputId.value = false;
  disableInputPassword.value = false;
  disableCheckId.value = false;
  disableLoginButton.value = false;
  const body = {
    recaptchaResponse: response,
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user/recaptcha`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => res.data);
};
</script>

<style lang="scss" scoped>
.container {
  max-width: 450px;
  margin: 80px auto;
  letter-spacing: -0.4px;
}
.login-view-wrapper {
  background-image: url("@/assets/background.gif");
  background-size: cover;
  min-height: 100vh;
  padding: 80px 500px;
  .recaptcha {
    margin-bottom: 10px;
  }
  @media (max-width: 1200px) {
    padding: 80px 20px;
  }
}
.card {
  border: 1px solid rgb(226, 226, 226);
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  padding: 0px 10px 10px;
}

.card-header {
  background-color: white;
  color: rgb(44, 44, 44);
  text-align: center;
  border-bottom: none;
  padding: 30px 15px 10px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  .card-header-logo {
    font-weight: bold;
    font-size: 1.5rem;
  }
}

img {
  width: 130px;
}

.form-control {
  border-radius: 5px;
}

.btn-primary {
  border: none;
  width: 100%;
}

.float-end {
  color: black;
  text-decoration: none;
  font-size: 14px;
}

.social-btn {
  display: flex;
  flex-direction: column;
  align-items: center;

  .btn {
    border: none;
    margin: 10px 3px 0;
    opacity: 1;
    width: 100%;
    height: 35px;

    &:hover {
      opacity: 0.9;
    }
  }

  .btn-primary {
    background: #fee500;
  }

  .btn-danger {
    background: #df4930;
  }

  > img {
    width: fit-content;
    cursor: pointer;
    padding: 8px;
  }
}

.or-seperator {
  margin-top: 40px;
  text-align: center;
  border-top: 1px solid #ccc;

  i {
    padding: 0 10px;
    background: #ffffff;
    position: relative;
    top: -11px;
    z-index: 1;
  }
}

.input-group {
  border: 1px solid #ced4da;
  border-radius: 5px;
  margin-bottom: 15px;

  .input-group-text {
    background-color: white;
    border: none;
    color: rgb(44, 44, 44);
    height: 100%;

    i {
      font-size: 1.2rem;
      line-height: inherit;
    }
  }
}

.no-outline {
  outline: none !important;
  box-shadow: none !important;
  border: 0px;
}

.sub-card {
  display: flex;
  justify-content: center;
  align-items: center;
  letter-spacing: -0.4px;

  > a {
    padding: 10px;
    color: rgb(44, 44, 44);
    font-size: 0.92rem;
    text-decoration: none;
  }

  span {
    font-size: small;
  }
}

.form-check-input {
  border: 1px solid rgb(113, 113, 113);
}

label {
  font-size: 1rem;
  position: relative;
  top: -2.5px;
}
</style>
