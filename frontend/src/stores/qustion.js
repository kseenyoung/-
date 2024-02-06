import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

export const useQuestionStore = defineStore('questionStore', () => {
  const question = ref([])

  const setQuestion = async function (question) {
    console.log('question : ' + question)
    question.value.push(question)
  }

  return { question, setQuestion }
})
