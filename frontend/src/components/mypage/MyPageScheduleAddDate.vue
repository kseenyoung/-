<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">스케줄에 추가</div>
    <button class="btn common-btn go-back-btn" @click="goToCalander">
      <i class="bi bi-arrow-90deg-left"></i>
    </button>
  </div>
  <div class="dagak-main-wrapper">
    <div class="dagak-main-title">날짜</div>
    <Datepicker
      v-model="dates"
      multi-dates
      locale="ko"
      weekStart="0"
      placeholder="YYYY-MM-DD"
      year-first
      :enable-time-picker="false"
    />
    <div class="dagak-main-title">다각 목록</div>
    <div class="dagak-list-wrapper" v-if="dagakList.length != 0">
      <div
        class="dagak-detail-wrapper common-pointer"
        v-for="dagak in dagakList"
        :key="dagak.dagakId"
        :class="{
          'dagak-detail-wrapper-clicked': isSelectedDagak(dagak.dagakId),
        }"
        @click="addDagakList(dagak.dagakId)"
      >
        <!-- <img src="@/assets/img/mypage/hexagon_thin.png" class="dagak-figure" /> -->
        <DagakImg :gak-length="dagak.gakLength" />
        <div class="dagak-title">{{ dagak.dagakName }}</div>
      </div>
    </div>
    <div v-else>생성한 다각이 없습니다. 새로 생성해주세요.</div>
  </div>
  <div class="dagak-add-wrapper">
    <button class="btn common-btn add-btn" @click="addDagakDate">
      추가하기
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import axios from 'axios';
import DagakImg from '@/components/dagak/DagakImg.vue';

const router = useRouter();

const dagakList = ref([]);
const selectDagak = ref('');
const dates = ref();

onMounted(() => {
  getAllDagakList();
});

//전체 다각 목록 불러오기
const getAllDagakList = async function () {
  try {
    //전체 다각 목록
    const response = await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}dagak/getAllDagakList`,
    );
    const dagaks = response.data.result;

    //다각의 각 목록
    const gakLengthPromises = dagaks.map((dagak) =>
      axios.get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
        params: { dagakId: dagak.dagakId },
      }),
    );

    const gakLengthResponses = await Promise.all(gakLengthPromises);

    //다각 리스트에 각 개수 데이터 저장
    dagakList.value = dagaks.map((dagak, index) => ({
      ...dagak,
      gakLength: gakLengthResponses[index].data.result.length,
    }));
  } catch (error) {
    console.error('Error:', error);
  }
};

//선택한 다각id 저장
const addDagakList = function (id) {
  if (isSelectedDagak(id)) {
    // 이미 선택된 다각을 누르면 선택 취소
    selectDagak.value = '';
  } else {
    // 선택되지 않은 다각을 누르면 선택
    selectDagak.value = id;
  }
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

//선택된 다각에 css 적용하기 위한 메서드
const isSelectedDagak = (dagakId) => {
  return selectDagak.value === dagakId;
};

//뒤로가기(캘린더로)
const goToCalander = function () {
  router.go(-1);
};
</script>

<style lang="scss" scoped>
.common-mypage-wrapper {
  padding: 30px 50px 10px 50px;
}
.dagak-main-wrapper {
  margin: 0px 50px;
  > div:nth-child(2) {
    margin-bottom: 30px;
  }
  .dagak-main-title {
    font-weight: bold;
    margin-bottom: 5px;
  }
  .dagak-list-wrapper {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    max-width: 710px;
    margin: 0 auto;
    .dagak-detail-wrapper-clicked {
      transform: translateY(-5px);
      background-color: aliceblue;
    }
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
      .dagak-title {
        margin-top: 10px;
      }
    }
  }
}
.dagak-add-wrapper {
  text-align: right;
  margin: 0px 50px 20px;
}
.common-mypage-wrapper {
  .common-mypage-title {
    display: inline-block;
  }
  .go-back-btn {
    position: relative;
    top: -8px;
    left: 10px;
  }
  .add-btn {
    display: block;
  }
}
</style>
