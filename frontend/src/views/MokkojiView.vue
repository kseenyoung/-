<template>
  <div class="mokkoji-view-wrapper">
    <img src="@/assets/img/mypage/clip_pixel.png" class="img-clip" />
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
          <div class="mok-subtitle">모꼬지원 목록 ({{ user.length }}명)</div>
          <div class="mok-list-wrapper">
            <div v-for="item in user" :key="item.userId">
              <div class="mok-list-detail">
                <img
                  class="profile"
                  v-if="item.userPicture"
                  :src="`${item.userPicture}`"
                />
                <img class="profile" v-else src="@/assets/img/default.jpg" />

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
            <div class="mok-right-info-content-name">
              {{ mokkoji.mokkojiName }}
            </div>
            <div>
              <div class="mok-right-info-content-box-index">모꼬지 소개</div>
              <div class="mok-right-info-content-box-border">
                {{ mokkoji.mokkojiStatus }}
              </div>
            </div>
            <div>
              <div class="mok-right-info-content-box-index">생성일</div>
              <div class="mok-right-info-content-box-border">
                {{ mokkoji.createdDate }}
              </div>
            </div>
            <div>
              <div class="mok-right-info-content-box-index">카테고리</div>
              <div class="mok-right-info-content-box-border">
                <template v-for="item in categories" :key="item.categoryId">
                  <div class="mok-right-info-content-cate">
                    # {{ item.categoryName }}
                  </div>
                </template>
              </div>
            </div>
          </div>
        </div>
        <div v-if="leaderCheck" class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 수정</div>
          <div class="mok-right-info-content">
            <div>
              <label for="mokkojiName" class="form-label">모꼬지 이름</label>
              <input
                type="text"
                v-model="editedMokkojiName"
                class="form-control"
              />
            </div>
            <div>
              <label for="mokkojiStatus" class="form-label">모꼬지 소개</label>
              <input
                type="text"
                v-model="editedMokkojiStatus"
                class="form-control"
              />
            </div>
            <div>
              <label for="category" class="form-label">카테고리</label>
              <select class="form-select" v-model="editedCategoryId">
                <option disabled value="" selected>- 카테고리 선택 -</option>
                <option
                  v-for="category in categoryStore.categoryList"
                  :key="category.categoryId"
                  :value="category.categoryId"
                >
                  {{ category.categoryName }}
                </option>
              </select>
              <div
                v-for="category in categories"
                :key="category.categoryId"
                :value="category.categoryId"
                class="edit-tag"
              >
                # {{ category.categoryName }}
              </div>
            </div>
            <div>
              <button class="btn common-btn" @click="updateMokkoji">
                수정하기
              </button>
            </div>
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
import { useCategoryStore } from '@/stores/category';

const userStore = useUserStore();
const categoryStore = useCategoryStore();
const router = useRouter();
const route = useRoute();

const mokkoji = ref([]);
const user = ref([]);
const categories = ref();
const leaderCheck = ref();
const myMokkoji = ref();
const userId = ref();

const editedMokkojiName = ref('');
const editedMokkojiStatus = ref('');
const editedCategoryId = ref('');

onMounted(async () => {
  await getMokkojiDetail();
  editedMokkojiName.value = mokkoji.value.mokkojiName;
  editedMokkojiStatus.value = mokkoji.value.mokkojiStatus;
});

//모꼬지 상세정보 가져오기
const getMokkojiDetail = async function () {
  const id = route.params.id; //id 값을 route.params에서 가져옴
  return await axios
    .get(`${import.meta.env.VITE_API_BASE_URL}mokkoji/detail/${id}`)
    .then((res) => {
      if (res.data.code === 1000) {
        mokkoji.value = {
          ...res.data.result.mokkoji,
          createdDate: formatDate(res.data.result.mokkoji.createdDate),
        };
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

//배열로 들어온 생성일 yyyy.mm.dd로 반환
const formatDate = function (dateArray) {
  const year = dateArray[0];
  const month = dateArray[1].toString().padStart(2, '0');
  const day = dateArray[2].toString().padStart(2, '0');
  return `${year}.${month}.${day}`;
};

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
        //유저 정보 업데이트
        userStore.getLoginUserInfo();
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
      console.log(res);
      if (res.data.code === 1000) {
        //성공
        alert('모꼬지가 삭제되었습니다.');
        //유저 정보 업데이트
        userStore.getLoginUserInfo();
        //친구/모꼬지 신청 페이지로 이동
        router.push({
          name: 'apply',
        });
      } else {
        alert(res.data.message);
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

//모꼬지 수정
const updateMokkoji = function () {
  //수정
};
</script>

<style lang="scss" scoped>
$my-shadow:
  rgba(0, 0, 0, 0.4) 0px 2px 4px,
  rgba(0, 0, 0, 0.3) 0px 7px 13px -3px,
  rgba(0, 0, 0, 0.2) 0px -3px 0px inset;

.mokkoji-view-wrapper {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  min-height: 100vh;
  padding: 0px 300px;
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
    // background-color: whitesmoke;
    background-color: $color-light-6;
    .mok-content-left {
      flex-basis: 40%;
      padding: 30px;
      > div {
        background-color: $color-light-2;
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
          .mok-list-detail {
            display: flex;
            align-items: center;
            padding: 10px;
            img {
              width: 30px;
              height: 30px;
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
      margin: 30px;
      .mok-right-info {
        margin-bottom: 50px;
        border-bottom: 1px solid black;
        min-height: 300px;
        box-shadow: $my-shadow;
        background-color: white;
        .mok-right-info-subtitle {
          background-color: $color-light-2;
          text-align: center;
          font-size: 1.5rem;
          font-weight: 600;
          padding: 5px;
          box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
        }
        .mok-right-info-content {
          text-align: center;
          font-size: 1.3rem;
          padding: 20px 20px 20px;
          max-width: 470px;
          .mok-right-info-content-box-index {
            display: inline-block;
            position: relative;
            z-index: 1;
            background-color: white;
            font-weight: bold;
            padding: 0px 6px;
          }
          .mok-right-info-content-box-border {
            padding: 20px 10px;
            position: relative;
            top: -16px;
            border: 1px solid black;
            border-radius: 3px;
          }
          .mok-right-info-content-name {
            font-size: 1.7rem;
            font-weight: bold;
            margin-bottom: 15px;
          }
          .mok-right-info-content-cate {
            display: inline-block;
            padding: 3px 8px;
            margin: 7px 5px;
            background-color: $color-dark-6;
            color: white;
            border-radius: 5px;
          }
        }
      }
    }
  }
}
.edit-tag {
  display: inline-block;
  padding: 3px 8px;
  margin: 7px 5px;
  background-color: $color-light-2;
  border-radius: 5px;
}
</style>
