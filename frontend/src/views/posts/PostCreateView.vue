<template>
  <div class="container">
    <h2>게시글 작성</h2>
    <hr class="my-4" />
    <form @submit.prevent="savePost">
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">제목</label>
        <input v-model="title" class="form-control" id="title" />
      </div>
      <!-- <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">태그</label>
        <input  class="form-control" id="tag" />
      </div> -->

      <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
          태그
        </button>
        <ul class="dropdown-menu">
          <li v-for=" tag in tagList">
            <a class="dropdown-item" href="#">{{ tag }}</a>
          </li>
        </ul>
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea v-model="content" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
      </div>
      <div class="pt-4">
        <button type="button" class="btn btn-outline-dark me-2" @click="goListPage">목록</button>
        <button class="btn btn-primary" type="submit">저장</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'
const router = useRouter()
const title = ref('')
const content = ref('')
const tagList = ref(['구해요', '정보'])
const tag = ref('')

// 목록 페이지로 돌아가기
const goListPage = () => {
  router.push({
    name: 'postList'
  })
}
// 게시글 저장 메서드
const savePost = async () => {
  try {
    const body = {
      sign: 'modifyPost',
      boardTitle: title.value,
      boardContent: content.value,
      tagId: tag.value,
    };
    const response = await axios
      .post(`${import.meta.env.VITE_API_BASE_URL}boardTest/boardCreate`, body)
      goListPage()
      console.log('새 포스트 : ', response)
    } catch (error) {
      console.log('Error saving post:', error)
    }
}

</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
</style>
