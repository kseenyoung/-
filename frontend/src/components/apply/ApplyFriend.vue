<template>
  <div class="apply-friend-wrapper">
    <div class="apply-title">친구 신청</div>
    <button @click="getFriendList">테스트</button>
    <!-- 검색 -->
    <div class="apply-search input-group mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="친구 검색"
        aria-describedby="button-addon2"
      />
      <button class="btn common-btn" type="button" id="button-addon2">
        검색
      </button>
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
            <td><img src="@/assets/img/기본프로필_갈색.jpg" /></td>
            <td class="dropdown">
              <div
                class="dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                @click="friendDetail(userNickname)"
              >
                ssafy
              </div>
              <ul class="dropdown-menu">
                <li>{{ friendDetailInfo.userNickname }}</li>
                <li>모꼬지: {{ friendDetailInfo.mokkoijiName }}</li>
                <li v-if="friendDetailInfo.userRank != null">
                  랭크: {{ friendDetailInfo.userRank }} 위
                </li>
                <li v-else>랭크: -</li>
                <li v-if="friendDetailInfo.userTotalStudyTime != null">
                  공부시간: {{ friendDetailInfo.userTotalStudyTime }} 분
                </li>
                <li v-else>공부시간: -</li>
                <li>"{{ friendDetailInfo.userStatusMessage }}"</li>
              </ul>
            </td>
            <td><button class="btn common-btn">신청</button></td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="apply-pagenation">페이지네이션</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

const userStore = useUserStore();

const friendList = ref([]);

const getFriendList = function () {
  const body = {
    sign: 'viewAllUser',
    userId: userStore.loginUserInfo.userId,
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body).then((res) => {
    console.log(res);
    friendList.value = res.data.result;
  });
};

//유저 정보 디테일
const friendDetailInfo = ref({});
const friendDetail = function (nickname) {
  const body = {
    sign: 'viewUserInformation',
    userNickname: nickname,
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code === 1000) {
        //성공
        friendDetailInfo.value = json.result;
      } else {
        alert('닉네임이 비어있습니다.');
      }
    });
};
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
