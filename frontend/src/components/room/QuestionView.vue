<template>
  <div v-for="(q, index) in questions" :key="index">
    <div class="questionbox">
      <div class="questionlabel" @click="showAnswer(index)">
        <b>{{ q }}</b>
      </div>
      <div class="questiondetail">
        <p>질문자 : {{ userId }}</p>
      </div>
      <div v-show="answer[index]">
        <AnswerView />
        <AnswerField />
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
const userStore = useUserStore()
const loginUserInfo = userStore.loginUserInfo
const userId = loginUserInfo.userId

// 답변
const answer = reactive(questions.map(() => false))
const showAnswer = function (index) {
  answer[index] = !answer[index]
}
</script>

<style scoped>
/* .questiontitle{
    font-weight: 800;
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
} */
.nametag {
  /* margin: 0; */
}
.questionlabel {
  font-weight: 800;
  padding: 3px;
  font-size: 20px;
  border: 1px black dashed;
  display: flex;
  justify-content: space-between;
}

.questiondetail {
  text-align: left;
  padding-left: 10px;
  padding-right: 10px;
  flex: 7;
}

.questionbox {
  border: 2px solid black;
  background-color: white;
  margin-left: 5px;
  margin-right: 5px;
  /* display: flex; */
  padding: 3px;
}
</style>
