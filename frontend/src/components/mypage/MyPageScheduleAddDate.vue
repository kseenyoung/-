<template>
  <div>스케줄에 넣기</div>
  <button class="btn common-btn" @click="goToCalander">캘린더로</button>
  <button class="btn common-btn" @click="addDagakDate">추가</button>
  <div>{{ selectDagak }}</div>
  <div class="dagak-list-wrapper">
    <Datepicker
      v-model="dates"
      multi-dates
      locale="ko"
      weekStart="0"
      placeholder="YYYY-MM-DD"
      year-first
      :enable-time-picker="false"
    />
    <div
      class="dagak-detail-wrapper common-pointer"
      v-for="dagak in dagakList"
      :key="dagak.dagakId"
      @click="addDagakList(dagak.dagakId)"
    >
      <img src="@/assets/img/mypage/hexagon_thin.png" class="dagak-figure" />
      {{ dagak.dagakId }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import axios from 'axios';

const router = useRouter();

const dagakList = ref([]);
const selectDagak = ref('');
const dates = ref();

onMounted(() => {
  getAllDagakList();
});

//전체 다각 목록 불러오기
const getAllDagakList = function () {
  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllDagakList`)
    .then((res) => {
      dagakList.value = res.data.result;
    });
};

//선택한 다각id 저장
const addDagakList = function (id) {
  selectDagak.value = id;
};

//캘린더에 다각 추가
const addDagakDate = function () {
  if (selectDagak.value === '') {
    alert('다각을 선택해주세요.');
  } else if (dates.value == null) {
    alert('날짜를 선택해주세요.');
  } else {
    //날짜 배열 형태로 변환
    const formattedDates = dates.value.map((date) => [
      date.getFullYear(),
      date.getMonth() + 1,
      date.getDate(),
    ]);
    const body = {
      sign: 'addDagakDate',
      dagakId: String(selectDagak.value),
      calendarDate: formattedDates,
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //캘린더 추가 성공
          router.push({
            name: 'myPageSchedule',
          });
        } else {
          alert('실패했습니다.');
        }
      });
  }
};

//뒤로가기(캘린더로)
const goToCalander = function () {
  router.go(-1);
};
</script>

<style lang="scss" scoped>
.dagak-list-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  .dagak-detail-wrapper {
    border: 1px solid black;
    .dagak-figure {
      width: 78px;
    }
  }
}
</style>
