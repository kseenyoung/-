<template>
  <div v-for="(q, index) in questions" :key="index">
    <div class="questionbox" @click="showAnswer(index)">
      <div class="questionlabel">
        <div>
          <b>{{ q }}</b>
        </div>
      </div>
      <div class="questiondetail">
        <p>질문자 : dory</p>
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

// 질문(pinia)
const questionStore = useQuestionStore()
const questions = questionStore.question

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
