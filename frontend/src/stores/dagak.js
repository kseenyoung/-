import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useDagakStore = defineStore('dagakStore', () => {
  const todayDagak = ref([]);
  const getTodayDagak = function () {
    axios
      .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getTodayDagak`)
      .then((res) => {
        console.log("다각: ",res)
        console.log(res.data.result)
        todayDagak.value = res.data;
      });
  };

  return { todayDagak, getTodayDagak };
});