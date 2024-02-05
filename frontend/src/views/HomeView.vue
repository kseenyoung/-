<template>
  <div class="background">
    <div class="parent">
      <div class="part one">
        <div class="title m-0" style="color:#000000; font-size:150px;">다각</div>
        <h2><div style="color:white;font-weight:bold" class="">다같이 랜덤 스터디</div></h2>
        <div
          style="color: white;"
          v-if="userStore.loginUserInfo.userId"
          @click="navigateToStudyRoom"
        >
          <h3 style="display:inline-block;"> <VueWriter :array="arr" style="display:inline-block;" class="is-typed cursor" :caret="underscore" /></h3>
        <p style="display: inline-block;" class="font-weight-bold"><h3> 공부하기</h3></p>
    </div>
      </div>
      <div class="part two">
        <MyRanking />
        <MokkojiRanking />
      </div>
      <div class="part three">3</div>
    </div>
  </div>
</template>

<script setup>
import { onMounted,ref } from 'vue'
import { useRouter } from 'vue-router'
import MyRanking from '@/components/home/MyRanking.vue'
import MokkojiRanking from '@/components/home/MokkojiRanking.vue'
import { useUserStore } from '@/stores/user'
import { useCategoryStore } from '@/stores/category'
import { useAlarmStore } from '@/stores/alarm'
import SimpleDagak from '@/components/dagak/SimpleDagak.vue'

const arr = ref([
  " \"정보처리기사\"",
  "\"SQLD\"",
  "\"생활과윤리\"",
  "\"JLPT\"",
]);


const userStore = useUserStore()
const categoryStore = useCategoryStore()
const alarmStore = useAlarmStore()
const router = useRouter()

const navigateToStudyRoom = () => {
  router.push('/studyroom')
}

onMounted(async () => {
  // store.login();
  alarmStore.getUnReadAlarmList()
  categoryStore.getCategoryList()
})
</script>

<style lang="scss" scoped>
.title {
  font-size: 15rem;
  font-weight: bold;
  position: relative;
  z-index: 30;
  color: black;
  padding-top: 10%;
  padding-bottom: 5%;
}

.title::before {
  content: attr(data-text);
  /* 글자 내용을 속성값으로 가져와서 적용 */
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
  color: white;
  /* 흰색으로 설정 */
  overflow: hidden;
  clip: rect(0, auto, 0, auto);
  /* 추가된 텍스트를 숨기기 위해 사용 */
}
.startbutton {
  // 기존 스타일 유지
  background-color: #639b9d;
  /* 적절한 배경색으로 변경 */
  bottom: 50px;
  z-index: 100;
  border: none;
  color: white;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  cursor: pointer;
  border-radius: 8px;
  /* 둥근 모서리 추가 */
  transition: background-color 0.3s;
  /* 부드러운 전환 효과 */

  &:hover {
    background-color: #3e6271;
    /* 마우스 호버 시 색상 변경 */
  }

  span {
    position: relative;
    z-index: 1;
  }

  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    // background: linear-gradient(45deg, #FF8C00, #FFD700); /* 그라데이션 효과 추가 */
    background: linear-gradient(45deg, #00ff7f, #48d1cc);
    /* 그라데이션 효과 추가 */
    z-index: -1;
    border-radius: 8px;
    /* 둥근 모서리에 그라데이션도 적용 */
    opacity: 0;
    transition: opacity 0.3s;
  }

  &:hover:before {
    opacity: 1;
  }
}

.parent {
  overflow: scroll;
  height: 100vh;
  scroll-snap-type: y mandatory;
  text-align: center;
  margin: 0 auto;
}

// 스크롤 숨기기
.parent::-webkit-scrollbar {
  display: none;
}

.part {
  height: 100vh;
  scroll-snap-align: start;
  position: relative;
}

.one {
  background-size: cover;
  background-position: center;
}

@keyframes move1h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 25vh;
    left: 65vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

@keyframes move1h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    left: 90vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move2h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 35vh;
    left: 80vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

@keyframes move3h {
  from {
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    left: 90vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move4h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 70vh;
    left: 80vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move5h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 65vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move5h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 85vw;
    width: 225px;
    height: 150px;
    // background-color:rgb(207, 207, 207);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move6h {
  from {
    top: 50vh;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 93vh;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move7h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 30vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move7h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 10vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
  }
}

@keyframes move8h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 70vh;
    left: 20vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move9h {
  from {
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    left: 10vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move10h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 35vh;
    left: 20vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move10h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    left: 10vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move11h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 25vh;
    left: 35vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move12h {
  from {
    top: 50vh;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

div[id^='square'] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 150px;
  height: 100px;
  // background-color: rgb(190, 190, 190);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  // box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  // border: 20px solid #000;
  // border-radius: 4px 4px 4px 4px;
  color: black;
}

div[id^='bsquare'] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 225px;
  height: 150px;
  // background-color: rgb(255, 61, 61);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  // box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  // border: 20px solid #373737;
  // border-radius: 4px 4px 4px 4px;
}

#square1 {
  transition-timing-function: ease-in;
  animation: move1h 2s forwards;
}

#bsquare1 {
  transition-timing-function: ease-in;
  animation: move1h_1 2s forwards;
}

#square2 {
  transition-timing-function: ease-in;
  animation: move2h 2s forwards;
}

#square3 {
  transition-timing-function: ease-in;
  animation: move3h 2s forwards;
}

#square4 {
  transition-timing-function: ease-in;
  animation: move4h 2s forwards;
}

#square5 {
  transition-timing-function: ease-in;
  animation: move5h 2s forwards;
}

#bsquare5 {
  transition-timing-function: ease-in;
  animation: move5h_1 2s forwards;
}

#square6 {
  transition-timing-function: ease-in;
  animation: move6h 2s forwards;
}

#square7 {
  transition-timing-function: ease-in;
  animation: move7h 2s forwards;
}

#bsquare7 {
  transition-timing-function: ease-in;
  animation: move7h_1 2s forwards;
}

#square8 {
  transition-timing-function: ease-in;
  animation: move8h 2s forwards;
}

#square9 {
  transition-timing-function: ease-in;
  animation: move9h 2s forwards;
}

#square10 {
  transition-timing-function: ease-in;
  animation: move10h 2s forwards;
}

#bsquare10 {
  transition-timing-function: ease-in;
  animation: move10h_1 2s forwards;
}

#square11 {
  transition-timing-function: ease-in;
  animation: move11h 2s forwards;
}

#square12 {
  transition-timing-function: ease-in;
  animation: move12h 2s forwards;
}

.two {
  // background-color: gray;
  display: flex;
  align-items: center;
  position: relative;
  font-family: 'NanumSquareNeo';
}

.three {
  // background-color: rgb(195, 195, 195);
  background-size: cover;
  background-position: center;
}
.background {
  position: relative;
  width: 100%;
  height: 100%;
  background: url('@/assets/background.gif') no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

.background::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.2); /* 흰색에 대한 배경 투명도 조절 가능 */
  z-index: 1; /* 이미지 위에 배치하려면 z-index 값을 조절하세요 */
}
</style>
