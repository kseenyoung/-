<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">친구 목록</div>
    <div class="friend-list-wrapper">
      <div class="friend-list-total">
        <i class="bi bi-people-fill"></i> {{ totalFriend }}명
      </div>

      <div
        v-for="(friend, index) in listFriend"
        :key="index"
        class="friend-list-detail"
      >
        <img src="@/assets/img/기본프로필_갈색.jpg" />
        <div
          class="dropdown-toggle"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          @click="friendDetail(friend.userNickname)"
        >
          {{ friend.userNickname }}
        </div>
        <div class="friend-onoff friend-online">
          <!-- <div class="friend-onoff friend-offline"> -->
          <i class="bi bi-circle-fill"></i>접속중
        </div>
        <button class="btn common-btn"><i class="bi bi-send"></i></button>
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
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const totalFriend = ref('');
const listFriend = ref([]);

onMounted(() => {
  getFriends();
});

const getFriends = function () {
  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}friend/getFriendList`)
    .then((res) => {
      console.log(res);
      if (res.data.code === 1000) {
        //성공
        totalFriend.value = res.data.result.countFriend;
        listFriend.value = res.data.result.friends;
      }
    });
};

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
</script>

<style lang="scss" scoped>
.friend-list-wrapper {
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  max-height: 430px;
  margin: 0px 80px;
  padding: 0px 20px;
  .friend-list-total {
    font-size: 1.5rem;
  }
}
.friend-list-detail {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding: 10px;

  img {
    width: 40px;
    border-radius: 50%;
    margin-right: 10px;
  }

  div {
    margin-right: 10px; /* Adjust the margin as needed */
  }

  :nth-child(1) {
    flex-basis: 10%;
  }
  :nth-child(2) {
    flex-basis: 40%;
  }
  :nth-child(3) {
    flex-basis: 23%;
  }
  :nth-child(4) {
    flex-basis: auto;
  }

  .dropdown-menu {
    padding: 10px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
    > li:nth-child(1) {
      font-weight: 500;
    }
  }

  @mixin friend-status {
    border: 1px solid #ccc;
    background-color: #f4f4f4;
    border-radius: 30px;
    text-align: center;
  }
  .friend-onoff {
    @include friend-status;

    i {
      font-size: 0.7rem;
      position: relative;
      top: -3px;
      padding-right: 6px;
    }
  }

  .friend-online {
    i {
      color: rgb(0, 176, 0);
    }
  }

  .friend-offline {
    i {
      color: rgb(228, 0, 0);
    }
  }
}
</style>
