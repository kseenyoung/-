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
      <button class="question" @click="sendQuestion()">질문</button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'

const question = ref('')
let userStore = useUserStore()
let loginUserInfo = userStore.loginUserInfo

const checkStore = function () {
  console.log('userStore : ' + userStore)
  if (userStore == null || userStore == undefined) {
    userStore = useUserStore()
    loginUserInfo = userStore.loginUserInfo
  }
}

const sendQuestion = async function () {
  // console.log('question : ' + question.value)
  checkStore()
  // alert('질문이 등록되었습니다.')
  // console.log('loginUserInfo : ' + loginUserInfo)

  if (question.value == '') {
    alert('질문을 입력해주세요.')
    return
  }

  sendAxios(1)
  sendAxios(2)
  sendAxios(3)
  question.value = ''
}

const sendAxios = function (sessionNumbser) {
  const body = {
    sign: 'askQuestion',
    session: loginUserInfo.sub + sessionNumbser,
    userId: loginUserInfo.userId,
    data: question.value
  }
  // alert(body.session + body.userId + body.data)

  axios.post(`${import.meta.env.VITE_API_BASE_URL}room`, body).then((res) => {
    console.log(res.data.result)
  })
}
</script>

<style scoped>
textarea {
  box-sizing: border-box; /* box-sizing을 border-box로 설정 */
  width: calc(100% - 10px);
  border: 2px solid black;
  margin-left: 5px;
  margin-right: 5px;
}
.question {
  border: 1px black dashed;
  font-family: 'NanumSquareNeo';
  font-weight: 800;
  font-size: 16px; /* 작은 크기로 변경 */
  padding: 5px 8px; /* 내부 패딩 조정 */
  /* margin-right: 10px; */
  display: inline-block;
  float: right;
}

#message {
  font-family: 'NanumSquareNeo';
  font-weight: 600;
  font-size: 15px;
  text-align: left;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
