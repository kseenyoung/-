<template>
  <div class="container">
    <h2> {{detail.boardTitle}} </h2>
    <button class="btn tag" v-if="detail.boardTag">{{ detail.boardTag.boardTagName }}</button>
    <p>{{detail.boardContent}}</p>
    <p class="text-muted" v-if="detail.createdDate">{{ formatDate(detail.createdDate) }}</p>
    <hr class="my-4" />
    <div class="row g-2">
      <div class="col-auto me-auto"></div>
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

</style>
