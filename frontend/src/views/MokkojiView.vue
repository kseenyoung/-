<template>
  <div class="container">
    <img src="@/assets/img/mypage/clip.png" class="img-clip">
    <div class="mok-content-wrapper">
      <div class="mok-content-left">
        <div class="mok-left-action">
          <div class="mok-subtitle">{{mokkoji.mokkojiName}}</div>
          <button v-if="myMokkoji ==0 && userId != '' " class="btn common-btn">모꼬지 신청</button>
          <button v-if="myMokkoji == mokkoji.mokkojiId" class="btn common-btn">모꼬지 탈퇴</button>
          <button v-if="leaderCheck" class="btn common-btn">모꼬지 삭제</button>
        </div>
        <div class="mok-left-list">
          <div class="mok-subtitle">모꼬지원 목록</div>
          <div class="mok-list-wrapper">
          <div v-for="item in user" :key="item.userId">
            <div class="mok-list-detail">
              <img v-if="item.userPicture =='' || item.userPicture ==null" src="@/assets/img/기본프로필_갈색.jpg">
              <div>{{item.userNickname}}</div>
              <button v-if="leaderCheck" class="btn btn-sm btn-danger">강퇴</button>
            </div>
          </div>
          </div>
        </div>
      </div>

      <div class="mok-content-right">
        <div class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 정보</div>
          <div class="mok-right-info-content">
            <div>{{mokkoji.mokkojiName}}</div>
            <div>{{mokkoji.leaderId}}</div>
            <div>{{mokkoji.mokkojiStatus}}</div>
            <div v-for="item in categories" :key="item.categoryId">
              <div>{{item.categoryName}}</div>
            </div>
          </div>
        </div>
        <div v-if="leaderCheck"  class="mok-right-info">
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
import {useRoute, useRouter } from 'vue-router'
import {ref, onMounted} from "vue";

const mokkoji = ref([]);
const user = ref([]);
const categories = ref();
const leaderCheck = ref();
const myMokkoji = ref();
const userId = ref();

// Vue Router 인스턴스 생성
const route = useRoute(); // route 객체 생성
const id = route.params.id;
const getMokkojiDetail = async function () {
  const id = route.params.id; // id 값을 route.params에서 가져옵니다.
  return await axios.get(`${import.meta.env.VITE_API_BASE_URL}mokkoji/detail/${id}`);
};
onMounted(async () => {
    const response = await getMokkojiDetail(id);
    
    if(response.data.code === 1000){
      console.log(response.data);
      mokkoji.value = response.data.result.mokkoji;
      user.value = response.data.result.user;
      categories.value = response.data.result.categories;
      leaderCheck.value = response.data.result.leaderCheck;
      myMokkoji.value = response.data.result.myMokkoji;
      userId.value = response.data.result.userId;
    }
    else{
      alert(response.data.message);
    }
})



</script>

<style lang="scss" scoped>
$my-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
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
            justify-content: space-around;
            padding: 10px;
        
            img {
              width: 30px;
              border-radius: 50%;
              margin-right: 10px;
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