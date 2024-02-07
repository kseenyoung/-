import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useDagakStore = defineStore('dagakStore', () => {
  const todayDagak = ref([]);
  const getTodayDagak = async function () {
    const response = await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/dagak/getTodayDagak`,
      );
        if(response.data.code === 1000){
            todayDagak.value = response.data.result;
        }
        else{
            alert(response.data.message);
        }
  };

  return { todayDagak, getTodayDagak };
});
