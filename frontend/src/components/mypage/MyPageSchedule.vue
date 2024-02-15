<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <button @click="goToToday" class="btn common-btn">오늘</button>
      <button @click="prevMonth" class="cal-btn">
        <i class="bi bi-caret-left-square-fill ms-2"></i>
      </button>
      <span class="span-header">{{ currentMonth }}</span>
      <button @click="nextMonth" class="cal-btn">
        <i class="bi bi-caret-right-square-fill me-2"></i>
      </button>
      <button @click="goToMyDagak" class="btn common-btn goto-btn">
        <i class="bi bi-grid"></i>내 다각
      </button>
      <button @click="goToMyAddDate" class="btn common-btn goto-btn">
        <i class="bi bi-calendar-plus"></i>스케줄에 추가
      </button>
    </div>
    <table>
      <thead>
        <tr>
          <th v-for="day in daysOfWeek" :key="day">{{ day }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="week in weeks" :key="week">
          <td v-for="day in week" :key="day.date">
            <span class="span-date" :class="{ 'red-text': isToday(day.date) }">
              {{ day.day }}
            </span>
            <div v-if="day.date" class="dagak-wrapper">
              <div
                v-if="!hasEventsForDate(day.date)"
                @click="goToMyAddDateClick(day.date)"
                class="dagak-goto-add"
              ></div>
              <div
                v-for="event in getEventsForDate(day.date)"
                :key="event.dagakId"
                class="dagak-item"
                data-bs-toggle="modal"
                data-bs-target="#dagakScheduleDetail"
                @click="
                  openModal(
                    event.dagakId,
                    event.calendarDagakId,
                    event.dagakName,
                  )
                "
              >
                <div class="dagak-name">{{ event.dagakName }}</div>
                <DagakImg :gak-length="event.gakLength" />
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div
    class="modal fade"
    id="dagakScheduleDetail"
    tabindex="-1"
    aria-labelledby="dagakScheduleDetail"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="dagakScheduleDetail">
            다각 상세정보
          </h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="modal-body-title">[ {{ selectedDagakName }} ]</div>
          <div class="gak-wrapper">
            <div
              class="gak-detail-wrapper"
              v-for="gak in gakList"
              :key="gak.gakId"
            >
              <div class="gak-detail-order">{{ gak.gakOrder }}.</div>
              <div class="gak-detail-tag">
                {{ subjectMapping(getCategoryName(gak.categoryId)) }}
              </div>
              <div class="gak-detail-time">{{ gak.runningTime / 60 }}분</div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            닫기
          </button>
          <button
            type="button"
            class="btn btn-danger"
            data-bs-dismiss="modal"
            @click="deleteCalendarDagak(selectedScheduleId)"
          >
            스케줄에서 삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/category';
import { useRouter } from 'vue-router';
import axios from 'axios';
import DagakImg from '@/components/dagak/DagakImg.vue';
import { subjectMapping } from '@/utils/subjectMapping';

const categoryStore = useCategoryStore();
const router = useRouter();

const calendarList = ref([]);
const gakList = ref([]);
const selectedDagakId = ref(null);
const selectedScheduleId = ref(null);
const selectedDagakName = ref('');

onMounted(() => {
  getAllCalendarList();
});

//해당 날짜에 이벤트가 있는지 여부를 확인하는 메서드
const hasEventsForDate = (date) => {
  const eventsForDate = getEventsForDate(date);
  return eventsForDate.length > 0;
};

//모든 캘린더 다각 가져오기
// const getAllCalendarList = function () {
//   axios
//     .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllCalendarList`)
//     .then((res) => {
//       calendarList.value = res.data.result;
//     });
// };
const getAllCalendarList = async function () {
  try {
    //전체 캘린더 다각 목록
    const response = await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}dagak/getAllCalendarList`,
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
    calendarList.value = dagaks.map((dagak, index) => ({
      ...dagak,
      gakLength: gakLengthResponses[index].data.result.length,
    }));
  } catch (error) {
    console.error('Error:', error);
  }
};

//다각 날짜 반환
const getEventsForDate = function (date) {
  const eventsForDate = calendarList.value.filter((event) => {
    const [year, month, day] = event.calendarDate;
    return (
      year === date.getFullYear() &&
      month === date.getMonth() + 1 &&
      day === date.getDate()
    );
  });
  return eventsForDate;
};

// 현재 날짜 정보
const currentDate = ref(new Date());

// 현재 월 표시
const currentMonth = computed(() => {
  return currentDate.value.toLocaleString('default', {
    month: 'long',
    year: 'numeric',
  });
});

// 요일 배열
const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

// 달력 데이터 생성
const weeks = computed(() => {
  const firstDayOfMonth = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth(),
    1,
  );
  const lastDayOfMonth = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1,
    0,
  );
  const startDay = firstDayOfMonth.getDay();
  const endDay = lastDayOfMonth.getDate();

  let date = 1;
  const calendar = [];

  for (let i = 0; i < 6; i++) {
    const week = [];
    for (let j = 0; j < 7; j++) {
      if (i === 0 && j < startDay) {
        week.push({ day: '', date: null });
      } else if (date <= endDay) {
        const newDate = new Date(
          currentDate.value.getFullYear(),
          currentDate.value.getMonth(),
          date,
        );
        week.push({ day: date, date: newDate });
        date++;
      } else {
        week.push({ day: '', date: null });
      }
    }
    calendar.push(week);
  }
  return calendar;
});

// 이전 월로 이동
const prevMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() - 1,
    1,
  );
};

// 다음 월로 이동
const nextMonth = () => {
  currentDate.value = new Date(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth() + 1,
    1,
  );
};

// 오늘인지 여부를 확인하는 함수
const isToday = (date) => {
  const today = new Date();
  return (
    date &&
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
};

// 오늘로 이동
const goToToday = () => {
  currentDate.value = new Date(); // 현재 날짜로 설정
};

//클릭 시 다각의 상세정보 불러오기
const openModal = function (id, calId, name) {
  selectedDagakId.value = id;
  selectedScheduleId.value = calId;
  selectedDagakName.value = name;

  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
      params: { dagakId: id },
    })
    .then((res) => {
      //각 목록을 gakOrder 기준으로 오름차순 정렬
      const sortedGakList = res.data.result.sort(
        (a, b) => a.gakOrder - b.gakOrder,
      );
      gakList.value = sortedGakList;
    });
};

//캘린더에서 빼기
const deleteCalendarDagak = function (calId) {
  if (window.confirm('스케줄에서 삭제하시겠습니까?')) {
    const body = {
      sign: 'deleteCalendarDagak',
      calendarDagakId: String(calId),
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //삭제 성공
          getAllCalendarList();
        } else {
          alert('실패했습니다.');
        }
      });
  }
};

//카테고리Id를 카테고리Name으로 반환
const getCategoryName = function (categoryId) {
  const category = categoryStore.categoryList.find(
    (cat) => cat.categoryId === categoryId,
  );
  return category ? category.categoryName : 'Unknown Category';
};

//라우터 이동 메서드
const goToMyDagak = function () {
  router.push({
    name: 'myPageScheduleDagak',
  });
};
const goToMyAddDate = function () {
  router.push({
    name: 'myPageScheduleAddDate',
  });
};
//날짜 클릭 시 -> 날짜 정보 들고 라우터 이동
const goToMyAddDateClick = function (date) {
  //이전 날짜인지 확인
  if (!isPreviousDate(date)) {
    const formattedDate = new Date(
      date.getTime() - date.getTimezoneOffset() * 60000,
    )
      .toISOString()
      .split('T')[0];
    router.push({
      name: 'myPageScheduleAddDate',
      query: {
        selectedDate: formattedDate,
      },
    });
    console.log('이전 날짜');
  }
};

// 이전 날짜 여부 확인 함수
const isPreviousDate = function (date) {
  const today = new Date();
  const startClickableDate = new Date(
    today.getFullYear(),
    today.getMonth(),
    today.getDate(),
  );
  return date < startClickableDate;
};
</script>

<style lang="scss" scoped>
.cal-btn {
  border: none;
  background-color: transparent;
  font-size: 18px;
  color: #555;

  i {
    font-size: 1.5rem;
    color: $color-light-3;
  }
}

.calendar-header {
  margin-bottom: 20px;
  text-align: center;
  .goto-btn {
    margin-right: 5px;
    i {
      position: relative;
      top: -2px;
      left: -3px;
    }
  }
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  height: 95px;
  width: 14.2857%;
}

th {
  text-align: center;
  background-color: #f5f5f5;
  font-weight: bold;
  color: #555;
  height: 30px;
}

td {
  cursor: pointer;
  text-align: right;
  position: relative;

  &:hover {
    background-color: #fcfcfc;
  }

  .span-date {
    position: absolute;
    top: 0px;
    left: 5px;
    padding: 0px 3px;
  }
}

.span-header {
  font-size: 24px;
  font-weight: bold;
  color: #555;
  margin: 0 10px;
}

.red-text {
  color: white;
  background: linear-gradient(to top, $color-light-3 8%, transparent 8%);
  background-color: $color-light-3;
  border-radius: 3px;
  padding-bottom: 2px;
  font-weight: bold;
}

.dagak-wrapper {
  height: 100%;
  .dagak-name {
    position: relative;
    top: 77px;
  }
  .dagak-goto-add {
    height: 100%;
  }
  > div {
    font-size: 1rem;
    height: 58px; //20px
    text-align: center;
    position: relative; //추가
    top: -15px; //추가
  }
  .dagak-figure {
    position: absolute;
    width: 78px;
    top: 12px;
    left: 18px;
  }
}

.divider {
  margin: 15px 0;
  border-top: 1px solid #ddd;
}

.schedule-event {
  background-color: #3498db;
  color: #fff;
  padding: 5px;
  border-radius: 5px;
  margin-bottom: 5px;
  display: inline-block;
}

.modal-body-title {
  text-align: center;
  font-weight: bold;
  font-size: 1.3rem;
}
.gak-wrapper {
  font-size: 1.3rem;
  .gak-detail-wrapper {
    margin: 20px 0px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    .gak-detail-order {
      width: 30px;
      height: 30px;
      text-align: center;
    }
    .gak-detail-tag {
      margin-left: 5px;
    }
    .gak-detail-time {
      margin-left: 10px;
    }
  }
}
</style>
