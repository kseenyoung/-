<template>
  <div class="room">
    <div class="studyroomheader">
      <div class="flow">
        <div class="lastname">java 마스터
          <div class="untilnow">
            03:40
          </div>
        </div>
        <div class="nowname">
          Python 마스터
          <div class="untilnow">
            ~07:20
          </div>
        </div>
        <div class="nextname">C++ 마스터
          <div class="untilnow">
            ~10:20
          </div>
        </div>
      </div>

    </div>
    
    <div class="containers">
      <div class="video-players">
        <div class="video-player-1">
          <video class="video" ref="video1">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video2">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video3">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video4">
            <source src="@/assets/movie.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video5">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video6">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video7">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video8">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div>

        <div class="video-player-2">
          <video class="video2" ref="video9">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video2" ref="video10">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video2" ref="video11">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video2" ref="video12">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div>

        <div class="video-player-3">
          <video class="bigvideo" ref="video13">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
        </div>
      </div>

      <div class="achievement">
          <div  class="rate"  v-if="showRate">
            달성률
          </div>
        </div>
      <div class="QnA">
        <div  v-if="showQuestion">
          <QnAListView/>
        </div>
      </div>

      <div class="bar">
      <button class="ratetoggle" @click="toggleRate">▼</button>
      <button class="questiontoggle" @click="toggleQuestion">▼</button>
    </div>

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
            showQuestion: true
        };
    },
    methods: {
      toggleRate() {
            this.showRate = !this.showRate;
        },
        toggleQuestion() {
            this.showQuestion = !this.showQuestion;
        }
    },


};


</script>

<style>
.room {
  flex-direction: column; 
  height: 100%;
  background-color: white;
}
.studyroomheader {
  width:calc(100%-320px);
  background-color: lightgray;
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
  font-size: 30px;
  padding-right: 30px;
  font-weight: 500;
}
.nowname {
  font-size: 40px;
  font-weight: 700;
  text-align: center;
  padding-inline: 30px;
  background-color: white;
}
.nextname{
  text-align: center;
  font-size: 30px;
  padding-left: 30px;
  font-weight: 500;
}

.untilnow{
  font-size: 20px;
  font-weight: 700;
}

.containers {
  display: flex;
  justify-content: center; 
  align-items: center;
  background-color: white;
  width: 60%;
  margin: auto;
}

.video-players {
  display: flex;
  flex-wrap: wrap;
  flex:3;
  border: 10px white solid;
  box-sizing: border-box;
}

.video-player-2 {
  flex: 1;
}
.video-player-3 {
  flex: 1;
}

.video {
  width: 25%;
  height: auto;
  border: 5px white solid;
  box-sizing: border-box;
}
.video2 {
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
  font-size: 40px;
  padding: 15px;
  border-radius: 15px;
  border: 3px black;
  box-shadow: 0 40px 40px rgba(0, 0, 0, 0.25);
  border: 2px solid black;
  border-radius: 15px;
  background-color: rgba(255, 255, 204, 0.9);
  width: 50%;
}
.achievement {
  position: fixed;
  right:0;
}
.QnA {
  position: fixed;
  right:0;
}

.ratetoggle{
  background-color: white;
  position: absolute;
  right: 0;
  bottom:0;
  width: 30px;
  height: 30px;
  border-radius: 15px;
}

.questiontoggle{
  background-color: white;
  position: absolute;
  right: 30px;
  bottom:0;
  width: 30px;
  height: 30px;
  border-radius: 15px;
}
.bar{
  position: absolute;
  bottom: 5%;
  left: 35px;
  width: 100%;
  height: 15%;
  /* background-color: blue; */
}
</style>