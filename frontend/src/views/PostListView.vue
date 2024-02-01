<template>
  <div>
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
    <button class="btn btn-outline-success" type="button" @click="goPage">글쓰기</button>
  </div>
</template>

<script setup>
import PostItem from '@/components/posts/PostItem.vue'
import { useRouter } from 'vue-router'
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
</script>

<style scoped>
.title {
  font-weight: 900;
  font-size: 50px;
}
.cardlist {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.marketpage {
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.card img {
  border: 10px solid black;
}
</style>
