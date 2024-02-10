<template>
  <div class="container">
    <h2>게시글 수정</h2>
    <hr class="my-4" />
    <form @submit.prevent="savePost">
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">제목</label>
        <input  class="form-control" id="title" v-model="detail.boardTitle" />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" v-model="detail.boardContent"></textarea>
      </div>
      <div class="pt-4">
        <button type="button" class="btn btn-outline-danger me-2" @click="goDetailPage">
          취소
        </button>
        <button class="btn btn-primary">수정</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from '@/stores/board';
import { ref } from 'vue'

const route = useRoute()
const router = useRouter()
const boardStore = useBoardStore()
const id = route.params.id
const detail = ref([])

const fetchDetail = (id) => {
  boardStore.getPostDetail(id);
  detail.value = boardStore.postDetail;
}
fetchDetail(id);

const goDetailPage = () =>
  router.push({
    name: 'postDetail',
    params: { id }
  })

  const savePost = async () => {
  try {
    const body = {
      sign: 'modifyPost',
      boardTitle: title.value,
      boardContent: content.value,
      tagId: tag.value,
    };
    const response = await axios
      .post(`${import.meta.env.VITE_API_BASE_URL}board`, body)
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
