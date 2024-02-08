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
const userStore = useUserStore()
const loginUserInfo = userStore.loginUserInfo

const sendQuestion = async function () {
  // console.log('question : ' + question.value)
  console.log('loginUserInfo : ' + loginUserInfo.value)

  const body = {
    sign: 'askQuestion',
    session: 'SQLD3',
    userId: 'ssafy',
    data: '질문1'
  }

  // axios
  //   .post(`${import.meta.env.VITE_API_BASE_URL}room`, body)
  //   .then((res) => {
  //     console.log(res.data.result)
  //     // dagakList.value = res.data.result;
  //   });
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
  font-size: 20px;
  padding: 5px;
  right: 0;
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
