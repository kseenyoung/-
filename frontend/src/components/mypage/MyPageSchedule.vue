<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <button @click="prevMonth" class="cal-btn">
        <i class="bi bi-caret-left-square-fill"></i>
      </button>
      <span class="span-header">{{ currentMonth }}</span>
      <button @click="nextMonth" class="cal-btn">
        <i class="bi bi-caret-right-square-fill"></i>
      </button>
      <button @click="goToToday" class="btn common-btn">오늘</button>
      <button @click="goToMyDagak" class="btn common-btn">내 다각</button>
      <button @click="goToMyAddDate" class="btn common-btn">
        스케줄에 추가하기
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
          <td v-for="day in week" :key="day.date" @click="selectDate(day)">
            <span class="span-date" :class="{ 'red-text': isToday(day.date) }">
              {{ day.day }}
            </span>
            <div v-if="day.date" class="dagak-wrapper">
              <div
                v-for="event in getEventsForDate(day.date)"
                :key="event.dagakId"
                class="dagak-item"
              >
                {{ event.dagakId }}
                {{ event.calendarDagakId }}
                <img
                  v-if="event.dagakId"
                  src="@/assets/img/mypage/hexagon_thin.png"
                  class="dagak-figure"
                />
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/category';
import { useRouter } from 'vue-router';
import axios from 'axios';

const categoryStore = useCategoryStore();
const router = useRouter();

const calendarList = ref([]);

onMounted(() => {
  getAllCalendarList();
});

//모든 캘린더 다각 가져오기
const getAllCalendarList = function () {
  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllCalendarList`)
    .then((res) => {
      console.log(res);
      calendarList.value = res.data.result;
    });
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

// Get todos for a specific date
const getTodos = (date) => {
  const dateString = date.toDateString();
  return todoList.value[dateString] || [];
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

// 다각 배열
const todoList = ref({
  // 'Wed Jan 17 2024': ['일정1']
});

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

// 날짜 선택 후 일정 추가
// const selectDate = (day) => {
//   if (day.date) {
//     console.log('Selected Date:', day.date);
//     const newTodo = prompt('Enter Todo:');
//     if (newTodo) {
//       addTodo(day.date, newTodo);
//     }
//     console.log(todoList.value);
//   }
// };

// 배열에 일정 추가
// const addTodo = (date, todo) => {
//   const dateString = date.toDateString();
//   if (!todoList.value[dateString]) {
//     todoList.value[dateString] = [];
//   }
//   todoList.value[dateString].push(todo);
// };
</script>

<style lang="scss" scoped>
.cal-btn {
  border: none;
  background-color: transparent;
  font-size: 18px;
  color: #555;

  i {
    font-size: 1.5rem;
    color: #ff6347;
  }
}

.calendar-header {
  margin-bottom: 20px;
  text-align: center;
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
  margin: 0 15px;
}

.red-text {
  color: white;
  background: linear-gradient(to top, #ff6347 8%, transparent 8%);
  background-color: #ff6347;
  border-radius: 3px;
  padding-bottom: 2px;
}

.dagak-wrapper {
  > div {
    font-size: 1rem;
    height: 20px;
    text-align: center;
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
</style>
