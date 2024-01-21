<template>
  <div class="ranking">
    <div>
      <p class="title">모꼬지 순위</p>
    </div>
    <!-- <div class="top-ranker">
      <p>{{ topRankingData.rank }}위 {{ topRankingData.name }} {{ topRankingData.score }}</p>
    </div> -->
    <table>
      <thead>
        <tr>
          <th>38위</th>
          <th>mory</th>
          <th>23445</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in rankingData.slice(0, 10)" :key="index">
          <td>{{ index + 1 }}위</td>
          <td>{{ item.name }}</td>
          <td>{{ item.score }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
const topRankingData = { rank: 1, name: 'dory', score: 29348 };

const rankingData = [
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  { name: 'dory', score: 29348 },
  // Add other ranking data as needed
];
</script>

<style lang="scss" scoped>
.title {
  font-size: 20px;
  font-weight: 700;
}

.ranking {
  font-size: 10px;
  border: 4px black dashed;
  border-radius: 16px;
  padding: 50px;
  background-color: whitesmoke;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 40%;
  line-height: 0.5;
}

.top-ranker {
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid black;
  padding: 8px;
  text-align: center;
}
</style>
