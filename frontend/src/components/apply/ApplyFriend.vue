<template>
  <div class="apply-friend-wrapper">
    <div class="apply-title">친구 신청</div>
    <!-- 검색 -->
    <div class="apply-search input-group mb-3">
      <input type="text" class="form-control" placeholder="친구 검색" aria-describedby="button-addon2">
      <button class="btn common-btn" type="button" id="button-addon2">검색</button>
    </div>

    <!-- 목록 -->
    <div class="apply-content">
      <table class="table">
        <thead class="my-thead">
          <tr>
            <th>프로필</th>
            <th>닉네임</th>
            <th>친구신청</th>
          </tr>
        </thead>
        <tbody>
          <tr class="my-hover">
            <td><img src="@/assets/img/기본프로필_갈색.jpg"></td>
            <td class="dropdown">
              <div class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                ssafy
              </div>
              <ul class="dropdown-menu">
                <li>ssafy</li>
                <li>길드명</li>
                <li>"오늘도 파이팅!!"</li>
              </ul>
            </td>
            <td><button class="btn common-btn">신청</button></td>
          </tr>
          <tr class="my-hover">
            <td><img src="@/assets/img/기본프로필_갈색.jpg"></td>
            <td>ssafy2</td>
            <td><button class="btn common-btn">신청</button></td>
          </tr>
          <tr class="my-hover">
            <td><img src="@/assets/img/기본프로필_갈색.jpg"></td>
            <td>ssafy3</td>
            <td><button class="btn common-btn">신청</button></td>
          </tr>
          <tr class="my-hover">
            <td><img src="@/assets/img/기본프로필_갈색.jpg"></td>
            <td>ssafy4</td>
            <td><button class="btn common-btn disabled">신청</button></td>
          </tr>
          <tr class="my-hover">
            <td><img src="@/assets/img/기본프로필_갈색.jpg"></td>
            <td>ssafy5</td>
            <td><button class="btn common-btn disabled">신청</button></td>
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
.apply-friend-wrapper {
  padding: 30px 30px;
  // .my-thead {
  //   tr {
  //     > th:nth-child(3) {
  //       width: 70%;
  //     }
  //   }
  // }
}
</style>