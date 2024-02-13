<template>
  <div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <hr class="my-4" />
    <input
      type="text"
      class="search_bar"
      placeholder="제목을 입력하고 엔터로 검색하세요"
      v-model="searchTitle"
      @keydown.enter="fetchPosts(0)"
    />
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
    <div class="pagination" v-if="totalPages > 1">
      <button
        v-for="n in pageCount"
        :key="n"
        @click="goToPage(n)"
        :class="{ active: currentPage === n }"
      >
        {{ n }}
      </button>
    </div>
    <div class="d-flex">
      <button
        v-if="userStore.loginUserInfo.userId"
        class="post-create-btn"
        type="button"
        @click="goCreatePage"
      >
        글쓰기
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref, computed } from "vue";
import { useBoardStore } from "@/stores/board";
import PostItem from "@/components/posts/PostItem.vue";
import { useUserStore } from "@/stores/user";
const router = useRouter();
const boardStore = useBoardStore();
const userStore = useUserStore();

const goCreatePage = () => {
  router.push({
    name: "postCreate",
  });
};
const posts = ref([]);
const totalPages = ref(0); // 총 페이지 수
const currentPage = ref(1); // 현재 페이지
const displayPage = 5; // 한 번에 보여줄 페이지 수
const searchTitle = ref(""); // 검색어를 저장할 데이터

const fetchPosts = async (page) => {
  await boardStore.getPosts(page, searchTitle.value);
  console.log("총 포스트:", boardStore.posts);
  posts.value = boardStore.posts;
  totalPages.value = boardStore.totalPages;
};

fetchPosts(0);
const goDetailPage = (id) => {
  router.push({
    name: "postDetail",
    params: {
      id,
    },
  });
};
const goToPage = (page) => {
  currentPage.value = page; // 현재 페이지 업데이트
  fetchPosts(page - 1); // 현재 페이지에 맞는 게시글 다시 가져오기
};

const pageCount = computed(() => {
  let start = Math.max(1, currentPage.value - Math.floor(displayPage / 2));
  let end = Math.min(totalPages.value, start + displayPage - 1);
  if (totalPages.value < displayPage) {
    start = 1;
  }
  return Array.from({ length: end - start + 1 }, (v, i) => start + i);
});

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
  background-image: url("@/assets/background.gif");
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
.postList {
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  overflow-y: auto; /* 스크롤 기능을 유지합니다. */
  height: 60%;
  margin-left: 20%;
}
.post {
  width: 40%;
  margin: 1%;
}
.postList::-webkit-scrollbar {
  display: none;
}
.post-create-btn {
  z-index: 1;
  background-color: #4b4b4b; /* 칠판과 비슷한 짙은 회색 배경색 */
  color: white; /* 텍스트 색상은 흰색 */
  border: none; /* 테두리 제거 */
  padding: 10px 20px; /* 패딩으로 버튼 내부 공간 조절 */
  cursor: pointer; /* 마우스를 올렸을 때 커서 모양을 손가락 모양으로 변경 */
}
.pagination {
  z-index: 3;
}
.search_bar {
  width: 50%;
  z-index: 1;
}
</style>
