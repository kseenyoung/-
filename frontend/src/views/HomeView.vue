<template>
  <div class="parent">
    <div class="part one">
      <div class="title">多角</div>
      <div id="square1">JAVA</div>
      <div id="square2">python</div>
      <div id="square3">C++</div>
      <div id="square4">민법</div>
      <div id="square5">수능수학</div>
      <div id="square6">수능국어</div>
      <div id="square7">재수</div>
      <div id="square8">이모티콘제작</div>
      <div id="bsquare7">형법</div>
      <div id="square9">입시미술</div>
      <div id="square10">도전만화</div>
      <div id="square11">수능영어</div>
      <div id="square12">OPIc</div>
      <div id="bsquare1">flutter</div>
      <div id="bsquare5">민사소송법</div>
      <div id="bsquare7">미분적분학</div>
      <div id="bsquare10">R</div>
      <button class="startbutton" @click="navigateToStudyRoom">공부 시작하기</button>
    </div>
    <div class="part two">2</div>
    <div class="part three">3</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const navigateToStudyRoom = () => {
  router.push('/studyroom') 
}

</script>

<style lang="scss" scoped>
.title {
  padding-top: 5%;
  font-size: 22rem;
  font-weight: bold;
  position: relative;
  z-index: 20;
}
.startbutton {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  font-family: 'NanumSquareNeo';
  font-size: 20px;
  font-weight: 600;
  z-index: 100;
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
 
      background-image: url('https://img.freepik.com/free-vector/detailed-wireframe-terrain-landscape-in-black-and-white_1048-11902.jpg?w=1380&t=st=1705038502~exp=1705039102~hmac=ab8142a255e5fb577d99e6111f45612933a987327ade60ae55e02646d932400a');

      // background-image: url('');


    
    background-size: cover;
    background-position: center;
    }
    .two {
      background-color: rgb(195, 195, 255);
    }
    .three {
      background-color: rgb(195, 195, 195);
    }
    //자바
    @keyframes move1h {
      from {
        width: 30px;
        height: 20px;
        background-color:gray;
        font-size: 0;
      }
      to {
        top:120px;
        left: 200px;
        width:150px;
        height: 100px;
        background-color:rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    //플러터
    @keyframes move1h_1 {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 120px;
        width: 225px;
        height: 150px;
        background-color: gainsboro;
        font-size: x-large;
      }
    }
    //파이썬
    @keyframes move2h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 100px;
        left: 430px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    //씨
    @keyframes move3h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 400px;
        left: 1200px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'Roboto Mono', monospace;
      }
    }
    //민법
    @keyframes move4h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 300px;
        left: 120px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'Eulyoo1945';
      }
    }
    //수능수학
    @keyframes move5h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 100px;
        left: 1050px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
      }
    }
    //민사소송법
    @keyframes move5h_1 {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 600px;
        left: 1200px;
        width: 225px;
        height: 150px;
        background-color: rgb(207, 207, 207);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    //국어
    @keyframes move6h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 650px;
        left: 650px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    // 재수
    @keyframes move7h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 550px;
        left: 140px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'Eulyoo1945';
      }
    }
    //미분적분학
    @keyframes move7h_1 {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 630px;
        left: 380px;
        width: 225px;
        height: 150px;
        background-color: gainsboro;
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    //이모티콘제작
    @keyframes move8h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 130px;
        left: 1300px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
      }
    }
    //입시미술
    @keyframes move9h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 400px;
        left: 350px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'Eulyoo1945';
      }
    }
    //도전만화
    @keyframes move10h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 620px;
        left: 900px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }
    //알
    @keyframes move10h_1 {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 350px;
        left: 1350px;
        width: 225px;
        height: 150px;
        background-color: gainsboro;
        font-size: x-large;
        font-family: 'Roboto Mono', monospace;
      }
    }
    //수능영어
    @keyframes move11h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 230px;
        left: 380px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
      }
    }
    //오픽
    @keyframes move12h {
      from {
        width: 30px;
        height: 20px;
        background-color: gray;
        font-size: 0;
      }
      to {
        top: 230px;
        left: 1110px;
        width: 150px;
        height: 100px;
        background-color: rgb(190, 190, 190);
        font-size: x-large;
        font-family: 'ChosunGs';
      }
    }

div[id^="square"] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 150px;
  height: 100px;
  background-color: rgb(190, 190, 190);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  border: 4px solid #000;
  border-radius: 4px 4px 4px 4px;
}

div[id^="bsquare"] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 225px;
  height: 150px;
  background-color: rgb(255, 61, 61);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  border: 4px solid #373737;
  border-radius: 4px 4px 4px 4px;
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
</style>