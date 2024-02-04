import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useCategoryStore = defineStore(
  'categoryStore',
  () => {
    const categoryList = ref([]);
    const getCategoryList = function () {
      axios
        .get(`${import.meta.env.VITE_API_BASE_URL}category/getAllCategoryList`)
        .then((res) => {
          categoryList.value = res.data.result;
        });
    };

    return { categoryList, getCategoryList };
  },
  { persist: true },
);
