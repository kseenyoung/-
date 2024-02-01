<template>
  <div class="apply-mokkoji-wrapper">
    <div class="apply-title">
      모꼬지 신청
      <button
        class="btn common-btn"
        data-bs-toggle="modal"
        data-bs-target="#createMokkoji"
        v-show="userStore.loginUserInfo.mokkojiId === null"
      >
        새로 만들기
      </button>
    </div>
    <ApplyMokkojiCreateModal @update-list="getMokkojiList()" />
    <!-- 검색 -->
    <div class="apply-search input-group mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="모꼬지 검색"
        aria-describedby="button-addon2"
        v-model="searchText"
        @keyup.enter="getSearchMokkojiList"
      />
      <select class="form-select" v-model="searchChoose">
        <option selected value="keyword">길드명</option>
        <option value="categories">태그</option>
      </select>
      <button
        class="btn common-btn"
        type="button"
        id="button-addon2"
        @click="getSearchMokkojiList"
      >
        검색
      </button>
    </div>

    <!-- 목록 -->
    <div class="apply-content">
      <table class="table">
        <thead class="my-thead">
          <tr>
            <th>길드명</th>
            <th>길드장</th>
            <th>태그</th>
          </tr>
        </thead>
        <tbody>
          <tr
            class="my-hover"
            v-for="mokkoji in mokkojiList"
            :key="mokkoji.mokkoji.mokkojiId"
          >
            <td>
              <RouterLink :to="`/mokkoji/${mokkoji.mokkoji.mokkojiId}`">{{
                mokkoji.mokkoji.mokkojiName
              }}</RouterLink>
            </td>
            <td>{{ mokkoji.mokkoji.leaderId }}</td>
            <td>
              <span
                v-for="category in mokkoji.categories"
                :key="category.categoryId"
              >
                #{{ category.categoryName }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="apply-pagenation">
      <nav aria-label="...">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 0 }">
            <a
              class="page-link"
              tabindex="-1"
              aria-disabled="true"
              @click="changePage(currentPage - 1)"
            >
              <i class="bi bi-chevron-left"></i>
            </a>
          </li>
          <li
            v-for="page in mokkojiListPage"
            :key="page"
            class="page-item"
            :class="{ active: currentPage === page - 1 }"
          >
            <a class="page-link" @click="changePage(page - 1)">
              {{ page }}
            </a>
          </li>
          <li
            class="page-item"
            :class="{ disabled: currentPage === mokkojiListPage - 1 }"
          >
            <a class="page-link" href="#" @click="changePage(currentPage + 1)">
              <i class="bi bi-chevron-right"></i>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/user';
import { useCategoryStore } from '@/stores/category';
import axios from 'axios';
import ApplyMokkojiCreateModal from '@/components/apply/ApplyMokkojiCreateModal.vue';

const userStore = useUserStore();
const categoryStore = useCategoryStore();

const mokkojiList = ref([]);
const mokkojiListPage = ref();
const currentPage = ref(0);
const searchText = ref('');
const searchChoose = ref('keyword');

onMounted(() => {
  getMokkojiList();
  categoryStore.getCategoryList();
});

const changePage = (newPage) => {
  currentPage.value = newPage;
  getSearchMokkojiList();
};

const getMokkojiList = function () {
  axios.get(`${import.meta.env.VITE_API_BASE_URL}mokkoji/list`).then((res) => {
    mokkojiList.value = res.data.result.list;
    mokkojiListPage.value = res.data.result.totalPages;
  });
};

// eslint-disable-next-line vue/return-in-computed-property
const apiUrl = computed(() => {
  if (searchChoose.value === 'keyword') {
    return `${import.meta.env.VITE_API_BASE_URL}mokkoji/list?categories=&keyword=${searchText.value}&page=${currentPage.value}`;
  } else if (searchChoose.value === 'categories') {
    const categoryId = getCategoryByName(searchText.value);
    return `${import.meta.env.VITE_API_BASE_URL}mokkoji/list?categories=${categoryId}&keyword=&page=${currentPage.value}`;
  }
});

//태그 이름으로 검색 후 categoryId 반환 메서드
const getCategoryByName = (categoryName) => {
  if (searchText.value === '') {
    return '';
  }
  const partialMatch = categoryStore.categoryList.find((c) =>
    c.categoryName.includes(categoryName),
  );
  return partialMatch?.categoryId || '';
};

//모꼬지 검색
const getSearchMokkojiList = function () {
  axios.get(apiUrl.value).then((res) => {
    mokkojiList.value = res.data.result.list;
    mokkojiListPage.value = res.data.result.totalPages;
  });
};
</script>

<style lang="scss" scoped>
.apply-mokkoji-wrapper {
  padding: 30px 30px;
  .apply-search {
    select {
      max-width: 100px;
    }
  }
  .my-thead {
    tr {
      > th:nth-child(3) {
        width: 50%;
      }
    }
  }
}
</style>
