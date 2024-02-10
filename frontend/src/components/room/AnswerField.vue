<template>
  <div class="questionfield">
    <div>
      <label for="message"></label>
      <textarea
        id="message"
        name="message"
        rows="4"
        cols="30"
        placeholder="-- 지식을 공유해주세요^^ --"
        v-model="answer"
      ></textarea>
      <button class="question" @click="sendAnswer()">답변</button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { useQuestionStore } from '@/stores/qustion'
import { useUserStore } from '@/stores/user'
import { ref, defineProps } from 'vue'

const props = defineProps({ questionId: String })
const qId = ref(props.questionId)
const userStore = useUserStore()
const loginUserInfo = userStore.loginUserInfo
const answer = ref('')

const sendAnswer = async function () {
  // console.log('questionId : ' + qId.value)
  // checkStore()

  sendAxios(1)
  sendAxios(2)
  sendAxios(3)

  answer.value = ''
}

const sendAxios = function (sessionNumbser) {
  const body = {
    session: loginUserInfo.sub + sessionNumbser,
    sign: 'answerQuestion',
    data: answer.value,
    userId: loginUserInfo.userId,
    questionId: qId.value
  }
  alert(body.session + ' ' + body.data + ' ' + body.questionId + ' ' + body.userId)

  axios.post(`${import.meta.env.VITE_API_BASE_URL}room`, body).then((res) => {
    console.log('답변 결과' + res.data)
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
