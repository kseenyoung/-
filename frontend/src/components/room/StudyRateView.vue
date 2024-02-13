<template>
  <div class="containers">
    <div class="achievement">
      <div class="rate">
        <p class="titletag">공부시간</p>
        <div class="studytime">{{ convertedTime }}
        <!-- {{ todayDagak }} -->
        </div>
        <hr />
        <p class="titletag">달성률 : {{ store.achievementRate }} %</p>
        <div>[{{ categoryName }}] 남은 시간 : {{ convertedRemainTime }}</div>
        <div class="dagak">
          <Dagak />
        </div>
        <div class="ratedetail">
          java 마스터 --- <b>140%</b><br />
          Python 마스터 --- <b>75%</b><br />
          C++ 마스터 ---- <b>0%</b>
        </div>
        <button class="questiontoggle" @click="toggleQuestion">질문하기✋</button>
        <button class="closebtn" @click="leaveStudyRoom">나가기🚪</button>
      </div>
      
    </div>
    <div class="QnA">
      <div v-if="showQuestion">
        <QnAListView />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import QnAListView from './QnAListView.vue'
import Dagak from '@/components/dagak/Dagak.vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { useDagakStore } from '@/stores/dagak'


const router = useRouter()
const leave = ref('refresh')
const store = useUserStore()
const dagakStore = useDagakStore()
const todayDagak = dagakStore.todayDagak
const showQuestion = ref(true)
const props = defineProps({
  sec: Number,
  remainTime : Number,
  categoryName : String
})


const  leaveStudyRoom = ()=> {
      // 부모 컴포넌트로 leave-study-room 이벤트를 발생시킵니다.
      this.$emit('leave-study-room')
    }

const convertTime = (seconds) => {
	let hour, min, sec

	hour = parseInt(seconds/3600);
	min = parseInt((seconds%3600)/60);
	sec = seconds%60;

	if (hour.toString().length==1) hour = "0" + hour;
	if (min.toString().length==1) min = "0" + min;
	if (sec.toString().length==1) sec = "0" + sec;

	return `${hour}:${min}:${sec}`;
}

const convertedTime = ref(convertTime(props.sec))
const convertedRemainTime = ref(convertTime(props.remainTime))

watch(props, (newTime) => {
  convertedTime.value = convertTime(newTime.sec);
})

watch(props, (newTime) => {
  convertedRemainTime.value = convertTime(newTime.remainTime);
})

const toggleQuestion = () => {
  showQuestion.value = !showQuestion.value
}

console.log(todayDagak)
</script>

<style lang="scss" scoped>
.dagak {
  text-align: center;
  /* padding: 20px; */
  position: relative;
  z-index: 1;
}

.containers {
  width: 100%;
  display: flex;
  margin-top: 110px;
  
}

.rate {
  padding: 2px;
  // border: 2px solid black;
  background-color: white;
  width: 320px;
  height: fit-content;
  // box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* 그림자 효과 추가 */
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
  right: 0;
  bottom: 0%;
}
</style>
