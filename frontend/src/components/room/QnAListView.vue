<template>
  <div class="QnA">
    <h4>**질문 게시판**</h4>
    <QuestionView />
    <QuestionField />
  </div>
</template>

<script setup>
import AnswerView from './AnswerView.vue'
import QuestionView from './QuestionView.vue'
import QuestionField from '@/components/room/QuestionField.vue'
</script>

<style scoped>
.QnA {
  padding: 2px;
  padding-top: 15px;
  padding-bottom: 15px;
  border: solid 2px black;
  border-radius: 10px;
  background-color: white;
  height: 500px;
  width: 320px;
  position: fixed;
  margin-top: 30px;
  margin-bottom: 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  right: 0;
  bottom: 0;
  z-index: 10;
}

.QuestionField {
  position: absolute;
  bottom: 0;
}
</style>
