<template>
  <div class="parent">
    <div class="part one">
      <!-- <div class="title">多角</div>
      <div id="square1">JAVA</div>
      <div id="square2">python</div>
      <div id="square3">C++</div>
      <div id="square4">민법</div>
      <div id="square5">수능수학</div>
      <div id="square6">수능국어</div>
      <div id="square7">재수</div>
      <div id="square8">이모티콘제작</div>
      <div id="square9">입시미술</div>
      <div id="square10">도전만화</div>
      <div id="square11">수능영어</div>
      <div id="square12">OPIc</div>
      <div id="bsquare1">flutter</div>
      <div id="bsquare5">민사소송법</div>
      <div id="bsquare7">미분적분학</div>
      <div id="bsquare10">R</div> -->

      <div class="title">多角</div>
      <div id="square1">
        <SimpleDagak />
      </div>
      <div id="square2">
        <SimpleDagak />
      </div>
      <div id="square3">
        <SimpleDagak />
      </div>
      <div id="square4">
        <SimpleDagak />
      </div>
      <div id="square5">
        <SimpleDagak />
      </div>
      <!-- <div id="square6"><SimpleDagak/></div> -->
      <div id="square7">
        <SimpleDagak />
      </div>
      <div id="square8">
        <SimpleDagak />
      </div>
      <div id="square9">
        <SimpleDagak />
      </div>
      <div id="square10">
        <SimpleDagak />
      </div>
      <div id="square11">
        <SimpleDagak />
      </div>
      <div id="square12">
        <SimpleDagak />
      </div>
      <div id="bsquare1">
        <SimpleDagak />
      </div>
      <div id="bsquare5">
        <SimpleDagak />
      </div>
      <div id="bsquare7">
        <SimpleDagak />
      </div>
      <div id="bsquare10">
        <SimpleDagak />
      </div>

      <button class="startbutton" @click="navigateToStudyRoom">공부시작</button>
    </div>
    <div class="part two">
      <MyRanking />
      <MokkojiRanking />
    </div>
    <div class="part three">3</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import MyRanking from '@/components/home/MyRanking.vue';
import MokkojiRanking from '@/components/home/MokkojiRanking.vue';
import { useUserStore } from '@/stores/user';
import { useRankStore } from '@/stores/rank';
import SimpleDagak from '@/components/dagak/SimpleDagak.vue';
const store = useUserStore();
const rankstore = useRankStore();
const router = useRouter();
const API_URL = 'https://localhost:8080';
const mokkojiRank = ref([]);

const navigateToStudyRoom = () => {
  router.push('/studyroom');
};

onMounted(async () => {
  console.log('마운트 시작');
  store.login();
  await rankstore.getMokkojiRank();
  console.log('mokkojiRank.value: ', rankstore.mokkojiRank);
});
</script>

<style lang="scss" scoped>
.title {
  padding-top: 5%;
  font-size: 22rem;
  font-weight: bold;
  position: relative;
  z-index: 20;
  color: black;
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
  // background-image: url('https://img.freepik.com/free-vector/abstract-3d-perspective-indoor-wireframe-vector-design_1017-39916.jpg?w=1060&t=st=1705037743~exp=1705038343~hmac=abc423a6b1c37e3cfec62d1e9af305e6f3989f5421e7c351a80a494cad1a4629');
  // background-image: url('https://img.freepik.com/free-vector/detailed-wireframe-terrain-landscape-in-black-and-white_1048-11902.jpg?w=1380&t=st=1705038502~exp=1705039102~hmac=ab8142a255e5fb577d99e6111f45612933a987327ade60ae55e02646d932400a');
  // background-image: url('https://previews.123rf.com/images/roxiller/roxiller1608/roxiller160800030/61554592-%ED%95%98%ED%94%84-%ED%86%A4-%ED%8C%A8%ED%84%B4%EC%9E%85%EB%8B%88%EB%8B%A4-%EB%B2%A1%ED%84%B0-%ED%95%98%ED%94%84-%ED%86%A4-%EC%A7%88%EA%B0%90%EC%9E%85%EB%8B%88%EB%8B%A4-%EB%B2%A1%ED%84%B0-%ED%95%98%ED%94%84-%ED%86%A4-%EC%A7%88%EA%B0%90%EC%9E%85%EB%8B%88%EB%8B%A4-%EC%B6%94%EC%83%81-%ED%8C%A8%ED%84%B4%EC%9E%85%EB%8B%88%EB%8B%A4-%ED%95%98%ED%94%84-%ED%86%A4-%ED%8C%A8%ED%84%B4%EC%9E%85%EB%8B%88%EB%8B%A4-%EC%A0%90%EC%9D%B4%EC%9E%88%EB%8A%94-%ED%85%8D%EC%8A%A4%EC%B2%98-%EA%B2%80%EC%9D%80-%ED%8C%A8%ED%84%B4-%EB%B2%A1%ED%84%B0.jpg');
  // background-image: url('@/assets/img/home/square.png');
  // z-index: -1;

  // filter: invert(100%);
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
  justify-content: space-evenly;
  font-family: 'NanumSquareNeo';
  font-weight: 700;
  // background-image: url('@/assets/img/home/navymountain.jpg');
  // background-image: url('@/assets/img/home/square.png');
  background-image: url('@/assets/img/home/sky.jpg');
  background-size: cover;
  background-position: center;
}

.three {
  // background-color: rgb(195, 195, 195);
  background-image: url('@/assets/img/home/sky.jpg');
  background-size: cover;
  background-position: center;
}
</style>
