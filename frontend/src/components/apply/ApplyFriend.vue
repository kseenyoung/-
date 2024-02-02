<template>
  <div class="apply-friend-wrapper">
    <div class="apply-title">친구 신청</div>
    <!-- 검색 -->
    <div class="apply-search input-group mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="친구 검색"
        aria-describedby="button-addon2"
        v-model="searchText"
        @keyup.enter="searchFriend"
      />
      <button
        class="btn common-btn"
        type="button"
        id="button-addon2"
        @click="searchFriend"
      >
        검색
      </button>
    </div>

    <!-- 목록 -->
    <div class="apply-content">
      <table class="table">
        <thead class="my-thead">
          <tr>
            <th>프로필</th>
            <th class="apply-friend-thead-nick">닉네임</th>
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
              <button
                class="btn btn-sm common-btn my-btn"
                :disabled="friend.friend"
                @click="requestFriend(friend.userId)"
              >
                신청
              </button>
            </td>
          </tr>
        </tbody>
      </table>
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
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

const userStore = useUserStore();

const friendList = ref([]);
const searchText = ref('');
const itemsPerPage = ref(10);
let currentPage = ref(1);

onMounted(() => {
  getFriendList();
});

//닉네임 검색
const searchFriend = function () {
  if (searchText.value === '') {
    getFriendList();
  } else {
    const filteredFriends = friendList.value.filter((friend) =>
      friend.userNickname.includes(searchText.value),
    );
    friendList.value = filteredFriends;
  }
};

// 검색어가 변경될 때 전체 목록으로 돌아가도록 수정
watch(searchText, () => {
  if (searchText.value === '') {
    getFriendList();
  }
});

//총 페이지 설정
const totalPages = computed(() =>
  Math.ceil(friendList.value.length / itemsPerPage.value),
);

//페이지별 목록
const paginatedFriendList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return friendList.value.slice(start, end);
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

//전체 유저 목록
const getFriendList = function () {
  const body = {
    sign: 'getAllUserList',
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
    sign: 'getUserInformation',
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

//친구 신청 보내기
const requestFriend = function (userId) {
  const body = {
    sign: 'requestFriend',
    userId: userId,
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}friend`, body).then((res) => {
    console.log(res);
    if (res.data.code === 1000) {
      //성공
      alert('친구 신청을 보냈습니다.');
    } else {
      alert('실패했습니다.');
    }
  });
};
</script>

<style lang="scss" scoped>
.apply-friend-wrapper {
  padding: 30px 30px;
  .apply-friend-thead-nick {
    width: 70px;
  }
  .my-btn {
    padding: 5px 15px;
  }
}
</style>
