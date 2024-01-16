<template>
  <div class="parent">
    <div class="part one">
      <div class="title">多角</div>
      <div id="square1">JAVA</div>
      <div id="square2">python</div>
      <div id="square3">C++</div>
      <div id="square4">민법</div>
      <div id="square5">수능수학</div>
      <!-- <div id="square6">수능국어</div> -->
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
      <button class="startbutton" @click="navigateToStudyRoom">공부시작</button>
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
  color: black;
}
.startbutton {
  position: absolute;
  top: 90%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 25px;
  font-family: 'NanumSquareNeo';
  font-size: 40px;
  font-weight: 600;
  z-index: 100;
  background-color: black;
  color: white;
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
    
    @keyframes move1h {
        from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray;
            font-size: 0;}
        to {top:25vh;
            left: 65vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
          font-size: x-large;
          font-family: 'ChosunGs';
   
        }
        }

        @keyframes move1h_1 {
        from {top:50vh;
            left: 50vw;
              width: 30px;
              height: 20px;
              background-color:gray;
            font-size: 0;}
        to {top:20vh;
            left: 90vw;
            width: 225px;
            height: 150px;
            background-color:gainsboro;
          font-size: x-large;
          font-family: 'Roboto Mono', monospace ;
      
 }
        }

        @keyframes move2h {
        from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray;
            font-size: 0;}
        to {top:35vh;
            left: 80vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
          font-size: x-large;
          font-family: 'ChosunGs';}
        }

        @keyframes move3h {
        from {left: 50vw;
          width: 30px;
              height: 20px;
              background-color:gray;
            font-size: 0;}
        to {left: 90vw;
          width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Roboto Mono', monospace ;}
        }

        @keyframes move4h {
        from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray; 
              font-size:0;}
        to {top:70vh;
            left: 80vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }

        @keyframes move5h {
        from {top:50vh;
            left: 50vw;
              width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {top:85vh;
            left: 65vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;}
        }

        @keyframes move5h_1 {
        from {top:50vh;
            left: 50vw;
              width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {top:85vh;
            left: 85vw;
            width: 225px;
            height: 150px;
            background-color:rgb(207, 207, 207);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }
        @keyframes move6h {
        from {top: 50vh;
              width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {top: 93vh;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }

        @keyframes move7h {
            from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;
    ;}
        to {top:85vh;
            left: 30vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }

        @keyframes move7h_1 {
            from {top:50vh;
            left: 50vw;
                  width: 30px;
                  height: 20px;
                  background-color:gray;
                  font-size:0;}
          
        to {top:85vh;
            left:10vw;
            width: 225px;
            height: 150px;
            background-color:gainsboro;
            font-size: x-large;
  }
        }


        @keyframes move8h {
            from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {top:70vh;
            left:20vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;}
        }

        @keyframes move9h {
        from {left: 50vw;
          width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {left: 10vw;
          width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }


        @keyframes move10h {
            from {top:50vh;
            left: 50vw;
            width: 30px;
              height: 20px;
              background-color:gray;
              font-size:0;}
        to {top:35vh;
            left: 20vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'Eulyoo1945';}
        }

        @keyframes move10h_1 {
            from {top:50vh;
            left: 50vw;
            width: 30px;
            height: 20px;
            background-color:gray;
            font-size:0;}
        to {top:20vh;
            left: 10vw;
            width: 225px;
            height: 150px;
            background-color:gainsboro;
            font-size: x-large;
            font-family: 'Roboto Mono', monospace ;}
        }


        @keyframes move11h {
            from {top:50vh;
            left: 50vw;
            width: 30px;
            height: 20px;
            background-color:gray;
            font-size:0;}
        to {top:25vh;
            left: 35vw;
            width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;}
        }



        @keyframes move12h {
        from {top: 50vh;
          width: 30px;
            height: 20px;
            background-color:gray;
            font-size:0;}
        to {top: 20vh;
          width:150px;
            height: 100px;
            background-color:rgb(190, 190, 190);
            font-size: x-large;
            font-family: 'ChosunGs'}
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
  color: black;
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