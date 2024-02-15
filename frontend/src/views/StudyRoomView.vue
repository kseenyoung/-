<template>
  <div class="space">
    <div class="room">
      <div class="studyroomheader div3">
        <div class="nowname">
          <div class="nametag">
            {{ subjectMapping(store.loginUserInfo.sub) }}
          </div>
          <img
            class="mute"
            @click="toggleMute"
            src="@/assets/img/studyroom/mute.png"
            alt="음소거"
          />
          <img
            v-if="!isPause"
            class="pause"
            @click="togglePause"
            src="@/assets/img/studyroom/pause.png"
            alt="휴식중"
          />
          <img
            v-else
            class="pause"
            @click="togglePause"
            src="@/assets/img/studyroom/playButton.png"
            alt="휴식중"
          />
          <button
            v-if="dagakStore.stay"
            type="button"
            class="div3 questiontoggle position-relative"
            style="margin-right: 10%; margin-left: auto; font-size: 20px; margin-top: 3%"
            @click="changeRoomAfterWait"
          >
            {{ !done ? "이동하기" : "나가기" }}
          </button>
        </div>
        <div class="lastlater" style="padding-left: 20px">
          {{ subscribers.length + 1 }} 명 공부중 ...
        </div>
      </div>
      <div class="bar">
        <!-- <button class="ratetoggle" @click="toggleRate">달성률</button> -->
      </div>
      <!-- <StudyRateView :sec="sec" :remainTime="remainTime" :categoryName="categoryName" /> -->
      <!-- <QnAListView /> -->
      <div class="containers">
        <div class="video-players div2">
          <div class="video-player-1">
            <div class="bigvideo" ref="video13">
              <!-- 첫 번째 subscriber가 없는 경우에만 mainStreamManager를 표시 -->
              <user-video
                v-if="subscribers.length === 0"
                :stream-manager="mainStreamManager"
              />
              <!-- 첫 번째 subscriber가 있는 경우에는 해당 subscriber를 표시 -->
              <user-video
                v-else
                :stream-manager="subscribers[0]"
                @click.native="updateMainVideoStreamManager(subscribers[0])"
              />
            </div>
          </div>
          <div class="video-player-2" v-if="subscribers.length > 0">
            <!-- 총 2명 -->
            <template v-if="subscribers.length === 1">
              <user-video class="videog2" :stream-manager="mainStreamManager" />
            </template>
            <!-- 총 3명 -->
            <template v-if="subscribers.length === 2">
              <user-video class="videog3" :stream-manager="mainStreamManager" />
              <user-video
                class="videog3"
                v-for="(sub, index) in subscribers.slice(1, 2)"
                :key="index"
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
            </template>
            <!-- 총 4명 -->
            <template v-else-if="subscribers.length === 3">
              <user-video class="videog4" :stream-manager="mainStreamManager" />
              <user-video
                class="videog4"
                v-for="(sub, index) in subscribers.slice(1, 3)"
                :key="index"
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
            </template>
            <!-- 총 5명 -->
            <template v-else-if="subscribers.length === 4">
              <user-video class="videog5" :stream-manager="mainStreamManager" />
              <user-video
                class="videog5"
                v-for="(sub, index) in subscribers.slice(1, 4)"
                :key="index"
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
            </template>
            <!-- 총 6명 -->
            <template v-else-if="subscribers.length === 5">
              <user-video class="videog6" :stream-manager="mainStreamManager" />
              <user-video
                class="videog6"
                v-for="(sub, index) in subscribers.slice(1, 5)"
                :key="index"
                :stream-manager="sub"
                @click.native="updateMainVideoStreamManager(sub)"
              />
            </template>
          </div>
        </div>
        <div>
          <StudyRateView
            :sec="sec"
            :remainTime="remainTime"
            :categoryName="categoryName"
            :gakOrder="gakOrder"
            @leave-study-room="leaveStudyRoom"
            @toggle-question="toggleQuestion"
          />
          <QnAListView v-if="showQuestion" />
        </div>
      </div>
    </div>
  </div>

  <!-- <button @click="showModal = true">모달 열기</button> -->

  <!-- 모달 창 -->
  <div>
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <div class="modal-header">
          <h2>우와!</h2>
          <span class="close" @click="handleModalResponse(false)">&times;</span>
        </div>
        <p>공부가 끝났습니다. 계속하시겠습니까?</p>
        <div class="modal-buttons">
          <button @click="handleModalResponse(true)">계속하기</button>
          <button @click="handleModalResponse(false)">이동하기</button>
        </div>
        <br />
        <p>이제 다른 과목으로 넘어가실 수 있습니다.</p>
        <p>{{ modalCount }}초 후 창이 닫힙니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount, onBeforeMount, onMounted, watch } from "vue";
import axios from "axios";
import { OpenVidu, Stream } from "openvidu-browser";
import { useUserStore } from "@/stores/user";
import UserVideo from "@/components/room/UserVideo.vue";
import StudyRateView from "@/components/room/StudyRateView.vue";
import { useRouter } from "vue-router";
import { useQuestionStore } from "@/stores/qustion";
import { useDagakStore } from "@/stores/dagak";
import QnAListView from "@/components/room/QnAListView.vue";
import { subjectMapping } from "@/utils/subjectMapping";

axios.defaults.headers.post["Content-Type"] = "application/json";

const dagakStore = useDagakStore();

const router = useRouter();
const store = useUserStore();
const questionStore = useQuestionStore();
const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production"
    ? `${import.meta.env.VITE_API_BASE_URL}`
    : `${import.meta.env.VITE_API_BASE_URL}`;

const OV = ref(undefined);
const session = ref(undefined);
const mySession = ref(store.loginUserInfo.sub);
const mainStreamManager = ref(undefined);
const publisher = ref(undefined);
const subscribers = ref([]);
const question = ref("");
const leave = ref("refresh");
const showQuestion = ref(false);
// const achievementRate = ref(0)

const change = ref(false);

const isLastSubject = ref(false);

const userId = ref("");
const sec = ref(0);
const remainTime = ref(10);
const categoryName = ref("");
const gakId = ref(0);
const categoryId = ref(0);
const calendarId = ref(0);
const gakOrder = ref(0);
const memoryTime = ref(0);
const done = ref(false);

/* 모달 */
const showModal = ref(false);
const showCountdown = ref(true);
const modalCount = ref(5);

const wantContinue = ref("");

let modalDownCountInterval;

// 모달을 5초 후에 자동으로 닫음
const countdownModalInterval = () => {
  modalDownCountInterval = setInterval(() => {
    modalCount.value--;
    if (modalCount.value === 0) {
      showModal.value = false;
      closeModal();
      modalCount.value = 5;
      if (isLastSubject.value) {
        CountAfterComplete();
        remainTime.value = 0;
        done.value = true;
        dagakStore.stay = true;
        isKeepGoing.value = true;
      } else {
        CountAfterComplete();
        dagakStore.stay = true;
        remainTime.value = 0;
        isKeepGoing.value = true;
        wantContinue.value = "";
      }
    }
  }, 1000);
};

const handleModalResponse = (response) => {
  showModal.value = false;
  if (response) {
    if (isLastSubject.value) {
      // 마지막 과목일 때
      CountAfterComplete();
      remainTime.value = 0;
      done.value = true;
      dagakStore.stay = true;
      isKeepGoing.value = true;
    } else {
      // 뒤에 과목이 남아있을 때
      done.value = false;
      remainRoom();
    }
  } else {
    done.value = false;
    if (!isLastSubject.value) changeRoom();
    else {
      dagakStore.stay = false;
      leaveStudyRoom();
    }
  }
};

const closeModal = () => {
  showModal.value = false;
  clearInterval(modalDownCountInterval);
};

watch(
  showModal,
  (newValue) => {
    if (newValue) {
      countdownModalInterval();
    } else {
      clearInterval(modalDownCountInterval);
    }
  }
  // if (newValue && modalCount.value <= 0) {
  //   showCountdown.value = false
  // }
);

// setInterval(() => sec.value +=1, 1000)
// setInterval(() => remainTime.value -=1, 1000)

const modifyMemoryTimeAndLeave = async function () {
  const body = {
    sign: "modifyMemoryTime",
    gakId: String(gakId.value),
    memoryTime: sec.value - memoryTime.value,
    categoryId: String(categoryId.value),
    calendarId: String(calendarId.value),
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      if (res.data.code === 1000) {
        //성공
        //나갑니다
      } else {
        // alert('저런,,,')
      }
    });
};

const modifyMemoryTime = async function (subject) {
  const body = {
    sign: "modifyMemoryTime",
    gakId: String(gakId.value),
    memoryTime: sec.value - memoryTime.value,
    categoryId: String(categoryId.value),
    calendarId: String(calendarId.value),
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      if (res.data.code === 1000) {
        //성공

        store.loginUserInfo.sub = subject;
        leaveSession().then(() => {
          change.value = true;
          joinSession();
        });
      } else {
        // alert('저런,,,');
      }
    });
  // 순공 시간 업데이트

  await axios
    .get(`${import.meta.env.VITE_API_BASE_URL}/dagak/enterRoomGetGakToStudy`)
    .then((res) => {
      const result = res.data.result;
      // result : gakId, totalTime, calendarId, memoryTime, categoryId, userId, categoryName, gakOrder
      // 그에 따른 categoryId로 방 이동 바랍니다.
      categoryId.value = result.categoryId;
      calendarId.value = result.calendarId;
      gakId.value = result.gakId;
      userId.value = result.userId;
      gakOrder.value = result.gakOrder + 1;
      memoryTime.value = result.memoryTime;
      store.loginUserInfo.sub = result.categoryName;
      alert(result.categoryName + "방에 입장합니다.");
      categoryName.value = result.categoryName;
      const achievementRate = result.memoryTime / result.totalTime;
      remainTime.value = result.requiredStudyTime;

      store.achievementRate = Math.floor(achievementRate * 100);
      sec.value = result.memoryTime; // 공부했던 시간.
    });
  startCount();
};

let countDownInterval;
let countUpInterval;

const togglePause = () => {
  if (isPause.value) {
    alert("공부를 다시 시작합니다.");
  } else {
    alert("공부를 중지합니다.");
  }

  isPause.value = !isPause.value;

  if (isPause.value) {
    if (!isStudyTimeDone.value) {
      clearInterval(countDownInterval);
      clearInterval(countUpInterval);
    } else {
      clearInterval(countUpIntervalAfterComplete);
    }
  } else {
    if (!isStudyTimeDone.value) {
      startCount();
    } else {
      CountAfterComplete();
    }
  }
};

const toggleQuestion = () => {
  showQuestion.value = !showQuestion.value;
};

const isStudyTimeDone = ref(false);
const isKeepGoing = ref(false);

const changeRoomAfterWait = () => {
  if (!done.value) {
    dagakStore.stay = false;
    isKeepGoing.value = false;
    clearInterval(countUpIntervalAfterComplete);
    modifyMemoryTime(
      dagakStore.categoryNameToStudy.value[gakOrder.value].replace(/["']/g, "")
    );
  } else {
    leaveStudyRoom();
  }
};

const startCount = () => {
  countUpInterval = setInterval(() => {
    // 공부한 시간 증가
    sec.value++;
  }, 1000);

  countDownInterval = setInterval(() => {
    remainTime.value--;

    if (remainTime.value <= 0) {
      isStudyTimeDone.value = true;
      clearInterval(countDownInterval);
      clearInterval(countUpInterval);
      // 다음 과목이 있는지 없는지에 따라, 나가거나, 방에 남아있거나, 방 이동 바랍니다.
      if (!isKeepGoing.value) {
        if (gakOrder.value == Object.keys(dagakStore.categoryNameToStudy.value).length) {
          showModal.value = true;
          isLastSubject.value = true;
          // const continueCount = confirm('\n마지막 공부가 끝났습니다.\n 계속 공부하시겠습니까?')
          if (continueCount) {
            // 방 이동 안 함
            CountAfterComplete();
            remainTime.value = 0;
            done.value = true;
            dagakStore.stay = true;
            isKeepGoing.value = true;
          } else {
            // 퇴장함.
            dagakStore.stay = false;
            leaveStudyRoom();
          }
        } else {
          showModal.value = true;
          // const continueCount = confirm(
          //   categoryName.value +
          //     '공부가 끝났습니다.\n[' +
          //     dagakStore.categoryNameToStudy.value[gakOrder.value].replace(/["']/g, '') +
          //     ']방으로 이동 하시겠습니까?'
          // )
          if (wantContinue.value == "yes") {
            // 방 이동 안 함
            CountAfterComplete();
            dagakStore.stay = true;
            remainTime.value = 0;
            isKeepGoing.value = true;
            wantContinue.value = "";
          } else if (wantContinue.value == "no") {
            // 방 이동 함
            // alert('이동하겠습니다.')
            dagakStore.stay = false;
            wantContinue.value = "";
            modifyMemoryTime(
              dagakStore.categoryNameToStudy.value[gakOrder.value].replace(/["']/g, "")
            );
          }
        }
      }
    }
  }, 1000);
};

const remainRoom = () => {
  CountAfterComplete();
  dagakStore.stay = true;
  remainTime.value = 0;
  isKeepGoing.value = true;
  wantContinue.value = "";
  done.value = false;
};

const changeRoom = () => {
  dagakStore.stay = false;
  wantContinue.value = "";
  modifyMemoryTime(
    dagakStore.categoryNameToStudy.value[gakOrder.value].replace(/["']/g, "")
  );
};

let countUpIntervalAfterComplete;

const CountAfterComplete = () => {
  countUpIntervalAfterComplete = setInterval(() => {
    // 공부한 시간 증가
    sec.value++;
  }, 1000);
};

onBeforeMount(async () => {
  await axios
    .get(`${import.meta.env.VITE_API_BASE_URL}/dagak/enterRoomGetGakToStudy`)
    .then((res) => {
      const result = res.data.result;
      // result : gakId, totalTime, calendarId, memoryTime, categoryId, userId, categoryName, gakOrder
      // 그에 따른 categoryId로 방 이동 바랍니다.
      categoryId.value = result.categoryId;
      calendarId.value = result.calendarId;
      gakId.value = result.gakId;
      userId.value = result.userId;
      gakOrder.value = result.gakOrder;
      memoryTime.value = result.memoryTime;
      store.loginUserInfo.sub = result.categoryName;
      done.value = false;

      alert(result.categoryName + "방에 입장합니다.");
      categoryName.value = result.categoryName;
      const achievementRate = result.memoryTime / result.totalTime;
      remainTime.value = result.requiredStudyTime;

      store.achievementRate = Math.floor(achievementRate * 100);
      sec.value = result.memoryTime; // 공부했던 시간.
    });

  // TODO : redis에 저장된 질문/ 답변을 불러와서, QnAListView에 뿌려주기
  console.log("studyRoom onBeforeMount!!!!!!!!!!!!!!!!!!");
  const body = {
    sign: "getSessionQnA",
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}room`, body)
    .then((res) => {
      {
        // alert('session 질문(redis) 가져오기 : ', res.data.result.questionVOList)
        // console.log('session 질문(redis) 가져오기 : ', res.data.result.questionVOList)
        const subQuestions = res.data.result.questionVOList;
        if (subQuestions) {
          subQuestions.forEach(async (element) => {
            // alert('data : ' + element)
            await questionStore.setQuestion(element);
          });
          console.log("question 제발 : " + question.value);
        }
      }
    })
    .catch((e) => {
      alert(e);
      console.log("session 질문(redis) 가져오기 실패!!!!!!!!!!! ");
    });
});

// 플래그

// 방 입장
const enterRoom = async (sessionId) => {
  let token = null;
  if (change.value == true) {
    token = await changeSession(sessionId);
    change.value = false;
  } else {
    token = await createSession(sessionId);
  }
  return token;
};

// 과목 변경
const changeSession = async () => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + "room",
    {
      sign: "changeSession",
      userId: store.myUserName,
      sessionName: store.loginUserInfo.sub,
      videoCodec: "VP8",
    },
    {
      headers: { "Content-Type": "application/json" },
    }
  );
  mySession.value = response.data.result.session;
  // store.loginUserInfo.sub = response.data.result.session;
  return response.data.result.token;
};

console.log("구독자들: ", subscribers.value);
console.log("STORE USER  :  ", store.loginUser);
// 초기 데이터(계정 세션 아이디, 계정 이름)
const myUserName = ref(store.myUserName);

// 방 생성
const createSession = async () => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + "room",
    {
      sign: "enterRandomroom",
      userId: store.myUserName,
      sessionName: store.loginUserInfo.sub,
      videoCodec: "VP8",
    },
    {
      headers: { "Content-Type": "application/json" },
    }
  );
  console.log(response.data.result.session);
  mySession.value = response.data.result.session;
  // store.loginUserInfo.sub = response.data.result.session;
  return response.data.result.token;
};

const askQuestion = async () => {
  console.log(mySession.value + "에서 질문합니다! ");
  const response = await axios.post(
    APPLICATION_SERVER_URL + "room",
    { sign: "askQuestion", session: mySession.value, data: question.value },
    {
      headers: { "Content-Type": "application/json" },
    }
  );
  return response.data.result;
};

const answerQuestion = async () => {
  console.log(mySession.value + "에서 답변합니다! ");
  const response = await axios.post(
    APPLICATION_SERVER_URL + "room",
    { sign: "answerQuestion", session: mySession.value, data: question.value },
    {
      headers: { "Content-Type": "application/json" },
    }
  );
  return response.data.result;
};

const joinSession = () => {
  OV.value = new OpenVidu();
  session.value = OV.value.initSession();

  session.value.on("signal:question", (stream) => {
    alert("질문이 들어왔습니다!");

    console.log("질문 내용:" + stream.data);

    const data = JSON.parse(stream.data);
    console.log("질문 stream : " + data);

    questionStore.setQuestion(data);
  });

  session.value.on("signal:answer", (stream) => {
    // alert('답변이 달렸습니다!')
    // console.log('답변 내용:' + stream.data)

    const data = JSON.parse(stream.data);
    // console.log('질문Id : ' + data.questionId)
    // console.log('답변 내용 : ' + data.data)

    questionStore.setAnswer(data.questionId, data);
  });

  session.value.on("streamCreated", ({ stream }) => {
    const subscriber = session.value.subscribe(stream);
    console.log("subscribers: " + subscriber.value);
    subscribers.value.push(subscriber);
  });

  session.value.on("streamDestroyed", ({ stream }) => {
    // const updatedSubscribers = subscribers.value.filter(sub => sub !== stream.streamManager);
    // subscribers.value = updatedSubscribers;
    const index = subscribers.value.indexOf(stream.streamManager, 0);
    if (index >= 0) {
      subscribers.value.splice(index, 1);
    }
  });

  session.value.on("exception", (exception) => {
    console.warn(exception);
    if (exception.name == "NO_STREAM_PLAYING_EVENT") {
      subscribers.value.forEach((element) => {
        console.log("tetete: ", element.stream);
        if (element.stream.streamId == exception.origin.stream.streamId) {
          subscribers.value.pop(element);
        }
      });
    }
  });

  enterRoom(store.loginUserInfo.sub).then((token) => {
    console.log("token" + token);
    store.studyRoomSessionToken = token;
    session.value
      .connect(token, store.myUserName)
      .then(() => {
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
        store.isInSession = true;
      })
      .catch((error) => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        );
      });
  });

  // window.addEventListener("beforeunload", leaveSession(false));
};

const leaveStudyRoom = async () => {
  alert("나가기 버튼을 눌렀습니다.");
  leave.value = "leave";
  await leaveSession();
  const body = {
    sign: "modifyMemoryTime",
    gakId: String(gakId.value),
    memoryTime: sec.value - memoryTime.value,
    categoryId: String(categoryId.value),
    calendarId: String(calendarId.value),
  };
  await axios
    .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      if (res.data.code === 1000) {
        alert("순공 시간 저장 성공!!");
      } else {
        alert("저런,,,");
      }
    });
  router.push("/");
};

const leaveSession = async () => {
  if (leave.value == "leave") alert("의도적으로 나갑니다");
  alert("나갑니다.");
  if (session.value) session.value.disconnect();

  session.value = undefined;
  mainStreamManager.value = undefined;
  publisher.value = undefined;
  subscribers.value = [];
  OV.value = undefined;

  const response = await axios
    .post(
      APPLICATION_SERVER_URL + "room",
      { sign: "leaveSession", leave: leave.value },
      {
        headers: { "Content-Type": "application/json" },
      }
    )
    .then(() => {
      alert("퇴실완료.");
    });

  // window.removeEventListener("beforeunload", leaveSession(true));
};

// const updateMainVideoStreamManager = (stream) => {
//   if (mainStreamManager.value === stream) return
//   mainStreamManager.value = stream
// }

const updateMainVideoStreamManager = (stream) => {
  mainStreamManager.value = stream;
};

const video13 = ref(null);

const showRate = ref(true);
const isPause = ref(false);

const toggleRate = () => {
  showRate.value = !showRate.value;
};

// const toggleQuestion = () => {
//   showQuestion.value = !showQuestion.value
// }

const toggleMute = (video) => {
  if (video && video.value instanceof HTMLVideoElement) {
    video.value.muted = !video.value.muted;
  }
};

onMounted(() => {
  done.value = false;
  dagakStore.stay = false;
  leaveSession().then(() => {
    joinSession();
  });
  startCount();
});

onBeforeUnmount(() => {
  alert("스터디룸에서 다른 페이지로 라우팅!");
  clearInterval(countUpInterval);
  clearInterval(countDownInterval);
  leaveSession();
});

console.log("구독자들: ", subscribers.length);
console.log("구독자들: ", subscribers.value.length);
</script>

<style lang="scss" scoped>
.room {
  flex-direction: column;
  height: 60%;
  width: 70%;
  height: 100%;
  margin-left: 12px;
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
  color: black;
  justify-content: space-around;
  height: 100px;
  position: relative;
  top: 80px;
}

.nowname {
  font-size: 40px;
  text-align: left;
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

.containers {
  width: 100%;
  height: 100%;
  display: flex;
  margin-top: 100px;
}

.video-players {
  display: flex;
  flex-wrap: wrap;
  box-sizing: border-box;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border: black solid 2px;
}

.video-player-1 {
  flex: 4;
}

.video-player-2 {
  flex: 4;
  background-color: white;
  display: flex;
  flex-wrap: wrap;
}
.videog2 {
  width: 100%;
  border: 5px white solid;
  box-sizing: border-box;
  flex-grow: 1;
}

.videog3 {
  width: 50%;
  border: 5px white solid;
  box-sizing: border-box;
}

.videog4 {
  width: 50%;
  border: 5px white solid;
  box-sizing: border-box;
  flex-direction: row;
}

.videog5 {
  width: 50%;
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
  width: 100%;
  display: flex;
  border: 5px white solid;
  box-sizing: border-box;
  object-fit: cover;
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

.div2 {
  box-shadow: -7px 0 0 0 black, 2px 0 0 0 black, 0 -7px 0 0 black, 0 2px 0 0 black;
}

.div3 {
  // margin: 0.5em auto;
  box-shadow: -4px 0 0 0 black, 4px 0 0 0 black, 0 -4px 0 0 black, 0 4px 0 0 black;
}

/* 모달 스타일 */
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.modal-header {
  display: flex;
  justify-content: space-between;
}

.close {
  color: #aaa;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.modal-buttons {
  margin-top: 10px;
}

.modal-buttons button {
  margin-right: 10px;
}
</style>
