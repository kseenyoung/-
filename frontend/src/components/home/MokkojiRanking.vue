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
import { useRankStore } from '@/stores/rank';
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue'

const store = useUserStore();

const topRankingData = { rank: 1, name: 'dory', score: 29348 };

const rankingData = [
  { name: '모꼬', score: 29348 },
  { name: '지모', score: 29348 },
  { name: '꼬지', score: 29348 },
  { name: '모꼬지', score: 29348 },
  { name: '모', score: 29348 },
  { name: '꼬', score: 29348 },
  { name: '지', score: 29348 },
  { name: '지꼬', score: 29348 },
  { name: '모지', score: 29348 },
  { name: '꼬모', score: 29348 },
  // Add other ranking data as needed
];

const rankstore = useRankStore();

onMounted(() => {
  rankstore.getMokkojiRank();
  console.log("mokkojiRank.value: ", rankstore.mokkojiRank);
  console.log("mokkojiRank.value: ", rankstore.mokkojiRank.value);
  console.log("mokkojiRank.value.mokkojiName: ", rankstore.mokkojiRank.mokkojiName);
});

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
  border: 2px solid black;
  padding: 8px;
  text-align: center;
}
</style>
<!-- 
<template>
  <div>
    <h2>모꼬지 순위</h2>
    <table>
      <thead>
        <tr>
          <th>순위</th>
          <th>모꼬지</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in mokkojiRank" :key="item.id">
          <td>{{ item.rank }}</td>
          <td>{{ item.mokkjiName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue';

const mokkojiRank = ref([]);

onMounted(() => {
  mokkojiRank.value = mokkojiRankProp;
});

const mokkojiRankProp = ref(props.mokkojiRank);
</script>

<style scoped>
/* Add your styling for the table here if needed */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}
</style> -->
<!-- 
<template>
  <div class="ranking">
  <button class="title startbutton" @click="navigateToStudyRoom">모꼬지 순위</button>
    <table>
      <thead>
        <tr>
          <th>순위</th>
          <th>모꼬지</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in rankStore.mokkojiRank" :key="item.mokkoji.mokkojiId">
          <td>{{ item.mokkoji.rank }}</td>
          <td>{{ item.mokkoji.mokkojiName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRankStore } from '@/stores/rank';
const rankStore = useRankStore();

onMounted(async () => {
  await rankStore.getMokkojiRank();
  console.log("mokkojiRank.value: ", rankStore.mokkojiRank);
});

console.log("모꼬지랭크: ", rankStore.mokkojiRank)

</script>



<style lang="scss" scoped>
.title {
  font-size: 20px;
  font-weight: 700;
}

.ranking {
  font-size: 10px;
  // border: 4px black dashed;
  border-radius: 16px;
  padding: 50px;
  background-color: whitesmoke;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 40%;
  line-height: 0.5;
}

.ranking button {
  margin: 5px;
}

.top-ranker {
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 2px dashed black;
  padding: 8px;
  text-align: center;
}
.startbutton {
  // 기존 스타일 유지
  background-color: #639B9D;
  /* 적절한 배경색으로 변경 */
  border: none;
  color: white;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  cursor: pointer;
  border-radius: 8px;
  /* 둥근 모서리 추가 */
  transition: background-color 0.3s;
  /* 부드러운 전환 효과 */

  &:hover {
    background-color: #3E6271;
    /* 마우스 호버 시 색상 변경 */
  }

  span {
    position: relative;
    z-index: 1;
  }

  &:before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    // background: linear-gradient(45deg, #FF8C00, #FFD700); /* 그라데이션 효과 추가 */
    background: linear-gradient(45deg, #00FF7F, #48D1CC);
    /* 그라데이션 효과 추가 */
    z-index: -1;
    border-radius: 8px;
    /* 둥근 모서리에 그라데이션도 적용 */
    opacity: 0;
    transition: opacity 0.3s;
  }

  &:hover:before {
    opacity: 1;
  }
}

</style>  -->