<template>
  <div class="apply-friend-wrapper">
    <div class="apply-title">친구 신청</div>
    <button @click="test">테스트</button>
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
          <tr
            v-for="friend in paginatedFriendList"
            :key="friend.userId"
            class="my-hover"
          >
            <!-- <td><img src="@/assets/img/${friend.userPicture}.png" /></td> -->
            <td><img src="@/assets/img/기본프로필_갈색.jpg" /></td>
            <td class="dropdown">
              <div
                class="dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                @click="friendDetail(friend.userNickname)"
              >
                {{ friend.userNickname }}
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
            <td>
              <button class="btn common-btn" :disabled="friend.friend">
                신청
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="apply-pagenation">
      <div class="apply-pagenation">
        <button @click="prevPage" :disabled="currentPage === 1">이전</button>
        <span>{{ currentPage }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">
          다음
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

const userStore = useUserStore();

const friendList = ref([]);
const itemsPerPage = ref(10);
let currentPage = ref(1);

const test = function () {
  console.log(paginatedFriendList.value);
};

const totalPages = computed(() =>
  Math.ceil(friendList.value.length / itemsPerPage.value),
);

const paginatedFriendList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return friendList.value.slice(start, end);
});

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

onMounted(() => {
  getFriendList();
});

const getFriendList = function () {
  const body = {
    sign: 'viewAllUser',
    userId: userStore.loginUserInfo.userId,
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body).then((res) => {
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
