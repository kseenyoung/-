<template>
  <div class="container">
    <h2> {{detail.boardTitle}} </h2>
    <button class="btn tag">{{ detail.tag.tagName }}</button>
    <p>{{detail.boardContent}}</p>
    <p class="text-muted">{{ formatDate(detail.createdDate) }}</p>
    <hr class="my-4" />
    <div class="row g-2">
      <div class="col-auto">
        <button class="btn btn-outline-dark">이전글</button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-dark">다음글</button>
      </div>
      <div class="col-auto me-auto"></div>
      <div class="col-auto">
        <button class="btn btn-outline-dark" @click="goListPage">목록</button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-primary" @click="goEditPage">수정</button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-danger">삭제</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from '@/stores/board'
import { ref } from 'vue'
const boardStore = useBoardStore()
const route = useRoute()
const router = useRouter()
const postId = route.params.id
const detail = ref([])
console.log(route.params.id, detail)
const goListPage = () => {
  router.push({
    name: 'postList'
  })
}
const goEditPage = () => {
  router.push({
    name: 'postEdit',
    params: {
      id: postId
    }
  })
}

const fetchDetail = (id) => {
  boardStore.getPostDetail(id);
  detail.value = boardStore.postDetail;
}
fetchDetail(postId);

const formatDate = (timestampArray) => {
  const date = new Date(...timestampArray);
  return date.toLocaleString();
};

</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
.tag {
  background-color: orange;
  color: white;
  font-size: 12px; 
  padding: 2px 5px;
}

</style>
