<template>
  <div class="apply-mokkoji-wrapper">
    <div class="apply-title">
      모꼬지 신청
      <button class="btn common-btn">새로 만들기</button>
    </div>
    <!-- 검색 -->
    <div class="apply-search input-group mb-3">
      <input type="text" class="form-control" placeholder="모꼬지 검색" aria-describedby="button-addon2">
      <select class="form-select">
        <option selected value="1">길드명</option>
        <option value="2">길드장</option>
        <option value="3">태그</option>
      </select>
      <button class="btn common-btn" type="button" id="button-addon2">검색</button>
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
          <tr class="my-hover">
            <td><RouterLink
              :to="`/mokkoji/1`"
              >정처기스터디모임</RouterLink></td>
            <td>ssafy</td>
            <td>#정보처리기사</td>
          </tr>
          <tr class="my-hover">
            <td>정처기스터디</td>
            <td>ssafy</td>
            <td>#정보처리기사</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="apply-pagenation">페이지네이션</div>
  </div>
</template>

<script setup>

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