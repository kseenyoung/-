<template>
    <div class="QnA">
        <p>질문 게시판</p>
        <div class="qnalist">
        <QuestionView/>
        <AnswerView/>
      </div>
        <QuestionField/>
    </div>
</template>

<script setup>
import AnswerView from './AnswerView.vue';
import QuestionView from './QuestionView.vue';
import QuestionField from "@/components/room/QuestionField.vue";


</script>

<style scoped>
.QnA{
  padding: 2px;
  padding-top: 15px;
  padding-bottom: 15px;
  border: 4px solid black;
  background-color: white;
  height: calc(100% - 30px);
  width: 320px;
  position: relative;
  margin-top: 30px;
  margin-bottom: 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
}

.QuestionField {
  position: absolute;
  bottom: 0;
}

</style>