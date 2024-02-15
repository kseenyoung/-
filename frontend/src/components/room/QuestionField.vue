<template>
  <div class="questionfield">
    <div>
      <label for="message"></label>
      <textarea
        id="message"
        name="message"
        rows="4"
        cols="30"
        placeholder="-- 질문을 입력하세요 --"
        v-model="question"
      ></textarea>

      <button class="question" style="margin: 3%; width: 50%" @click="sendQuestion()">
        질문
      </button>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useUserStore } from "@/stores/user";

const question = ref("");
let userStore = useUserStore();
let loginUserInfo = userStore.loginUserInfo;

const checkStore = function () {
  if (userStore == null || userStore == undefined) {
    userStore = useUserStore();
    loginUserInfo = userStore.loginUserInfo;
  }
};

const sendQuestion = async function () {
  checkStore();

  if (question.value == "") {
    alert("질문을 입력해주세요.");
    return;
  }

  sendAxios(1);
  sendAxios(2);
  sendAxios(3);
  question.value = "";
};

const sendAxios = function (sessionNumbser) {
  const body = {
    sign: "askQuestion",
    session: loginUserInfo.sub + sessionNumbser,
    userId: loginUserInfo.userId,
    data: question.value,
  };
  // alert(body.session + body.userId + body.data)

  axios.post(`${import.meta.env.VITE_API_BASE_URL}room`, body).then((res) => {});
};
</script>

<style scoped>
textarea {
  box-sizing: border-box;
  width: calc(100% - 10px);
  border: 2px solid black;
  margin-left: 5px;
  margin-right: 5px;
}
.question {
  border: 1px black dashed;
  font-weight: 800;
  font-size: 16px;
  padding: 5px 8px;
  display: inline-block;
}

#message {
  font-weight: 800;
  font-size: 15px;
  text-align: left;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
