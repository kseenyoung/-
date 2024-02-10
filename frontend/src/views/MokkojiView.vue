<template>
  <div class="container">
    <img src="@/assets/img/mypage/clip.png" class="img-clip" />
    <div class="mok-content-wrapper">
      <div class="mok-content-left">
        <div class="mok-left-action">
          <div class="mok-subtitle">{{ mokkoji.mokkojiName }}</div>
          <button
            v-if="myMokkoji == 0 && userId != ''"
            class="btn common-btn"
            @click="requestMokkoji"
          >
            모꼬지 신청
          </button>
          <button
            v-if="myMokkoji == mokkoji.mokkojiId && !leaderCheck"
            class="btn common-btn"
            @click="leaveMokkoji"
          >
            모꼬지 탈퇴
          </button>
          <button
            v-if="leaderCheck"
            class="btn common-btn"
            @click="deleteMokkoji"
          >
            모꼬지 삭제
          </button>
        </div>
        <div class="mok-left-list">
          <div class="mok-subtitle">모꼬지원 목록</div>
          <div class="mok-list-wrapper">
            <div v-for="item in user" :key="item.userId">
              <div class="mok-list-detail">
                <img
                  v-if="item.userPicture == '' || item.userPicture == null"
                  src="@/assets/img/기본프로필_갈색.jpg"
                />
                <div>
                  {{ item.userNickname
                  }}<i
                    v-if="mokkoji.leaderId == item.userId"
                    class="bi bi-mortarboard-fill leader-icon"
                  ></i>
                </div>
                <button
                  v-if="
                    leaderCheck &&
                    item.userId !== userStore.loginUserInfo.userId
                  "
                  class="btn btn-sm btn-danger kickbtn"
                  @click="kickMember(item.userId)"
                >
                  강퇴
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="mok-content-right">
        <div class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 정보</div>
          <div class="mok-right-info-content">
            <div>{{ mokkoji.mokkojiName }}</div>
            <div>{{ mokkoji.leaderId }}</div>
            <div>{{ mokkoji.mokkojiStatus }}</div>
            <div v-for="item in categories" :key="item.categoryId">
              <div>#{{ item.categoryName }}</div>
            </div>
          </div>
        </div>
        <div v-if="leaderCheck" class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 수정</div>
          <div class="mok-right-info-content">
            <div>길드장만 보임</div>
            <div>길드 수정 폼</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const router = useRouter();

const mokkoji = ref([]);
const user = ref([]);
const categories = ref();
const leaderCheck = ref();
const myMokkoji = ref();
const userId = ref();

// Vue Router 인스턴스 생성
const route = useRoute(); // route 객체 생성
const getMokkojiDetail = async function () {
  const id = route.params.id; // id 값을 route.params에서 가져옵니다.
  return await axios
    .get(`${import.meta.env.VITE_API_BASE_URL}mokkoji/detail/${id}`)
    .then((res) => {
      if (res.data.code === 1000) {
        mokkoji.value = res.data.result.mokkoji;
        user.value = res.data.result.user;
        categories.value = res.data.result.categories;
        leaderCheck.value = res.data.result.leader;
        myMokkoji.value = res.data.result.myMokkojiId;
        userId.value = res.data.result.userId;
      } else {
        alert(res.data.message);
      }
    });
};

onMounted(async () => {
  getMokkojiDetail();
  // const response = await getMokkojiDetail(id);

  // if (response.data.code === 1000) {
  //   console.log(response.data);
  //   mokkoji.value = response.data.result.mokkoji;
  //   user.value = response.data.result.user;
  //   categories.value = response.data.result.categories;
  //   leaderCheck.value = response.data.result.leader;
  //   myMokkoji.value = response.data.result.myMokkojiId;
  //   userId.value = response.data.result.userId;
  // } else {
  //   alert(response.data.message);
  // }
});

//모꼬지 가입 신청
const requestMokkoji = function () {
  const body = {
    sign: 'requestMokkoji',
    mokkojiId: mokkoji.value.mokkojiId,
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body)
    .then((res) => {
      if (res.data.code === 1000) {
        //성공
        alert('모꼬지 가입 신청을 보냈습니다.');
      } else if (res.data.code === 2101) {
        //이미 요청을 보낸 상태
        alert(res.data.message);
      } else {
        alert('실패');
      }
    });
};

//모꼬지 탈퇴
const leaveMokkoji = function () {
  const body = {
    sign: 'leaveMokkoji',
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body)
    .then((res) => {
      if (res.data.code === 1000) {
        //성공
        alert('탈퇴되었습니다.');

        //친구/모꼬지 신청 페이지로 이동
        router.push({
          name: 'apply',
        });
      } else {
        alert('실패');
      }
    });
};

//모꼬지 삭제
const deleteMokkoji = function () {
  const body = {
    sign: 'deleteMokkoji',
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body)
    .then((res) => {
      if (res.data.code === 1000) {
        //성공
        alert('모꼬지가 삭제되었습니다.');

        //친구/모꼬지 신청 페이지로 이동
        router.push({
          name: 'apply',
        });
      } else {
        alert('실패');
      }
    });
};

//모꼬지 강퇴
const kickMember = function (memberId) {
  if (window.confirm('정말 강퇴하시겠습니까?')) {
    const body = {
      sign: 'kickMember',
      member: memberId,
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //성공
          alert('강퇴되었습니다.');
          getMokkojiDetail();
        } else {
          alert('실패');
        }
      });
  }
};
</script>

<style lang="scss" scoped>
$my-shadow:
  rgba(0, 0, 0, 0.4) 0px 2px 4px,
  rgba(0, 0, 0, 0.3) 0px 7px 13px -3px,
  rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
.container {
  margin: 80px auto;
  padding: 0px 200px;
  min-height: 600px;
  // background-color: aliceblue;
  // font-size: 1.0rem;
  display: flex;
  flex-direction: column;
  .img-clip {
    width: 100px;
    position: relative;
    top: 35px;
    left: 100px;
  }
  .mok-title {
    font-size: 1.8rem;
    font-weight: 800;
    text-align: center;
  }
  .mok-content-wrapper {
    flex-grow: 1;
    display: flex;
    box-shadow: $my-shadow;
    padding: 20px;
    background-color: whitesmoke;
    .mok-content-left {
      flex-basis: 40%;
      padding: 30px;
      > div {
        background-color: cornsilk;
        box-shadow: $my-shadow;
      }
      .mok-subtitle {
        font-size: 1.5rem;
        font-weight: 600;
        text-align: center;
        padding: 5px;
        margin: 0px 25px;
        border-bottom: 1px solid black;
      }
      .mok-left-action {
        min-height: 150px;
        margin-bottom: 40px;
        > button {
          display: block;
          margin: 0 auto;
          margin-top: 25px;
        }
      }
      .mok-left-list {
        .mok-list-wrapper {
          overflow-y: scroll;
          min-height: 300px;
          max-height: 300px;
          // background-color: antiquewhite;
          .mok-list-detail {
            display: flex;
            align-items: center;
            padding: 10px;
            img {
              width: 30px;
              border-radius: 50%;
              margin-right: 10px;
              margin-left: 60px;
            }
            .kickbtn {
              margin-left: 10px;
            }
            .leader-icon {
              margin-left: 5px;
            }
          }
        }
      }
    }
    .mok-content-right {
      flex-grow: 1;
      // border: 1px solid black;
      margin: 30px;
      .mok-right-info {
        // border: 1px solid black;
        margin-bottom: 50px;
        border-bottom: 1px solid black;
        min-height: 300px;
        box-shadow: $my-shadow;
        background-color: white;
        .mok-right-info-subtitle {
          background-color: lightblue;
          text-align: center;
          font-size: 1.5rem;
          font-weight: 600;
          padding: 5px;
          box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
        }
        .mok-right-info-content {
          font-size: 1.3rem;
          padding: 20px 20px 0px;
        }
      }
    }
  }
}
</style>
