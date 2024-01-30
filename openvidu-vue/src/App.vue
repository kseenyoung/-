<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="img-div">
        <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success mr-3" @click="joinSession()">
              Join!
            </button>
            <button class="btn btn-lg btn-success" @click="loginSession()">
              Login!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div>
      <div id="video-container" class="col-md-6">
        <user-video
          :stream-manager="publisher"
          @click.native="updateMainVideoStreamManager(publisher)"
        />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo";

axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production"
    ? ""
    : "https://localhost:8080/dagak/";

export default {
  name: "App",

  components: {
    UserVideo,
  },

  data() {
    return {
      OV: undefined,
      OVMy: undefined, // 내 개인 방
      session: undefined,
      mySession: undefined,
      mainStreamManager: undefined,
      mainStreamManagerMySession : undefined,
      publisher: undefined,
      publisherMySession : undefined,
      subscribers: [],

      // 초기 데이터
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
    };
  },

  methods: {
    loginSession() { // 로그인 발생 이벤트
      this.OVMy = new OpenVidu();
      // 전체 참여 세션
      this.mySession = this.OVMy.initSession();
      this.mySession.on("signal:login", async( stream ) => {  // 로그인 시그널 수신
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

      this.mySession.on("signal:login-callBack", ({ stream }) => {
        console.log("[콜백] 친구 ", stream, "님이 로그인했습니다.");
        alert("콜백이 왔어요");
      });

      this.mySession.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.enterMyRoom().then((token) => {
        console.log("나의 방 토큰:",token)

        this.mySession
          .connect(token, { clientData:this.myUserName })
          .then(() => {
            let publisherMySession = this.OVMy.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: false, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManagerMySession = publisherMySession;
            this.publisherMySession = publisherMySession;

            // --- 6) Publish your stream ---;
            this.mySession.publish(this.publisherMySession);
            console.log("mySession에 로그인했습니다.")
            alert("로그인에 성공했습니다.")
          })
          .catch((error) => {
            console.log(
              "다음 세션에 로그인하는데 오류가 발생했습니다!:",
              error.code,
              error.message
            );
          });
      })
    },


    joinSession() {
      this.OV = new OpenVidu();
      this.session = this.OV.initSession();
      // --- 3) Specify the actions when events take place in the session ---

      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.enterRoom(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            console.log("token: " + token);
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- 6) Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      // Empty all properties...
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */

    async enterRoom(mySessionId) {
      // 세션 입장
      let token = await this.createSession(mySessionId);
      return token;
    },

    async createSession(sessionId) { // 세션 생성
      const response = await axios.post(
        APPLICATION_SERVER_URL + "room",
        { sign: "enterRandomroom", sessionName: sessionId, videoCodec: "VP8" },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      
      return response.data.data;
    },

    async enterMyRoom() {
      let token = await this.createMyRoom();
      return token;
    },

    async createMyRoom() {
      const response = await axios.post(
        APPLICATION_SERVER_URL + "room",
        { sign: "enterMyRoom", userId:this.myUserName }, // 내이름으로된 세션을 생성한다
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data.data;
    },
  },
};
</script>
