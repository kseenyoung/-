<template>
  <div>
    <div class="calendar-container">
      <div class="calendar-header">
        <button @click="prevMonth" class="cal-btn">&lt;</button>
        <span>{{ currentMonth }}</span>
        <button @click="nextMonth" class="cal-btn">&gt;</button>
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
              {{ day.day }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';

// 현재 날짜 정보
const currentDate = ref(new Date());

// 현재 월 표시
const currentMonth = computed(() => {
  return currentDate.value.toLocaleString('default', { month: 'long', year: 'numeric' });
});

// 요일 배열
const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

// 달력 데이터 생성
const weeks = computed(() => {
  const firstDayOfMonth = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth(), 1);
  const lastDayOfMonth = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 0);
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
        const newDate = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth(), date);
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

// 날짜 클릭 시 처리
const selectDate = (day) => {
  if (day.date) {
    console.log('Selected Date:', day.date);
    // 여기에 선택한 날짜에 대한 추가 로직을 넣을 수 있습니다.
  }
};

// 이전 월로 이동
const prevMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1);
};

// 다음 월로 이동
const nextMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1);
};

// 초기화
onMounted(() => {
  // 초기화 로직 추가 가능
});
</script>

<style lang="scss" scoped>
.cal-btn {
  border: none;
  background-color: transparent;
  cursor: pointer;
  font-size: 18px;
  color: #555;
}

.calendar-container {
  background-color: #fff;
  margin: 20px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  margin: 20px;
}

.calendar-header {
  margin-bottom: 20px;
  text-align: center;
}

.calendar-header button {
  font-size: 24px;
  font-weight: bold;
  color: #555;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  text-align: center;
  border: 1px solid #ddd;
}

th {
  background-color: #f5f5f5;
  font-weight: bold;
  color: #555;
  height: 40px; /* 높이 조절 */
}

td {
  cursor: pointer;
  height: 60px; /* 높이 조절 */
}

td:hover {
  background-color: #f0f0f0;
}

span {
  font-size: 24px;
  font-weight: bold;
  color: #555;
  margin: 0 15px;
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
