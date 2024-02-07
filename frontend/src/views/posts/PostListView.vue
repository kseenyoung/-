<template>
  <div class="container">
    <h2>게시글 목록</h2>
    <hr class="my-4" />
    <div class="row g-3">
      <div v-for="post in posts" :key="post.id" class="col-4">
        <PostItem
          :title="post.title"
          :content="post.content"
          :created-at="post.createdAt"
          @click="goDetailPage(post.id)"
        ></PostItem>
      </div>
    </div>

    <div class="d-flex">
      <button class="btn btn-outline-success" type="button" @click="goCreatePage">글쓰기</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { useBoardStore } from '@/stores/board'
import PostItem from '@/components/posts/PostItem.vue'
const router = useRouter()
const boardStore = useBoardStore()
const goCreatePage = () => {
  router.push({
    name: 'postCreate'
  })
}
const posts = ref([])
const fetchPosts = () => {
  posts.value = boardStore.posts
}
fetchPosts()
const goDetailPage = (id) => {
  router.push({
    name: 'postDetail',
    params: {
      id
    }
  })
}
</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
</style>
