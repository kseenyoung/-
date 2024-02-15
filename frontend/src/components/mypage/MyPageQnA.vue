<template>
  <div class="common-mypage-wrapper" v-if="paginatedQuestionList != ''">
    <div class="common-mypage-title">내 질문</div>

    <div class="qna-content-wrapper">
      <div
        v-for="question in paginatedQuestionList"
        :key="question.questionId"
        class="qna-wrapper"
      >
        <div class="question">
          <img src="@/assets/img/mypage/question.png" class="question-img" />
          <span v-html="highlightSearchText(question.data)"></span>
          <span class="qna-date">{{ question.date }}</span>
        </div>

        <template v-for="answer in answerList" :key="answer.answerId">
          <div v-if="answer.questionId === question.questionId" class="answer">
            <i class="bi bi-arrow-return-right"></i> {{ answer.data }}
            <span class="qna-date">{{ answer.date }}</span>
          </div>
        </template>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div class="apply-pagenation">
      <nav aria-label="...">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <a class="page-link" @click="prevPage">
              <i class="bi bi-chevron-left"></i>
            </a>
          </li>
          <li
            v-for="page in totalPages"
            :key="page"
            class="page-item"
            :class="{ active: currentPage === page }"
          >
            <a class="page-link" @click="changePage(page)">
              {{ page }}
            </a>
          </li>
          <li
            class="page-item"
            :class="{ disabled: currentPage === totalPages }"
          >
            <a class="page-link" @click="nextPage">
              <i class="bi bi-chevron-right"></i>
            </a>
          </li>
        </ul>
      </nav>
    </div>

    <!-- 검색 -->
    <div class="input-group mb-3 search-wrapper">
      <input
        :value="searchText"
        @input="onInputSearch"
        type="text"
        class="form-control"
        placeholder="질문 검색"
        aria-describedby="button-addon2"
        @keyup.enter="searchQuestions"
      />
      <button @click="searchQuestions" class="btn common-btn">검색</button>
    </div>
  </div>
  <div v-else class="common-mypage-wrapper">
    <div class="common-mypage-title">내 질문</div>
    등록된 질문이 없습니다.
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import axios from 'axios';

const questionList = ref([]);
const answerList = ref([]);
const searchText = ref('');
const filteredQuestionList = ref([]);
const itemsPerPage = ref(7);
let currentPage = ref(1);

onMounted(() => {
  getUserQnA();
});

watch(searchText, () => {
  if (searchText.value === '') {
    getUserQnA();
  }
});

//QnA 목록 가져오기
const getUserQnA = function () {
  const body = {
    sign: 'getUserQnA',
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}room`, body).then((res) => {
    console.log(res);
    if (res.data.code === 1000) {
      //성공
      questionList.value = res.data.result.questionVOList;
      answerList.value = res.data.result.answerVOList;
      filteredQuestionList.value = res.data.result.questionVOList;
    } else {
      alert(res.data.message);
    }
  });
};

//질문 한글 입력 이슈
const onInputSearch = function (event) {
  searchText.value = event.currentTarget.value;
};

//질문 검색
const searchQuestions = function () {
  filteredQuestionList.value = questionList.value.filter((question) =>
    question.data.toLowerCase().includes(searchText.value.toLowerCase()),
  );
};

//검색 시 하이라이트 css
const highlightSearchText = function (text) {
  if (!searchText.value) {
    return text;
  }
  const regex = new RegExp(`(${searchText.value})`, 'gi');
  return text.replace(regex, '<span class="highlighted">$1</span>');
};

//페이지네이션
const totalPages = computed(() =>
  Math.ceil(questionList.value.length / itemsPerPage.value),
);

//페이지별 목록
const paginatedQuestionList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return filteredQuestionList.value.slice(start, end);
});

//페이지네이션 숫자 클릭으로 이동
const changePage = (newPage) => {
  currentPage.value = newPage;
};

//페이지네이션 화살표 이동
const prevPage = function () {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};
const nextPage = function () {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};
</script>

<style lang="scss" scoped>
.qna-content-wrapper {
  min-height: 550px;
  .qna-wrapper {
    max-width: 690px;
    margin-bottom: 10px;
    border: 1px solid black;
    border-radius: 5px;
    .question {
      padding: 6px 12px;
      background-color: $color-light-6;
      border-radius: 5px;

      .question-img {
        width: 15px;
        position: relative;
        top: -3px;
      }
      > span {
        margin-left: 10px;
      }
      .highlighted {
        background-color: gold;
        :deep(&) & {
          background-color: gold;
        }
      }
    }
    .qna-date {
      font-size: 0.8rem;
      color: #777;
    }
    .answer {
      margin-left: 35px;
      font-size: 1.05rem;
      padding: 5px 12px;
    }
  }
}

.pagenation-wrapper {
  margin: 0 auto;
  text-align: center;
  width: 400px;
  border: 1px solid black;
}
.search-wrapper {
  width: 400px;
  margin: 0 auto;
}
</style>
