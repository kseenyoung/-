<template>
  <div class="room">
    <div class="name">
      스터디룸 이름
      <div class="untilnow">순공 시간</div>
    </div>
    <div class="containers">
      <div class="video-players">
        <div class="video-player-1">
          <video class="video" ref="video1">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video2">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video3">
            <source src="@/assets/video.mp4" type="video/mp4" />
          </video>
          <video class="video" ref="video4">
            <source src="@/assets/video.mp4" type="video/mp4" />
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

      <div>
        <QnAListView/>
      </div>

      <div class="achievement">
        <button class="ratetoggle" @click="toggleRate">
        ▼
        </button>
        <div  class="rate"  v-if="showRate">
            달성률
        </div>
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
            showRate: true
        };
    },
    methods: {
        toggleRate() {
            this.showRate = !this.showRate;
        }
    },
};


</script>

<style>
.room {
  display: block; /* 부모 .containers의 flex를 덮어쓰기 위해 block으로 설정 */
  text-align: center; /* 자식 요소를 가운데 정렬하기 위해 추가 */
  flex-direction: column; /* 세로 중앙 정렬을 위해 컬럼 방향으로 설정 */
  margin: 30px;
  border-radius: 15px;
  box-shadow: 0 40px 40px rgba(0, 0, 0, 0.25);
}

.name {
  background-color: lavender;
  font-size: 60px;
  font-weight: 700;
  text-align: left;
  padding: 15px;
  border-radius: 15px 15px 0 0;
}

.untilnow{
  font-size: 20px;
  font-weight: 700;
}

.containers {
  display: flex;
  background-color: white;
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
  height: auto;
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
  height: 50px;
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
.achievement{
  position: absolute;
  bottom: 15%;
  left: 35px;
  width: 60%;
  height: 50px;
  margin: 15px;

}
</style>