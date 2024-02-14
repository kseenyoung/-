<template>
  <div class="QnA overflow-auto">
    <h4>[ 질문 게시판 ]</h4>
    <QuestionView />
    <br />
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
  border-radius: 5px;
  background-color: rgb(188, 188, 188);
  height: 500px;
  width: 320px;
  margin-top: 30px;
  margin-bottom: 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5); /* 그림자 효과 추가 */
  position: fixed;
  right: 350px;
  bottom: 0;
  justify-content: center;
}

.QnA h4 {
  text-align: center; /* 텍스트를 가운데 정렬합니다. */
}
</style>
