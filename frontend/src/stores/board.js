import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

// export function getPosts() {
//   return posts
// }

export const useBoardStore = defineStore(
  'boardStore',
  () => {
    const posts = ref([])
    const getPosts = async function () {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}board/list?page=0&keyword=`);
        posts.value = response.data.result.boards
      } catch (error) {
        console.log(error);
      }
    };
    const postDetail = ref([])
    const getPostDetail = async function (id) {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}board/detail/${id}`);
        postDetail.value = response.data.result.board
      } catch (error) {
        console.log(error);
      }
    };

    return { posts, getPosts, postDetail, getPostDetail }
  }
)
