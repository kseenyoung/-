<template>
  <div class="containers">
    <div class="achievement">
      <div class="rate div2">
        <p class="titletag" style="font-weight: bold; font-size: 30px">공부시간</p>
        <div class="div4 studytime">{{ convertedTime }}</div>
        <hr />
        <p class="titletag"><b>달성률 :</b> {{ store.achievementRate }} %</p>
        <div><b>과목명:</b> {{ subjectMapping(categoryName) }}</div>
        <div><b>남은시간 : </b> {{ convertedRemainTime }}</div>
        <div><b>다각이름:</b>{{ dagakName }}</div>

        <div class="dagak">
          <DagakImg2 :gak-length="gaksToStudy.length" />
        </div>
        <br />

        <div class="ratedetail" style="padding-bottom: 20px">
          {{ categoryToStudy }}
          <div class="ratedetail">
            <ul class="list">
              <li v-for="(gak, index) in gaksToStudy" :key="index">
                {{ subjectMapping(categoryNameList[gak.categoryId - 1].categoryName) }} :
                {{ getStatus(index) }}
              </li>
            </ul>
          </div>
        </div>
        <button
          type="button"
          class="div3 questiontoggle position-relative"
          style="margin-left: 10%; margin-right: 10%"
          @click="toggleQuestion"
        >
          질문하기 ✋
          <span
            class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-danger"
          >
            {{ questionBadge }}
            <span class="visually-hidden">unread messages</span>
          </span>
        </button>
        <button
          class="div3 closebtn"
          @click="leaveStudyRoom"
          style="background-color: red; color: white"
        >
          나가기 🚪
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, onMounted } from 'vue'
import Dagak from '@/components/dagak/Dagak.vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { useDagakStore } from '@/stores/dagak'
import { useCategoryStore } from '@/stores/category'
import { useQuestionStore } from '@/stores/qustion'
import DagakImg2 from '@/components/dagak/DagakImg2.vue'
import { subjectMapping } from '@/utils/subjectMapping'

const router = useRouter()
const leave = ref('refresh')
const store = useUserStore()
const dagakStore = useDagakStore()
const categoryStore = useCategoryStore()
const todayDagak = dagakStore.todayDagak
const showQuestion = ref(true)
const questionStore = useQuestionStore()
const questionBadge = computed(() => {
  return questionStore.question.length
})

const props = defineProps({
  sec: Number,
  remainTime: Number,
  categoryName: String,
  gakOrder: Number
})

const gaksToStudy = ref(todayDagak.gaks)
const categoryNameList = ref(categoryStore.categoryList)
const dagakName = ref(todayDagak.dagakName)

const getStatus = (index) => {
  if (index < props.gakOrder) {
    return '완료 ✔'
  } else if (index === props.gakOrder) {
    return '진행 중'
  } else {
    return '진행 예정'
  }
}

const emit = defineEmits(['leave-study-room', 'toggle-question'])
const leaveStudyRoom = () => {
  emit('leave-study-room')
}
const toggleQuestion = () => {
  emit('toggle-question')
}

const convertTime = (seconds) => {
  let hour, min, sec

  hour = parseInt(seconds / 3600)
  min = parseInt((seconds % 3600) / 60)
  sec = seconds % 60

  if (hour.toString().length == 1) hour = '0' + hour
  if (min.toString().length == 1) min = '0' + min
  if (sec.toString().length == 1) sec = '0' + sec

  return `${hour}:${min}:${sec}`
}

const convertedTime = ref(convertTime(props.sec))
const convertedRemainTime = ref(convertTime(props.remainTime))

watch(props, (newTime) => {
  convertedTime.value = convertTime(newTime.sec)
})

watch(props, (newTime) => {
  convertedRemainTime.value = convertTime(newTime.remainTime)
})
</script>

<style lang="scss" scoped>
.dagak {
  margin-bottom: 5%;
  text-align: center;
  position: relative;
  // z-index: 1;
  height: 150px;
  width: 100%;
  padding: 20px 0px 10px 20px;
}
.dagakname {
  padding-right: 25px;
  text-decoration: underline;
}

.containers {
  width: 100%;
  display: flex;
}

.rate {
  padding: 10px;
  border: black 2px solid;
  background-color: white;
  width: 320px;
  height: fit-content;
  position: fixed;
  right: 15px;
  bottom: 3%;
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

.questiontoggle:hover,
.ratetoggle:hover,
.closebtn:hover {
  background-color: white;
  /* border-bottom: 2px solid white;*/
}

.div2 {
  margin: 0.5em auto;
  box-shadow:
    -7px 0 0 0 black,
    2px 0 0 0 black,
    0 -7px 0 0 black,
    0 2px 0 0 black;
}

.div3 {
  margin: 0.5em auto;
  box-shadow:
    -2px 0 0 0 black,
    2px 0 0 0 black,
    0 -2px 0 0 black,
    0 2px 0 0 black;
}

.div4 {
  margin: 0.5em auto;
  box-shadow:
    -4px 0 0 0 black,
    4px 0 0 0 black,
    0 -4px 0 0 black,
    0 4px 0 0 black;
}

.list {
  list-style: none;
  padding: 0px 0px 0px 0px;
}
</style>
