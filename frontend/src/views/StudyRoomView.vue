<template>
  <div class="room">
    <div class="studyroomheader">
      <div class="nowname">
        <div class="nametag">Python 마스터</div>
        <img class="mute" @click="toggleMute" src="@/assets/img/studyroom/mute.png" alt="음소거" />
        <img class="pause" @click="togglePause" src="@/assets/img/studyroom/pause.png" alt="휴식중" />
      </div>
      <div class="lastlater">
        <div class="lastname">java 마스터 3:40</div>
        <div class="latername">C++ 마스터 ~10:20</div>
      </div>
    </div>

    <div class="containers">
      <div class="video-players">
        <div class="column">
          <div class="video-player-3">
            <div class="bigvideo" ref="video13">
              <user-video :stream-manager="mainStreamManager" />
            </div>
          </div>
          <div class="video-player-1">
            <user-video class="videog1" ref="video1" :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)" />
            <user-video class="videog1" ref="video2" :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)" />
            <user-video class="videog1" ref="video3" :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)" />
            <user-video class="videog1" ref="video5" v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
              :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)" />
          </div>
        </div>

        <div class="video-player-2">
          <user-video class="videog2" ref="video6" :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)" />
          <user-video class="videog2" ref="video7" :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)" />
          <user-video class="videog2" ref="video8" :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)" />
          <user-video class="videog2" ref="video9" :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)" />
          <user-video class="videog2" ref="video10" :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)" />
        </div>
      </div>
      <div class="bar">
        <button class="ratetoggle" @click="toggleRate">달성률</button>
        <button class="questiontoggle" @click="toggleQuestion">질문하기</button>
      </div>
      <div class="utility">
        <div class="achievement">
          <div class="rate" v-if="showRate">
            <p class="titletag">공부시간</p>
            <div class="studytime">01:30:32</div>
            <hr />
            <p class="titletag">달성률</p>
            <div class="dagak">
              <!-- <img src="@/assets/img/studyroom/hexagon.png" style="transform: rotate(30deg)" alt="" /> -->
              <Dagak />
            </div>
            <div class="ratedetail">
              java 마스터 --- <b>140%</b><br />
              Python 마스터 --- <b>75%</b><br />
              C++ 마스터 --- <b>0%</b>
            </div>
          </div>
        </div>
      </div>
      <div class="QnA">
        <div v-if="showQuestion">
          <QnAListView />
        </div>
      </div>
    </div>

    <div class="black" v-if="isPause">
      <p class="resttitle">휴식중</p>
      <p class="resttime">~00:30</p>
      <img class="play" @click="togglePause" src="@/assets/img/studyroom/whiteplay.png" alt="다시시작" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { useUserStore } from '@/stores/user';
import QnAListView from "@/components/room/QnAListView.vue";
import UserVideo from "@/components/room/UserVideo.vue";
import Dagak from "@/components/dagak/Dagak.vue";


axios.defaults.headers.post['Content-Type'] = 'application/json';


const store = useUserStore();
const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://localhost:8080/dagak/';


const OV = ref(undefined);
const OVMy = ref(undefined);
const session = ref(undefined);
const mySession = ref(store.loginUser.sub);
const mainStreamManager = ref(undefined);
const mainStreamManagerMySession = ref(undefined);
const publisher = ref(undefined);
const publisherMySession = ref(undefined);
const subscribers = ref([]);

// 초기 데이터(계정 세션 아이디, 계정 이름)
const myUserName = ref(store.loginUser.id);

// 방 입장
const enterRoom = async (sessionId) => {
  let token = await createSession(sessionId);
  return token;
};

// 방 생성
const createSession = async (sessionId) => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + "room",
    { sign: "enterRandomroom", sessionName: sessionId, videoCodec: "VP8" },
    {
      headers: { "Content-Type": "application/json" },
    }
  );

  return response.data.result;
};

const loginSession = () => {
  OVMy.value = new OpenVidu();
  // 전체 참여 세션
  mySession.value = OVMy.value.initSession();
  mySession.value.on("signal", async ({ stream }) => {
    console.log(stream, "님이 로그인했습니다.");
    alert("친구가 로그인했어요!");
    await axios.post(
      "https://i10a404.p.ssafy.io/openvidu/api/signal",
      {},
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU",
        },
        data: {
          session: myUserName.value,
          type: "login-callBack",
          data: "yj",
        },
      }
    );
  });

  mySession.value.on("signal:login-callBack", ({ stream }) => {
    console.log("[콜백] 친구 ", stream, "님이 로그인했습니다.");
  });

  mySession.value.on("exception", ({ exception }) => {
    console.warn(exception);
  });

  enterMyRoom().then((token) => {
    console.log("나의 방 토큰:", token);

    mySession.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        let publisherMySession = OVMy.value.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: false,
          publishVideo: false,
          resolution: "640x480",
          frameRate: 30,
          insertMode: "APPEND",
          mirror: false,
        });

        // Set the main video in the page to display our webcam and store our Publisher
        mainStreamManagerMySession.value = publisherMySession;
        publisherMySession.value = publisherMySession;

        // --- 6) Publish your stream ---;
        mySession.value.publish(publisherMySession.value);
        console.log("mySession에 로그인했습니다.");
        alert("로그인에 성공했습니다.");
      })
      .catch((error) => {
        console.log(
          "다음 세션에 로그인하는데 오류가 발생했습니다!:",
          error.code,
          error.message
        );
      });
  });
};

const joinSession = () => {
  OV.value = new OpenVidu();
  session.value = OV.value.initSession();

  session.value.on("streamCreated", ({ stream }) => {
    const subscriber = session.value.subscribe(stream);
    subscribers.value.push(subscriber);
  });

  session.value.on("streamDestroyed", ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0);
    if (index >= 0) {
      subscribers.value.splice(index, 1);
    }
  });

  session.value.on("exception", ({ exception }) => {
    console.warn(exception);
  });

  enterRoom(mySession.value).then((token) => {
    session.value.connect(token, { clientData: myUserName.value }).then(() => {
      publisher.value = OV.value.initPublisher(undefined, {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        resolution: "640x480",
        frameRate: 30,
        insertMode: "APPEND",
        mirror: false,
      });

      mainStreamManager.value = publisher.value;

      session.value.publish(publisher.value);
    }).catch((error) => {
      console.log("There was an error connecting to the session:", error.code, error.message);
    });
  });

  window.addEventListener("beforeunload", leaveSession);
};

const leaveSession = () => {
  if (session.value) session.value.disconnect();

  session.value = undefined;
  mainStreamManager.value = undefined;
  publisher.value = undefined;
  subscribers.value = [];
  OV.value = undefined;

  window.removeEventListener("beforeunload", leaveSession);
};

const updateMainVideoStreamManager = (stream) => {
  if (mainStreamManager.value === stream) return;
  mainStreamManager.value = stream;
};


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

const showRate = ref(true);
const showQuestion = ref(true);
const isPause = ref(false);

const toggleRate = () => {
  showRate.value = !showRate.value;
};

const toggleQuestion = () => {
  showQuestion.value = !showQuestion.value;
};

const toggleMute = (video) => {
  if (video && video.value instanceof HTMLVideoElement) {
    video.value.muted = !video.value.muted;
  }
};

const togglePause = () => {
  isPause.value = !isPause.value;
};

onMounted(() => {
  joinSession();
});
</script>

<style>
.room {
  flex-direction: column;  
  height: 60%;
}

.resttitle {
  font-size: 100px;
}

.resttime {
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
  top: 100px;
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

.nametag {
  font-weight: 900;
  margin-right: 40px;
  margin-left: 10px;
}

.lastlater {
  display: flex;
}

.play {
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
  flex: 3;
  position: relative;
  /* background-color: black; */
  width: 100%;
  display: flex;
  flex-direction: row;
  transform-origin: left top;
  transform: rotate(90deg);
  /* 90도 회전 */
}

.column {
  flex: 4;
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
  display: flex;
  border-top: white solid 5px;
  border-bottom: white solid 5px;
  flex-direction: row;
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
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* 그림자 효과 추가 */
}

.QnA {
  position: fixed;
  right: 0;
  bottom: 0%;
}

.achievement {
  position: fixed;
  right: 0;
  bottom: 5%;
  height: 60%;
  justify-content: center;
  display: flex;
}

.titletag {
  margin: 0;
}

.dagak {
  text-align: center;
  /* padding: 20px; */
  position: relative;
  z-index: 1;
}

.dagak img {
  width: 60%;
  height: auto;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
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

.mute {
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-top: 20px;
}

.pause {
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: 20px;
}

.questiontoggle {
  background-color: rgb(200, 200, 200);
  width: 120px;
  height: 40px;
  border: gainsboro;
  border-radius: 15px 15px 0 0;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 10;
  top: -40px;
}

.ratetoggle {
  background-color: gainsboro;
  width: 120px;
  height: 40px;
  border: gainsboro;
  border-radius: 15px 15px 0 0;
  transition: background-color 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  top: -40px;
  position: relative;
}

.questiontoggle:hover,
.ratetoggle:hover {
  background-color: white;
  /* border-bottom: 2px solid white;*/
}
</style>
