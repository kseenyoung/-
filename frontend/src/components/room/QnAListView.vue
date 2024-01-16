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
  padding-top: 15px;
  padding-bottom: 15px;
  border-radius: 15px;
  border: 2px solid black;
  background-color: beige;
  height: 500px;
  width: 320px;
  position: relative;
}

.QuestionField {
  position: absolute;
  bottom: 0;
}

.qnalist{
  height: 350px;
}
</style>