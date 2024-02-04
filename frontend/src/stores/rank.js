import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

export const useRankStore = defineStore('rankStore', () => {
const mokkojiRank = ref()
const getMokkojiRank = async function () {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/mokkoji/rank10`);

    if(response.data.code === 1000){
      mokkojiRank.value = response.data.result;
    }
    
  } catch (error) {
    console.error(error);
  }
};

  return {mokkojiRank, getMokkojiRank }
}, { persist: true })