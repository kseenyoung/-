<template>
  <div class="container">
    <div class="regist-title">회원가입</div>
    <div class="form-con">
      <div class="input-con row mb-4">
        <label for="id" class="col-sm-2 col-form-label">
          아이디<span>*</span>
        </label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="text"
            id="id"
            placeholder="아이디를 입력하세요"
            required
            autocomplete="off"
            v-model="id"
            :class="{ 'is-valid': idFlag, 'is-invalid': !idFlag }"
          />
          <div v-if="!isValidId" class="form-text">
            영소문자와 숫자 조합의 5~15자리를 사용하세요
          </div>
          <div class="btn-dup">
            <button
              @click="duplicateIdCheck(id)"
              class="btn btn-sm btn-primary"
            >
              중복 확인
            </button>
            <span v-if="dupIdClicked && isDuplicateId" class="form-text">
              사용할 수 있는 아이디입니다
            </span>
            <span v-if="dupIdClicked && !isDuplicateId" class="form-text">
              중복된 아이디입니다
            </span>
          </div>
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="password" class="col-sm-2 form-label">
          비밀번호<span>*</span>
        </label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="password"
            id="password"
            placeholder="비밀번호를 입력하세요"
            required
            v-model="password"
            :class="{ 'is-valid': isValidPw, 'is-invalid': !isValidPw }"
          />
          <div v-if="isValidPw" class="form-text pw-text">
            사용할 수 있는 비밀번호입니다
          </div>
          <div v-else class="form-text pw-text">
            영문자, 숫자 및 특수문자(@&!%*#?&) 조합의 8~15자리를 사용하세요
          </div>

          <input
            class="form-control"
            type="password"
            id="passwordCheck"
            placeholder="비밀번호 재확인"
            required
            v-model="passwordCheck"
            :class="{ 'is-valid': isSamePw, 'is-invalid': !isSamePw }"
          />
          <div v-if="isSamePw" class="form-text">비밀번호가 일치합니다.</div>
          <div v-else class="form-text">비밀번호가 일치하지 않습니다</div>
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="name" class="col-sm-2 form-label">이름<span>*</span></label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="text"
            id="name"
            placeholder="이름을 입력하세요"
            required
            autocomplete="off"
            v-model="name"
          />
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="nickname" class="col-sm-2 form-label">
          닉네임<span>*</span>
        </label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="text"
            id="nickname"
            placeholder="사용하실 닉네임을 입력하세요"
            required
            autocomplete="off"
            :value="nickname"
            @input="onInputNick"
            :class="{
              'is-valid': nicknameFlag,
              'is-invalid': !nicknameFlag,
            }"
          />
          <div v-if="!isValidNickname" class="form-text">
            올바른 닉네임을 사용해주세요.
          </div>
          <button
            @click="duplicateNicknameCheck(nickname)"
            class="btn btn-sm btn-primary"
          >
            중복 확인
          </button>
          <span
            v-if="dupNicknameClicked && isDuplicateNickname"
            class="form-text"
            >사용할 수 있는 닉네임입니다
          </span>
          <span
            v-if="dupNicknameClicked && !isDuplicateNickname"
            class="form-text"
            >중복된 닉네임입니다
          </span>
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="email" class="col-sm-2 form-label">
          이메일<span>*</span>
        </label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="email"
            id="email"
            placeholder="you@example.com"
            autocomplete="off"
            v-model="email"
            :class="{ 'is-valid': isValidEmail, 'is-invalid': !isValidEmail }"
          />
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="birth" class="col-sm-2 form-label">생년월일</label>
        <div class="col-sm-10">
          <div>
            <Datepicker
              v-model="date"
              locale="ko"
              weekStart="0"
              auto-apply
              placeholder="YYYY-MM-DD"
              year-first
              :format="format"
            />
          </div>
        </div>
      </div>

      <div class="input-con row mb-4">
        <label for="tel" class="col-sm-2 form-label">전화번호</label>
        <div class="col-sm-10">
          <input
            class="form-control"
            type="text"
            id="tel"
            placeholder="휴대폰 번호(-제외)"
            autocomplete="off"
            maxlength="11"
            v-model="tel"
            @input="formatPhoneNumber"
          />
        </div>
      </div>

      <div class="d-grid" @click="shake">
        <button
          class="btn btn-primary"
          @click="registUser"
          :disabled="submitFlag"
        >
          가입
        </button>
        <div v-if="submitFlag" class="form-text sub-text">
          입력 조건을 만족해주세요
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, computed, watch } from 'vue';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import axios from 'axios';
const router = useRouter();

const id = ref('');
const password = ref('');
const passwordCheck = ref('');
const name = ref('');
const nickname = ref('');
const email = ref('');
const tel = ref('');

//생일
const date = ref();
const birth = ref(null);
const format = (date) => {
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const year = date.getFullYear();
  birth.value = `${year}-${month}-${day}`;
  return birth.value;
};

//아이디 확인
const isValidId = ref(false);
const isDuplicateId = ref(false);
const dupIdClicked = ref(false);
const idFlag = computed(() => isValidId.value && isDuplicateId.value);

//아이디 입력값 감시
watch(id, (newId) => {
  dupIdClicked.value = false;
  isDuplicateId.value = false;
  checkId(newId);
});

//아이디 유효성 검사(영소문자, 숫자 조합 + 5~15 자리)
const checkId = function (id) {
  const validateId = /^[a-z0-9]{5,15}$/;
  isValidId.value = validateId.test(id);
};

//아이디 중복확인
const duplicateIdCheck = async function (checkId) {
  dupIdClicked.value = true;
  const body = {
    sign: 'isExistId',
    userId: checkId,
  };

  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code == 1002) {
        // 중복 아님
        isDuplicateId.value = true;
      } else {
        // 중복임
        isDuplicateId.value = false;
        alert('이미 존재하는 아이디입니다.');
        id.value = ''; //아이디 텍스트 초기화
      }
    });
};

//비밀번호 확인
const isValidPw = ref(false);

//비밀번호 입력값 감시
watch(password, (newPw) => {
  isSamePw.value = false;
  checkPw(newPw);
  samePw();
});

//비밀번호 유효성 검사(영문자, 숫자 및 특수문자(@&!%*#?&) + 8~15 자리)
const checkPw = function (pw) {
  const validatePw = /^(?=.*\d)(?=.*[@&!%*#?&])[A-Za-z\d@&!%*#?&]{8,15}$/;
  isValidPw.value = validatePw.test(pw);
};

//비밀번호 일치 확인
const isSamePw = ref(false);
watch(passwordCheck, () => {
  isSamePw.value = false;
  samePw();
});
const samePw = function () {
  if (passwordCheck.value === password.value) isSamePw.value = true;
};

//이름 확인
const isValidName = ref(false);
watch(name, (newName) => {
  checkName(newName);
});
//이름 유효성 검사(영어, 한글만 가능)
const checkName = function (name) {
  const validateName = /^[a-zA-Z가-힣]*$/;
  isValidName.value = validateName.test(name);
};

//한글 입력 이슈 -> v-model에서 v-bind로 변경
const onInputNick = function (event) {
  nickname.value = event.currentTarget.value;
};

//닉네임 확인
const isValidNickname = ref(false);
const isDuplicateNickname = ref(false);
const dupNicknameClicked = ref(false);
const nicknameFlag = computed(
  () => isValidNickname.value && isDuplicateNickname.value,
);

watch(nickname, (newNickname) => {
  dupNicknameClicked.value = false;
  isDuplicateNickname.value = false;
  checkNickname(newNickname);
});

//닉네임 유효성 검사(특수문자 불가능 2~8 글자)
const checkNickname = function (name) {
  const validateNickname = /^[a-zA-Z가-힣]{2,8}$/;
  isValidNickname.value = validateNickname.test(name);
};

//닉네임 중복확인
const duplicateNicknameCheck = async function (checkNickname) {
  dupNicknameClicked.value = true;
  const body = {
    sign: 'isExistNickname',
    userNickname: checkNickname,
  };

  await axios
    .post(
      // 로그인 콜백
      `${import.meta.env.VITE_API_BASE_URL}user`,
      body,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )
    .then((res) => res.data)
    .then((json) => {
      console.log(json.code);
      if (json.code == 1004) {
        // 중복 아님
        isDuplicateNickname.value = true;
      } else {
        // 중복임
        isDuplicateNickname.value = false;
        alert('이미 존재하는 닉네임입니다.');
        nickname.value = ''; //닉네임 텍스트 초기화
      }
    });
};

//이메일 확인
const isValidEmail = ref(false);
watch(email, (newEmail) => {
  checkEmail(newEmail);
});

//이메일 유효성 검사('@', '.' 포함)
const checkEmail = function (email) {
  const checkEmail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/; // eslint-disable-line
  isValidEmail.value = checkEmail.test(email);
};

//전화번호 하이픈(-) 추가 메서드
const formatPhoneNumber = function () {
  tel.value = tel.value.replace(/[^0-9]/g, '');
  if (tel.value.length >= 4 && tel.value.length <= 7) {
    tel.value = tel.value.replace(/(\d{3})(\d{1,4})/, '$1-$2');
  } else if (tel.value.length >= 8) {
    tel.value = tel.value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
  }
};

//유저등록
const registUser = function () {
  const body = {
    sign: 'signUp',
    userId: id.value,
    userBirthday: birth.value,
    userName: name.value,
    userPassword: password.value,
    userPhonenumber: tel.value,
    userEmail: email.value,
    userNickname: nickname.value,
  };

  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then(() => {
      alert('회원가입이 완료되었습니다.');
      router.push({
        name: 'login',
      });
    });
};

//회원가입 버튼 flag
const submitFlag = computed(() => {
  return (
    id.value === '' ||
    name.value === '' ||
    password.value === '' ||
    passwordCheck.value === '' ||
    idFlag.value === false ||
    isValidPw.value === false ||
    isSamePw.value === false ||
    isValidName.value === false ||
    nicknameFlag.value === false ||
    isValidEmail.value === false
  );
});
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

  label > span {
    color: red;
  }
  .form-text {
    font-size: 0.9rem;
  }
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
  0% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  50% {
    transform: translateX(5px);
  }
  75% {
    transform: translateX(-5px);
  }
  100% {
    transform: translateX(0);
  }
}
</style>
