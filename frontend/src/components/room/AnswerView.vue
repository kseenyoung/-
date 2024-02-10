<template>
  <div class="answerbox" v-for="(answer, index) in answers" key="index">
    <div class="answerlabel">{{ answer.userId }}</div>
    <div class="answerdetail">
      <p class="nametag">
        <b>{{ answer.data }}</b>
      </p>
    </div>
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
.nametag {
  margin: 0;
}
.answerlabel {
  font-weight: 800;
  padding: 3px;
  font-size: 20px;
  border: 1px black dashed;
}
.answerdetail {
  text-align: left;
  padding-left: 10px;
  padding-right: 10px;
  flex: 7;
}

.answerbox {
  border: 2px solid black;

  background-color: azure;
  margin-left: 5px;
  margin-right: 5px;
  display: flex;
  padding: 3px;
}
</style>
