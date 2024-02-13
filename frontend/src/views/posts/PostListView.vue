<template>
  <div class="container">
    <h2>게시글 목록</h2>
    <hr class="my-4" />
    <div class="row g-3">
      <div v-for="post in posts" :key="post.boardId" class="col-6">
        <PostItem
          :title="post.boardTitle"
          :created-date="formatDate(post.createdDate)"
          @click="goDetailPage(post.boardId)"
        ></PostItem>
        <!-- <PostItem
          :title="post.boardTitle"
          :created-date="formatDate(post.createdDate)"
          :tag="post.tag.tagName"
          @click="goDetailPage(post.boardId)"
        ></PostItem> -->
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
import SimpleDagak from '@/components/dagak/SimpleDagak.vue';
const router = useRouter()
const boardStore = useBoardStore()

const goCreatePage = () => {
  router.push({
    name: 'postCreate'
  })
}
const posts = ref([])
const fetchPosts = () => {
  boardStore.getPosts();
  console.log('총 포스트:', boardStore.posts)
  posts.value = boardStore.posts;
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

const formatDate = (timestampArray) => {
  const date = new Date(...timestampArray);
  return date.toLocaleString();
};



</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
</style>
