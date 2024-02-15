<template>
  <div class="ranking">
    <div>
      <p class="title">인기 스터디룸</p>
    </div>
    <table>
      <tbody>
        <tr v-for="(item, index) in studyRoomRanking.slice(0, 10)" :key="index">
          <td>{{ index + 1 }}위</td>
          <td>{{ subjectMapping(item.name) }}</td>
          <td>{{ item.count }}명</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { subjectMapping } from '@/utils/subjectMapping';

import axios from 'axios';
const studyRoomRanking = ref([]);
const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production'
    ? `${import.meta.env.VITE_API_BASE_URL}`
    : `${import.meta.env.VITE_API_BASE_URL}`;

const getStudyRoomRanking = async () => {
  console.log('call getStudyRoomRanking');
  await axios
    .post(
      APPLICATION_SERVER_URL + 'room',
      { sign: 'getSessionRanking' },
      {
        headers: { 'Content-Type': 'application/json' },
      },
    )
    .then((res) => {
      studyRoomRanking.value = res.data.result;
      console.log('랭킹 정보:' + res.data.result[1].name);
    });
};

onMounted(() => getStudyRoomRanking());

const topRankingData = { rank: 1, name: 'dory', score: 29348 };

const rankingData = [
  { name: 'SQLD', score: 59 },
  { name: '정보처리기사', score: 40 },
  { name: '고등수학', score: 22 },
  { name: '중등국어', score: 20 },
  { name: '고등화학1', score: 18 },
  { name: '고등화학2', score: 10 },
  { name: 'JAVA', score: 3 },
  { name: '한국사자격증시험', score: 2 },
  { name: '고등물리1', score: 1 },
  { name: '중등수학', score: 1 },
  // Add other ranking data as needed
];
</script>

<style lang="scss" scoped>
.title {
  font-size: 1.3rem;
  font-weight: 700;
}

.ranking {
  margin-top: 16%;
  font-size: 1.1rem;
  color: black;
  padding: 50px;
  width: 60%;
  line-height: 0.8;
}

.top-ranker {
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 10px;
  text-align: center;
}
</style>
