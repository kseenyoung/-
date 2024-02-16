<template>
  <div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <div class="post-wrapper">
      <div class="post-search mb-2">
        <input
          type="text"
          class="form-control mx-2"
          placeholder="제목을 입력하고 엔터로 검색하세요"
          v-model="searchTitle"
          @keydown.enter="fetchPosts(0)"
        />
        <button
          v-if="userStore.loginUserInfo.userId"
          class="btn common-btn-light"
          @click="goCreatePage"
        >
          글쓰기
        </button>
      </div>

      <div class="apply-pagenation">
        <nav aria-label="...">
          <ul class="pagination">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <a class="page-link" @click="goToPage(currentPage - 1)">
                <i class="bi bi-chevron-left"></i>
              </a>
            </li>
            <li
              v-for="page in pageCount"
              :key="page"
              class="page-item"
              :class="{ active: currentPage === page }"
            >
              <a class="page-link" @click="goToPage(page)">
                {{ page }}
              </a>
            </li>
            <li
              class="page-item"
              :class="{
                disabled:
                  currentPage === pageCount || currentPage === totalPages,
              }"
            >
              <a class="page-link" @click="goToPage(currentPage + 1)">
                <i class="bi bi-chevron-right"></i>
              </a>
            </li>
          </ul>
        </nav>
      </div>

      <div class="post-list">
        <template v-for="post in posts" :key="post.boardId">
          <PostItem
            :title="post.boardTitle"
            :created-date="formatDate(post.createdDate)"
            :tag="post.boardTag.boardTagName"
            :user-id="post.userId"
            @click="goDetailPage(post.boardId)"
          ></PostItem>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, computed } from 'vue';
import { useBoardStore } from '@/stores/board';
import PostItem from '@/components/posts/PostItem.vue';
import { useUserStore } from '@/stores/user';
const router = useRouter();
const boardStore = useBoardStore();
const userStore = useUserStore();

const goCreatePage = () => {
  router.push({
    name: 'postCreate',
  });
};
const posts = ref([]);
const totalPages = ref(0);
const currentPage = ref(1);
const displayPage = 9;
const searchTitle = ref('');

const fetchPosts = async (page) => {
  await boardStore.getPosts(page, searchTitle.value);
  posts.value = boardStore.posts;
  totalPages.value = boardStore.totalPages;
};

fetchPosts(0);
const goDetailPage = (id) => {
  router.push({
    name: 'postDetail',
    params: {
      id,
    },
  });
};
const goToPage = (page) => {
  currentPage.value = page;
  fetchPosts(page - 1);
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
  const year = date.getFullYear();
  const month = date.getMonth();
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
};
</script>

<style lang="scss" scoped>
.boardPage {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
}
.post-wrapper {
  color: white;
  z-index: 1;
  width: 55%;
  height: 65%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .post-search {
    .form-control {
      width: 350px;
      display: inline-block;
    }
  }
  .post-list {
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
  }
  .post-list::-webkit-scrollbar {
    display: none;
  }
}
</style>
