<template>
  <div class="room">
    <div class="studyroomheader">
      <div class="flow">
        <div class="lastname">
          <img src="@/assets/arrow.png" alt="">
          <div>java 마스터<br>
          3:40
        </div>
        </div>
        <div class="nowname">
          <div class="nametag">Python 마스터</div>
        </div>
        <div class="nextname">
          <img src="@/assets/arrow.png" alt="">
          <div>C++ 마스터<br>
          ~10:20</div>
        </div>
      </div>

    </div>
    
    <div class="containers">
      <div class="video-players">
        <div class="video-player-3">
          <video class="bigvideo" ref="video13">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div>
        <div class="video-player-1">
          <video class="videog1" ref="video1">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video2">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video3">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video4">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video5">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video6">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video7">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video8">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog1" ref="video9">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div>
<!-- 
        <div class="video-player-2">

          <video class="videog2" ref="video10">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video11">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video12">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div> -->


      </div>

      <div class="achievement">
          <div  class="rate"  v-if="showRate">
            <p>공부시간</p>
            <div class="studytime">01:30:32</div>
            <hr>
            <p>달성률</p>
              <div class="dagak">
              <img src="@/assets/hexagon.png" style="transform:rotate(30deg);" alt="">
              </div>
              <div class="ratedetail">
                java 마스터 --- <b>140%</b><br>
                Python 마스터 --- <b>75%</b><br>
                C++ 마스터 --- <b>0%</b>
              </div>
          </div>
        </div>
      <div class="QnA">
        <div  v-if="showQuestion">
          <QnAListView/>
        </div>
      </div>

      <div class="bar">
        <img class= "mute" @click="toggleMute" src="@/assets/mute.png" alt="음소거">
        <img class= "pause" @click="togglePause" src="@/assets/pause.png" alt="휴식중">
        <button class="ratetoggle" @click="toggleRate">달성률</button>
        <button class="questiontoggle" @click="toggleQuestion">질문하기</button>
      </div>

      </div>

<div class="black" v-if=isPause>휴식중

  <img class= "pause" @click="togglePause" src="@/assets/whiteplay.png" alt="다시시작">
</div>
  </div>

</template>

<script>
import QnAListView from "@/components/room/QnAListView.vue";
import { ref, onMounted } from "vue";

export default {
    name: "VideoPlayer",
    components: { QnAListView},
    setup() {
        const video1 = ref(null);
        const video2 = ref(null);
        const video3 = ref(null);
        const video4 = ref(null);
        const video5 = ref(null);
        const video6 = ref(null);
        const video7 = ref(null);
        const video8 = ref(null);
        const video9 = ref(null);
        const video10 = ref(null);
        const video11 = ref(null);
        const video12 = ref(null);
        const video13 = ref(null);


        onMounted(() => {
            // 비디오 요소들에 대한 작업을 수행
            console.log(video1.value); // 예시: video1 요소에 접근
        });
        return {
            video1,
            video2,
            video3,
            video4,
            video5,
            video6,
            video7,
            video8,
            video9,
            video10,
            video11,
            video12,
            video13,
        };
    },
    data() {
        return {
            showRate: true,
            showQuestion: true,
            isPause: false,
        };
    },
    methods: {
      toggleRate() {
            this.showRate = !this.showRate;
        },
        toggleQuestion() {
            this.showQuestion = !this.showQuestion;
        },
        toggleMute(video) {
          if (video && video.value instanceof HTMLVideoElement) {
            video.value.muted = !video.value.muted;
          }},
        togglePause(){
          this.isPause = !this.isPause;
        }
          
        },
    


};


</script>

<style>

.black {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: black;
    color: white;
    font-size: 300px;
}
.room {
  flex-direction: column; 
  height: 100%;
  background-color: white;
}
.studyroomheader {
  width:calc(100%-320px);
  background-color: white;
  margin: 80px 0 0 0;
  color: black;
  display: flex;
  justify-content: center;
}
.flow{
  display: flex;
  /* flex: 3; */
}
.lastname{
  text-align: center;
}
.lastname img{
  width: 30%;
}
.nowname {
  font-size: 60px;
  font-weight: 900;
  text-align: center;
  background-color: white;
  text-decoration: underline; 
}
.nametag{
  padding: 20px;
  font-weight: 900;
}
.nextname{
  text-align: center;
}
.nextname img{
  width: 30%;
}
.untilnow{
  font-size: 20px;
  font-weight: 700;
}

.nowadjust{
  display: flex;
  justify-content: space-evenly;
  height: 20px;
}

.mute{
  width: 20px;
  height: 20px;
  cursor: pointer;
}

.pause{
  width: 20px;
  height: 20px;
  cursor: pointer;
}

.space{
  width: 50px;


}
.containers {
  display: flex;
  justify-content: center; 
  align-items: center;
  background-color: white;
  width: 80%;
  margin: auto;
}

.video-players {
  display: flex;
  flex-wrap: wrap;
  border: 10px black solid;
  box-sizing: border-box;
}
.video-player-1 {
  flex:3;
}
.video-player-2 {
  flex: 1;
}
.video-player-3 {
  flex: 3;
}

.videog1 {
  width: 33.3%;
  height: auto;
  border: 5px white solid;
  box-sizing: border-box;
}
.videog2 {
  width: 50%;
  height: auto;
  border: 5px white solid;
  box-sizing: border-box;
}

.bigvideo {
  width: 100%;
  height: auto;
  display: flex;
  border: 5px white solid;
  box-sizing: border-box;
  object-fit: contain;
}

.rate {
  padding: 2px;
  border: 4px solid black;
  background-color: white;
  width: 320px;
  height: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
}

.achievement {
  position: fixed;
  right:0;
  bottom: 5%;
  height: 60%;
  justify-content: center;
  display: flex;
}
.dagak {
  text-align: center;
  padding: 20px;
}
.dagak img{
  width: 60%;
  height: auto;
}
.studytime {
  font-size: 30px;
  text-align: center;
  font-weight: 700;
}

.ratedetail {
  font-size: 15px;
  text-align: center;
}
.QnA {
  position: fixed;
  right:0;
  bottom: 0%;
}

.ratetoggle{
  background-color: white;
  position: absolute;
  right:40px;
  top: 0%;
  width: 120px;
  height: 30px;
}

.questiontoggle{
  background-color: white;
  position: absolute;
  right:160px;
  width: 120px;
  height: 30px;
}

.bar{
  position: fixed;
  bottom: 0;
  height: 5%;
  background-color: white;
  width:100%;

  border: 4px solid black;
}
</style>