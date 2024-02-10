import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStopWatch = defineStore('stopWatch', () => {
    const sec = ref(0);
    const min = ref(0);
    const hour = ref(0);
    const stopFlag = ref(false);

    setInterval(() => sec.value +=1, 1000)
 

  return { hour, min, sec, stopFlag }
})
