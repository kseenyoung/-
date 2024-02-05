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
        posts.value = response

      } catch (error) {
        console.error(error);
      }
    };
    const postDetail = ref([])
    const getPostDetail = async function (id) {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}board/detail/${id-1}`);
        postDetail.value = response.data.result.board

      } catch (error) {
        console.error(error);
      }
    };

    return { posts, getPosts, postDetail, getPostDetail }
  },
  { persist: true }
)
