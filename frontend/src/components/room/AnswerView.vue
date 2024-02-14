<template>
  <div class="answerbox" v-for="(answer, index) in answers" key="index">
    <div class="answerlabel element">
      <b>{{ answer.data }}</b>
    </div>
    <div class="answerdetail">{{ answer.userId }}</div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { useQuestionStore } from '@/stores/qustion'
import { defineProps, ref } from 'vue'

const props = defineProps({ questionId: String })
const qId = ref(props.questionId)

const questionStore = useQuestionStore()
const answers = questionStore.answer.get(qId.value)
</script>

<style scoped>
/* .answertitle{
    font-weight: 800;
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
} */
.answerbox {
  display: flex;
  flex-direction: column; /* 요소들을 세로로 배치합니다. */
  align-items: flex-end; /* 요소들을 오른쪽으로 정렬합니다. */
  background-color: transparent;
  margin-bottom: 10px;
}

.answerlabel {
  position: relative;
  display: inline-block;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 20px;
  border: 2px dotted black; /* 점선 스타일 설정 */
  background-color: #f5f5f5; /* 밝은 회색 배경 */
}

.answerdetail {
  font-size: 12px; /* 작은 글꼴 크기 적용 */
  width: 20px; /* 작은 너비 적용 */
  height: 10px; /* 작은 높이 적용 */
  align-self: flex-end;
  margin-right: 40px; /* 왼쪽으로 조금만 나오도록 마진 설정 */
}

.element {
  word-wrap: break-word;
  width: 200px;
  font-size: 12px;
}
</style>
