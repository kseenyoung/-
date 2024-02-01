<template>
  <div class="container">
    <h1>게시글 목록</h1>
    <hr class="my-4" />
    <div class="row g-3">
      <div v-for="post in posts" :key="post.id" class="col-4">
        <PostItem
          :title="post.title"
          :content="post.content"
          :created-at="post.createdAt"
          @click="goPage(post.id)"
        ></PostItem>
      </div>
    </div>
  </div>
  <div class="d-flex">
    <button class="btn btn-outline-success" type="button" @click="goWrite">글쓰기</button>
  </div>
</template>

<script setup>
import PostItem from '@/components/posts/PostItem.vue'
import { useRouter, useRoute } from 'vue-router'
import { getPosts } from '@/api/posts'
import { ref } from 'vue'
const router = useRouter()
const posts = ref([])
const fetchPosts = () => {
  posts.value = getPosts()
}
fetchPosts()
const goPage = (id) => {
  router.push(`/posts/${id}`)
}
const goWrite = () => router.push({ name: 'PostCreate' })
</script>

<style scoped>
.title {
  font-weight: 900;
  font-size: 50px;
}
.container {
  margin: 80px;
}
</style>
