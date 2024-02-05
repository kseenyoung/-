<template>
  <div class="container">
    <h2>게시글 작성</h2>
    <hr class="my-4" />
    <form @submit.prevent="savePost">
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">제목</label>
        <input v-model="title" class="form-control" id="title" />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea v-model="content" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
      </div>
      <div class="pt-4">
        <button type="button" class="btn btn-outline-dark me-2" @click="goListPage">목록</button>
        <button class="btn btn-primary"  type="submit">저장</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'
const router = useRouter()
const title  =ref('')
const content = ref('')
const tagId = ref('')
const goListPage = () => {
  router.push({
    name: 'postList'
  })
}

const savePost = async () => {
  try {
    const body = {
    sign: 'addBoard',
    boardTitle: title.value,
    boardContent: content.value,
    tagId: tagId.value,
  };
    const response = await axios.post(`${import.meta.env.VITE_API_BASE_URL}board`, body)

    // Handle the response as needed
    console.log('Post saved:', response.data)

    // Redirect to the list page or perform any other actions
    goListPage()
  } catch (error) {
    console.error('Error saving post:', error)
  }
}

</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
</style>
