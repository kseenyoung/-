<template>
  <div class="room">
    <div class="studyroomheader">
        <div class="nowname">
          <div class="nametag">Python 마스터</div>
          <img class= "mute" @click="toggleMute" src="@/assets/img/studyroom/mute.png" alt="음소거">
          <img class= "pause" @click="togglePause" src="@/assets/img/studyroom/pause.png" alt="휴식중">
        </div>
          <div class="lastlater">
            <div class="lastname">java 마스터 3:40
            </div>
            <div class="latername">C++ 마스터 ~10:20
            </div>
      </div>
    </div>

    <div class="containers">
      <div class="video-players">
        <div class="column">
        <div class="video-player-3">
          <video class="bigvideo" ref="video13">
            <source src="@/assets/movie.mp4" type="video/mp4" />
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
        </div>
      </div>

        <div class="video-player-2">
          <video class="videog2" ref="video5">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video6">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video7">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video8">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="videog2" ref="video9">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
        </div>
      </div>
          <div class="bar">
            <button class="ratetoggle" @click="toggleRate">달성률</button>
            <button class="questiontoggle" @click="toggleQuestion">질문하기</button>
           
      </div>
      <div class="utility">
              <div class="achievement">
                <div  class="rate"  v-if="showRate">
                  <p class="titletag">공부시간</p>
                  <div class="studytime">01:30:32</div>
                  <hr>
                  <p class="titletag">달성률</p>
                    <div class="dagak">
                    <img src="@/assets/img/studyroom/hexagon.png" style="transform:rotate(30deg);" alt="">
                    </div>
                    <div class="ratedetail">
                      java 마스터 --- <b>140%</b><br>
                      Python 마스터 --- <b>75%</b><br>
                      C++ 마스터 --- <b>0%</b>
                    </div>
          </div>
        </div>
      </div>
      <div class="QnA">
        <div  v-if="showQuestion">
          <QnAListView/>
        </div>
      </div>
      </div>

<div class="black" v-if=isPause>
  <p class="resttitle">휴식중</p>
  <p class="resttime">~00:30</p>
  <img class= "play" @click="togglePause" src="@/assets/img/studyroom/whiteplay.png" alt="다시시작">
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
        },
        handleScroll() {
      const header = this.$refs.studyroomheader;

     
        },
      }
};


</script>

<style>
.room {
  flex-direction: column; 
  height: 100%;
}
.resttitle{
  font-size: 100px;
}
.resttime{
  font-size: 50px;
}
.black {
    position: fixed;
    top: 50%;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.8);
    color: white;
    text-align: center;
    transform: translateY(-50%);
    z-index: 101;
}
.studyroomheader {
  background-color: gainsboro;
  color: black;
  justify-content: space-around;
  height: 100px;
  /* border: 2px black dashed; */
  width: 62.5%;
  position: relative;
  top:100px;
}
.nowname {
  font-size: 40px;
  text-align: left;
  /* background-color: white; */
  display: flex;
}
.lastname {
  padding-left: 15px;
  padding-right: 15px;
}
.latername {
  border-left: 1px dashed black;
  padding-left: 15px;
  padding-right: 15px;
}
.nametag{
  font-weight: 900;
  margin-right: 40px;
  margin-left: 10px;
}
.lastlater {
  display: flex;
}
.play{
  width: 40px;
  height: 40px;
  cursor: pointer;
}
.containers {
  width: 100%;
  display: flex;
  margin-top: 110px;
}
.video-players {
  display: flex;
  flex-wrap: wrap;
  /* border: 10px black solid; */
  box-sizing: border-box;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  flex: 5;
}
.bar {
  flex : 3;
  position: relative;
  /* background-color: black; */
  width: 100%;
  display: flex;
  flex-direction: row;
  transform-origin: left top;
  transform: rotate(90deg); /* 90도 회전 */
}
.column{
  flex:4;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.video-player-2 {
  flex: 1;
  background-color: white;
}
.video-player-3 {
  flex: 4;
}
.video-player-1 {
  flex: 1;
  border-top: white solid 5px;
}
.videog1 {
  width: 25%;
  height: auto;
  border: 5px white solid;
  box-sizing: border-box;
}
.videog2 {
  width: 100%;
  height: auto;
  border: 5px white solid;
  box-sizing: border-box;

}

.bigvideo {
  height: 100%;
  width: auto;
  display: flex;
  border: 5px white solid;
  box-sizing: border-box;
  object-fit: cover;
}

.rate {
  padding: 2px;
  border: 2px solid black;
  background-color: white;
  width: 320px;
  height: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
}
.QnA {
  position: fixed;
  right:0;
  bottom: 0%;
}
.achievement {
  position: fixed;
  right:0;
  bottom: 5%;
  height: 60%;
  justify-content: center;
  display: flex;
}
.titletag {
  margin:0;
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

.mute{
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-top: 20px;
}
.pause{
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: 20px;
}
.questiontoggle{
  background-color: rgb(200, 200, 200);
  width: 120px;
  height: 40px;
  border: gainsboro;
  border-radius: 15px 15px 0 0;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 10;
  top:-40px;
}
.ratetoggle{
  background-color: gainsboro;
  width: 120px;
  height: 40px;
  border: gainsboro;
  border-radius: 15px 15px 0 0;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  top:-40px;
  position: relative;
}

.questiontoggle:hover, .ratetoggle:hover{
  background-color: white;
  /* border-bottom: 2px solid white;*/
}

</style>