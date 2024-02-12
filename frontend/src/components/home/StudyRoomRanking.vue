<template>
  <div class="ranking">
    <div>
      <p class="title">인기 스터디룸</p>
    </div>
    <table>
      <tbody>
        <tr v-for="(item, index) in rankingData.slice(0, 10)" :key="index">
          <td>{{ index + 1 }}위</td>
          <td>{{ item.name }}</td>
          <td>{{ item.score }}명</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
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
  margin-top: 19%;
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

th, td {
  padding: 10px;
  text-align: center;
}
</style>