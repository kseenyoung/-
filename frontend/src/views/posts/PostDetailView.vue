<template>
<div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <div class="content-header">
      <div class="row g-2">
        <div class="col-auto me-6"></div>
        <div class="col-auto">
          <button class="btn btn-outline-dark" @click="goListPage">목록</button>
        </div>
        <div class="col-auto">
          <button v-if="detail.userId === userStore.loginUserInfo.userId"  class="btn btn-outline-primary" @click="goEditPage">수정</button>
        </div>
        <div class="col-auto">
          <button v-if="detail.userId === userStore.loginUserInfo.userId"  class="btn btn-outline-danger" @click="deletePost">삭제</button>
        </div>
      </div>
  </div>
    <div class="content">
      <h2> {{detail.boardTitle}} </h2>
      <button class="btn tag" v-if="detail.boardTag">{{ detail.boardTag.boardTagName }}</button>
      <p>{{detail.boardContent}}</p>
      <p class="text-muted" v-if="detail.createdDate">{{ formatDate(detail.createdDate) }}</p>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from '@/stores/board'
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user';
import axios from 'axios';

const boardStore = useBoardStore()
const route = useRoute()
const router = useRouter()
const postId = route.params.id
const detail = ref([])
const userStore = useUserStore();

const deletePost = async () =>{
  let flag = confirm("정말로 삭제하시겠습니까?")
  if(flag){
      try {
    const body = {
      sign: 'deletePost',
      boardId : detail.value.boardId
    };
    console.log(detail.value);
    const response = await axios
      .post(`${import.meta.env.VITE_API_BASE_URL}board`, body)
      goListPage()
      console.log('새 포스트 : ', response)
    } catch (error) {
      console.log('Error saving post:', error)
    }
  }
} 

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

const fetchDetail = async (id) => {
  await boardStore.getPostDetail(id);
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
.container {
  margin: 80px auto;
  font-size: 1rem;
}
.boardPage {
  width: 100%;
  background-image: url('@/assets/background.gif');
  background-color: rgba(0, 0, 0, 0.5);
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  flex-direction: column; /* 컬럼 방향으로 요소들을 정렬 */
  align-items: center; /* 수평 방향으로 중앙 정렬 */
  justify-content: center; /* 수직 방향으로 중앙 정렬 */
  color: white;  
}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
  z-index: 0;
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
.content{
  z-index: 1;
  width: 70%;
  margin-left: 30%;
}
.content-header{
  z-index: 1;

}

</style>
