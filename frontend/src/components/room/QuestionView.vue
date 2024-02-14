<template>
  <div v-for="(question, index) in questions" :key="index">
    <div class="questionbox">
      <div class="questionlabel" @click="showAnswer(index)">
        <b>{{ question.data }}</b>
      </div>
      <div class="questiondetail">
        <p>{{ question.userId }}</p>
      </div>
      <div v-show="answer[index]">
        <AnswerView
          v-for="(answer, index2) in answers"
          :key="index2"
          :questionId="question.questionId"
        />
        <AnswerField :questionId="question.questionId" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/qustion'
import AnswerView from './AnswerView.vue'
import AnswerField from './AnswerField.vue'
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'

// 질문(pinia)
const questionStore = useQuestionStore()
const questions = questionStore.question
const answers = questionStore.answer
const userStore = useUserStore()
const loginUserInfo = userStore.loginUserInfo

// 답변
const answer = reactive(questions.map(() => false))
const showAnswer = function (index) {
  answer[index] = !answer[index]
}
</script>

<style scoped>

.questionlabel {
  position: relative;
  display: inline-block;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 20px;
  border: 2px dotted black; /* 점선 스타일 설정 */
  background-color: rgba(255, 255, 0, 0.8);
  font-size: 12px;
  font-family: 'Galmuri14';
}

.questiondetail {
  text-align: right; /* 텍스트를 오른쪽으로 정렬합니다. */
  flex: 1; /* 남은 공간을 모두 차지하며 오른쪽으로 밀어냅니다. */
  padding-right: 10px; /* 오른쪽에 약간의 여백을 추가합니다. */
  margin-bottom: 10px;
  font-size: 12px; /* 작은 글꼴 크기 적용 */
  width: 20px; /* 작은 너비 적용 */
  height: 10px; /* 작은 높이 적용 */
}

.questionbox {
  border-radius: 20px; /* 물풍선 형태를 만들기 위해 테두리의 반지름 설정 */
  border: none;
  background-color: rgb(188, 188, 188);  margin-left: 5px; /* 간격 조정 */
  margin-right: 10px; /* 간격 조정 */
  margin-bottom: none;
  padding: 5px; /* 내용과 상자 경계 사이의 여백을 늘리기 위해 내용 패딩 증가 */
}
/* 추가된 스타일 */
.questionbox:hover {
  background-color: #f0f0f0; /* 호버 효과 추가 */
  cursor: pointer; /* 마우스 커서 변경 */
}

.questionbox:focus {
  outline: none; /* 포커스 효과 제거 */
}


.div2 {
  box-shadow:   -1px 0 0 0 black,
                 1px 0 0 0 black,
                 0 -1px 0 0 black,
                 0 1px 0 0 black;
}
</style>
