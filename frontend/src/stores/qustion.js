import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useQuestionStore = defineStore('questionStore', () => {
  // state: () => {
  //   return {
  //     // all these properties will have their type inferred automatically
  //     question: []
  //   }
  // }
  const question = ref([])

  const setQuestion = async function (data) {
    console.log('question : ' + data)

    question.value.push(data)
  }

  return { question, setQuestion }
})
