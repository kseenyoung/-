import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useBoardStore = defineStore('boardStore', () => {
  const posts = ref([]);
  const totalPages = ref([]);
  const getPosts = async function (page, keyword) {
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}board/list?page=${page}&keyword=${keyword}`,
      );
      posts.value = response.data.result.boards;
      totalPages.value = response.data.result.totalPages;
    } catch (error) {
      console.log(error);
    }
  };
  const postDetail = ref([]);
  const comments = ref([]);
  const getPostDetail = async function (id) {
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}board/detail/${id}`,
      );
      postDetail.value = response.data.result.board;
      comments.value = response.data.result.comments;
    } catch (error) {
      console.log(error);
    }
  };

  return { posts, getPosts, postDetail, getPostDetail, comments, totalPages };
});
