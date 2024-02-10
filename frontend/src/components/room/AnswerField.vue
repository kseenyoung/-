<template>
  <div class="questionfield">
    <div>
      <!-- <label for="message"></label> -->
      <textarea
        id="message"
        name="message"
        rows="4"
        cols="30"
        placeholder="-- 지식을 공유해주세요^^ --"
        v-model="answer"
      ></textarea>
      <button class="question" @click="sendAnswer()">답변 달기</button>
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

  if (answer.value == '') {
    alert('답변을 입력해주세요.')
    return
  }

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
  // alert(body.session + ' ' + body.data + ' ' + body.questionId + ' ' + body.userId)

  axios.post(`${import.meta.env.VITE_API_BASE_URL}room`, body).then((res) => {
    console.log('답변 결과' + res.data)
  })
}
</script>

<style scoped>
.questionfield {
  display: flex; /* 요소들을 행으로 배치하기 위해 flex를 사용합니다. */
  justify-content: space-between; /* 요소 사이의 간격을 최대한 늘립니다. */
  margin-bottom: 20px; /* 아래 여백 추가 */
}
textarea {
  box-sizing: border-box; /* box-sizing을 border-box로 설정 */
  width: calc(100% - 10px);
  border: 2px solid black;
  margin-left: 5px;
  margin-right: 5px;
}
/* 기존 스타일 유지 */
.question {
  border: 1px black dashed;
  font-family: 'NanumSquareNeo';
  font-weight: 800;
  font-size: 16px; /* 작은 크기로 변경 */
  padding: 5px 8px; /* 내부 패딩 조정 */
  margin-right: 10px;
  display: inline-block;
  float: right;
}

/* 추가된 스타일 */
.question:hover {
  background-color: #f0f0f0; /* 호버 효과 추가 */
  cursor: pointer; /* 마우스 커서 변경 */
}

.question:focus {
  outline: none; /* 포커스 효과 제거 */
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
