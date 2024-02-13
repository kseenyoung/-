<template>
<div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <hr class="my-4" />
    <div class="postList">
      <div v-for="post in posts" :key="post.boardId" class="post">
        <PostItem
          :title="post.boardTitle"
          :created-date="formatDate(post.createdDate)"
          :tag="post.boardTag.boardTagName"
          @click="goDetailPage(post.boardId)"
        ></PostItem>
      </div>
    </div>
  
    <div class="d-flex">
    <button class="post-create-btn" type="button" @click="goCreatePage">글쓰기</button>
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
const fetchPosts = async () => {
  await boardStore.getPosts();
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
  margin: 80px auto;
  font-size: 1rem;
}
.boardPage {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  flex-direction: column; /* 컬럼 방향으로 요소들을 정렬 */
  align-items: center; /* 수평 방향으로 중앙 정렬 */
  justify-content: center; /* 수직 방향으로 중앙 정렬 */

}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
}
.postList{
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  overflow-y: auto; /* 스크롤 기능을 유지합니다. */
  height: 60%; 
  margin-left: 20%;
}
.post{
  width: 40%;
  margin: 1%;
}
.postList::-webkit-scrollbar {
  display: none;
}
.post-create-btn {
  z-index: 1;
  background-color: #4B4B4B; /* 칠판과 비슷한 짙은 회색 배경색 */
  color: white; /* 텍스트 색상은 흰색 */
  border: none; /* 테두리 제거 */
  padding: 10px 20px; /* 패딩으로 버튼 내부 공간 조절 */
  cursor: pointer; /* 마우스를 올렸을 때 커서 모양을 손가락 모양으로 변경 */
}

</style>
