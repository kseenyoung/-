<template>
  <div class="containers">
    <div class="achievement">
      <div class="rate div2">
        <p class="titletag" style="font-weight: bold; font-size: 30px">공부시간</p>
        <div class="div4 studytime">{{ convertedTime }}</div>
        <hr />
        <p class="titletag"><b>달성률 :</b> {{ store.achievementRate }} %</p>
        <div><b>과목명:</b> {{ categoryName }}</div>
        <div><b>남은시간 : </b> {{ convertedRemainTime }}</div>

        <div class="dagak">
          <DagakImg :gak-length="gaksToStudy.length" />
          다각 이름 : {{ dagakName }}
        </div>
        <br />
        <br />

        <div class="ratedetail" style="padding-bottom: 20px">
          {{ categoryToStudy }}
          <div class="ratedetail">
            <ul class="list">
              <li v-for="(gak, index) in gaksToStudy" :key="index">
                {{ categoryNameList[gak.categoryId - 1].categoryName }} : {{ getStatus(index) }}
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
import DagakImg from '@/components/dagak/DagakImg.vue'

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
  text-align: center;
  position: relative;
  z-index: 1;
  height: 120px;
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

.dagak-list-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  max-width: 710px;
  margin: 0 auto;
  .dagak-detail-wrapper {
    text-align: center;
    border: 1px solid black;
    border-radius: 4px;
    padding: 20px 10px 0px;
    width: 115px;
    min-height: 160px;
    margin: 0px 10px 30px;
    box-shadow: 5px 5px #ccc;
    .dagak-figure {
      width: 78px;
    }
  }
}

.list {
  list-style: none; /* 동글이 제거 */
  padding: 20px 10px 0px;
}
</style>
