import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser';

axios.defaults.headers.post['Content-Type'] = 'application/json'

export const useUserStore = defineStore('user', () => {
  //로그인 세션 test
  const loginUser = ref('')
  const login = function () {
    const id = ref('ssafy123')
    const sub = ref('SQLD')
    const user = ref({
      id: id.value,
      sub: sub.value
    })

    const userString = JSON.stringify(user.value)
    sessionStorage.setItem('login-user', userString)
    loginUser.value = JSON.parse(sessionStorage.getItem('login-user'))
    alert('로그인 성공')
    loginSession();
    alert('방입장 성공')
  }

  const OVMy = ref(undefined)
  const mySession = ref(undefined)
  const mainStreamManagerMySession = ref(undefined)
  const publisherMySession = ref(undefined)
  const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://localhost:8080/dagak/';
  const myUserName = ref(loginUser.value.id);

  // 계정 방 입장 
  const enterMyRoom = async () => {
    let token = await createMyRoom()
    return token
  }

  // 계정 방 생성
  const createMyRoom = async () => {
    console.log("loginUser : ", loginUser.value.id);
    const response = await axios.post(
      APPLICATION_SERVER_URL + 'room',
      { sign: 'enterMyRoom', userId: loginUser.value.id },
      {
        headers: { 'Content-Type': 'application/json' }
      }
    )
    return response.data.result
  }

  const loginSession = () => {
    OVMy.value = new OpenVidu()
    // 전체 참여 세션
    mySession.value = OVMy.value.initSession()
    mySession.value.on("signal:login", async( stream ) => {  // 로그인 시그널 수신
      console.log(stream.data, "님이 로그인했습니다.");
      alert("친구가 로그인했어요!")

      await axios.post( // 로그인 콜백
      "https://i10a404.p.ssafy.io/openvidu/api/signal",
      {
        session: stream.data,
          type: "signal:login-callBack",
          data: this.myUserName,
        },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU",
        }
      }
    );
    });

    mySession.value.on("signal:login-callBack", ({ stream }) => {
      console.log("[콜백] 친구 ", stream, "님이 로그인했습니다.");
      alert("콜백이 왔어요");
    });

    mySession.value.on('exception', ({ exception }) => {
      console.warn(exception)
    })

    enterMyRoom().then((token) => {
      console.log('나의 방 토큰:', token)

      mySession.value
        .connect(token, { clientData: myUserName.value })
        .then(() => {
          publisherMySession.value = OVMy.value.initPublisher(undefined, {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: false,
            publishVideo: false,
            resolution: '640x480',
            frameRate: 30,
            insertMode: 'APPEND',
            mirror: false
          })

          // Set the main video in the page to display our webcam and store our Publisher
          mainStreamManagerMySession.value = publisherMySession

          // --- 6) Publish your stream ---;
          mySession.value.publish(publisherMySession.value)
          console.log('mySession에 로그인했습니다.')
        })
        .catch((error) => {
          console.log('다음 세션에 로그인하는데 오류가 발생했습니다!:', error.code, error.message)
        })
    })
  }

  return { myUserName, APPLICATION_SERVER_URL, loginUser, login,  OVMy, mySession, mainStreamManagerMySession, publisherMySession, enterMyRoom, createMyRoom, loginSession, }
})
