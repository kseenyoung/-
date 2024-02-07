<template>
  <div class="room">
    <div class="studyroomheader">
      <div class="nowname">
        <div class="nametag"> Python 마스터 ({{ subscribers.length + 1 }})</div>
        <img class="mute" @click="toggleMute" src="@/assets/img/studyroom/mute.png" alt="음소거" />
        <img class="pause" @click="togglePause" src="@/assets/img/studyroom/pause.png" alt="휴식중" />
        <button class="btn btn-outline-dark me-2 leave" @click="leaveStudyRoom">나가기</button>
      </div>
      <div class="lastlater">
        <div class="lastname">java 마스터 3:40</div>
        <div class="latername">C++ 마스터 ~10:20</div>
        <div>
        <RouterLink :to="{ name: 'studyRate' }">달성률</RouterLink>
        |
        <RouterLink :to="{ name: 'QnAList' }">질문하기</RouterLink>
      </div>
      </div>
    </div>
    <div class="containers">
      <div class="video-players">
          <div class="video-player-3">
            <div class="bigvideo" ref="video13">
              <!-- 첫 번째 subscriber가 없는 경우에만 mainStreamManager를 표시 -->
              <user-video v-if="subscribers.length === 0" :stream-manager="mainStreamManager" />
              <!-- 첫 번째 subscriber가 있는 경우에는 해당 subscriber를 표시 -->
              <user-video v-else :stream-manager="subscribers[0]"
                @click.native="updateMainVideoStreamManager(subscribers[0])" />
            </div>
          </div>
        <div class="video-player-2" v-if="subscribers.length > 0 ">
            <!-- 총 2명 -->
            <template v-if="subscribers.length === 1">
              <user-video class="videog2" :stream-manager="mainStreamManager" />
            </template>
            <!-- 총 3명 -->
            <template v-if="subscribers.length === 2">
              <user-video class="videog3" :stream-manager="mainStreamManager" />
              <user-video class="videog3" v-for="(sub, index) in subscribers.slice(1, 2)" :key="index"
                :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)" />
            </template>
            <!-- 총 4명 -->
            <template v-else-if="subscribers.length === 3">
              <user-video class="videog4" :stream-manager="mainStreamManager" />
              <user-video class="videog4" v-for="(sub, index) in subscribers.slice(1, 3)" :key="index"
                :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)" />
            </template>
            <!-- 총 5명 -->
            <template v-else-if="subscribers.length === 4">
              <user-video class="videog5" :stream-manager="mainStreamManager" />
              <user-video class="videog5" v-for="(sub, index) in subscribers.slice(1, 4)" :key="index"
                :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)" />
            </template>
            <!-- 총 6명 -->
            <template v-else-if="subscribers.length === 5">
              <user-video class="videog6" :stream-manager="mainStreamManager" />
              <user-video class="videog6" v-for="(sub, index) in subscribers.slice(1, 5)" :key="index"
                :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)" />
            </template>
        </div>
      </div>

      <transition name="flip" mode="out-in">
        <div class="mypage-content flex-fill" :key="$route.fullPath">
          <RouterView />
        </div>
      </transition>
    </div>
    <div class="black" v-if="isPause">
      <p class="resttitle">휴식중</p>
      <p class="resttime">~00:30</p>
      <img class="play" @click="togglePause" src="@/assets/img/studyroom/whiteplay.png" alt="다시시작" />
    </div>
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount, onMounted } from 'vue'
import axios from 'axios'
import { OpenVidu, Stream } from 'openvidu-browser'
import { useUserStore } from '@/stores/user'
import QnAListView from '@/components/room/QnAListView.vue'
import UserVideo from '@/components/room/UserVideo.vue'
import { useRouter } from 'vue-router'
import Dagak from '@/components/dagak/Dagak.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json'

const router = useRouter()
const store = useUserStore()
const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://localhost:8080/dagak/'

const OV = ref(undefined)
const session = ref(undefined)
const mySession = ref(store.loginUserInfo.sub)
const mainStreamManager = ref(undefined)
const publisher = ref(undefined)
const subscribers = ref([])
const question = ref('')
const leave = ref("refresh");

console.log('구독자들: ', subscribers.value)
console.log('STORE USER  :  ', store.loginUser)
// 초기 데이터(계정 세션 아이디, 계정 이름)
const myUserName = ref(store.myUserName)

// 방 입장
const enterRoom = async (sessionId) => {
  let token = await createSession(sessionId)
  return token
}

// 방 생성
const createSession = async (sessionId) => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'room',
    {
      sign: 'enterRandomroom',
      userId: store.myUserName,
      sessionName: store.loginUserInfo.sub,
      videoCodec: 'VP8'
    },
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )
  console.log(response.data.result.session)
  mySession.value = response.data.result.session
  // store.loginUserInfo.sub = response.data.result.session;
  return response.data.result.token
}

const askQuestion = async () => {
  console.log(mySession.value + '에서 질문합니다! ')
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'room',
    { sign: 'askQuestion', session: mySession.value, data: question.value },
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )
  return response.data.result
}

const answerQuestion = async () => {
  console.log(mySession.value + '에서 답변합니다! ')
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'room',
    { sign: 'answerQuestion', session: mySession.value, data: question.value },
    {
      headers: { 'Content-Type': 'application/json' }
    }
  )
  return response.data.result
}

const joinSession = () => {
  OV.value = new OpenVidu()
  session.value = OV.value.initSession()

  session.value.on('signal:question', (stream) => {
    alert('질문이 들어왔습니다!')
    console.log('질문 내용:' + stream.data)
    const data = JSON.parse(stream.data)
    console.log(data.questionId)
  })

  session.value.on('signal:answer', (stream) => {
    alert('답변이 달렸습니다!')
    console.log('답변 내용:' + stream.data)
  })

  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    console.log("subscribers: " + subscriber.value);
    subscribers.value.push(subscriber)
  })

  session.value.on('streamDestroyed', ({ stream }) => {
    // const updatedSubscribers = subscribers.value.filter(sub => sub !== stream.streamManager);
    // subscribers.value = updatedSubscribers;
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  session.value.on('exception', (exception) => {
    console.warn(exception)
    if (exception.name == 'NO_STREAM_PLAYING_EVENT') {
      subscribers.value.forEach((element) => {
        console.log('tetete: ', element.stream)
        if (element.stream.streamId == exception.origin.stream.streamId) {
          subscribers.value.pop(element)
        }
      })
    }
  })

  enterRoom(store.loginUserInfo.sub).then((token) => {
    console.log('token' + token)
    store.studyRoomSessionToken = token
    session.value
      .connect(token, store.myUserName)
      .then(() => {
        publisher.value = OV.value.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: '640x480',
          frameRate: 30,
          insertMode: 'APPEND',
          mirror: false
        })

        mainStreamManager.value = publisher.value

        session.value.publish(publisher.value)
        store.isInSession = true
      })
      .catch((error) => {
        console.log('There was an error connecting to the session:', error.code, error.message)
      })
  })

  // window.addEventListener("beforeunload", leaveSession(false));
}

const leaveStudyRoom = async () => {
  alert('나가기 버튼을 눌렀습니다.')
  leave.value = "leave";
  await leaveSession()
  router.push('/')
}

const leaveSession = async () => {
  if (leave.value == "leave") alert("의도적으로 나갑니다");
  alert('나갑니다.')
  if (session.value) session.value.disconnect()

  session.value = undefined
  mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OV.value = undefined

  const response = await axios
    .post(
      APPLICATION_SERVER_URL + 'room',
      { sign: 'leaveSession', leave: leave.value },
      {
        headers: { 'Content-Type': 'application/json' }
      }
    )
    .then(() => {
      alert('퇴실완료.')
    })

  // window.removeEventListener("beforeunload", leaveSession(true));
}

// const updateMainVideoStreamManager = (stream) => {
//   if (mainStreamManager.value === stream) return
//   mainStreamManager.value = stream
// }

const updateMainVideoStreamManager = (stream) => {
  mainStreamManager.value = stream;
}

const video1 = ref(null)
const video2 = ref(null)
const video3 = ref(null)
const video4 = ref(null)
const video5 = ref(null)
const video6 = ref(null)
const video7 = ref(null)
const video8 = ref(null)
const video9 = ref(null)
const video10 = ref(null)
const video11 = ref(null)
const video12 = ref(null)
const video13 = ref(null)

const showRate = ref(true)
const showQuestion = ref(true)
const isPause = ref(false)

const toggleRate = () => {
  showRate.value = !showRate.value
}

const toggleQuestion = () => {
  showQuestion.value = !showQuestion.value
}

const toggleMute = (video) => {
  if (video && video.value instanceof HTMLVideoElement) {
    video.value.muted = !video.value.muted
  }
}

const togglePause = () => {
  isPause.value = !isPause.value
}

onMounted(() => {
  leaveSession().then(() => {
    joinSession();
  });
})

onBeforeUnmount(() => {
  alert("스터디룸에서 다른 페이지로 라우팅!")
  leaveSession();
});
console.log('구독자들: ', subscribers.length)
console.log('구독자들: ', subscribers.value.length)


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
  height: 50%;
  flex-wrap: wrap;
  box-sizing: border-box;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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


.video-player-2 {
  flex: 4;
  background-color: white;
  display: flex;
  flex-wrap: wrap; /* 요소들이 한 줄을 넘어갈 경우 다음 줄로 넘어갈 수 있도록 설정 */
  /* flex-direction: column;  */
}
.video-player-3 {
  flex: 4;
}


.videog2 {
  width: 100%; 
  border: 5px white solid;
  box-sizing: border-box;
  flex-grow: 1;
}
.videog3{
  width:50%;  
  border: 5px white solid;
  box-sizing: border-box;
}
.videog4{
  width:50%;  
  border: 5px white solid;
  box-sizing: border-box;
}
.videog5 {
  width:50%; 
  border: 5px white solid;
  box-sizing: border-box;
}
.videog6 {
  height: calc(100% / 5);
  border: 5px white solid;
  box-sizing: border-box;
  flex-direction: column;
}
.bigvideo {
  width:100%;
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
