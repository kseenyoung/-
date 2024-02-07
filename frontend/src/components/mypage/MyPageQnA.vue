<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 질문</div>

    <div class="qna-content-wrapper">
      <div class="qna-wrapper">
        <div class="question">
          <i class="bi bi-question-circle-fill"></i> [수학] 수학공부 잘하는법
          알려주세요<span class="qna-date">2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변1<span class="qna-date">2024-01-01</span>
        </div>
      </div>

      <div class="qna-wrapper">
        <div class="question">
          <i class="bi bi-question-circle-fill"></i> [수학] 수학공부 잘하는법
          알려주세요!!!!!!!<span class="qna-date">2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변입니다.<span
            class="qna-date"
            >2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변3<span class="qna-date">2024-01-01</span>
        </div>
      </div>
      <div class="qna-wrapper">
        <div class="question">
          <i class="bi bi-question-circle-fill"></i> [수학] 수학공부 잘하는법
          알려주세요수학공부 잘하는법 알려주세요수학공부 잘하는법
          알려주세요수학공부 잘하는법 알려주세요<span class="qna-date">2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변1<span class="qna-date">2024-01-01</span>
        </div>
      </div>
      <div class="qna-wrapper">
        <div class="question">
          <i class="bi bi-question-circle-fill"></i> [수학] 수학공부 잘하는법
          알려주세요<span class="qna-date">2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변1<span class="qna-date">2024-01-01</span>
        </div>
      </div>
      <div class="qna-wrapper">
        <div class="question">
          <i class="bi bi-question-circle-fill"></i> [수학] 수학공부 잘하는법
          알려주세요<span class="qna-date">2024-01-01</span>
        </div>
        <div class="answer">
          <i class="bi bi-arrow-return-right"></i> 답변1<span class="qna-date">2024-01-01</span>
        </div>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagenation-wrapper">
      <div>페이지네이션</div>
    </div>

    <!-- 검색 -->
    <div class="input-group mb-3 search-wrapper">
      <input
        type="text"
        class="form-control"
        placeholder="질문 검색"
        aria-describedby="button-addon2"
      />
      <button class="btn common-btn" type="button" id="button-addon2">
        검색
      </button>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.qna-content-wrapper {
  min-height: 400px;
  .qna-wrapper {
    max-width: 690px;
    margin-bottom: 10px;
    .question {
      padding: 6px 10px;
      background-color: aliceblue;
    }
    .qna-date {
      font-size: 0.8rem;
      color: #777;
      margin-left: 10px;
    }
    .answer {
      margin-left: 35px;
      font-size: 1.05rem;
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
