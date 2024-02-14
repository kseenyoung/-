
<template>
  <div class="ranking">
    <div>
      <p class="title">나의 순위</p>
    </div>

    <table>
      <tbody>
        <tr v-for="item in userRank" :key="item.userId">
          <td>{{ item.userRank }}위</td>
          <td>{{ item.userName }}</td>
          <td>{{ item.userTotalStudyTime }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useRankStore } from '@/stores/rank';
import { ref, onMounted } from 'vue';

const rankStore = useRankStore();
const userRank = ref([]);


onMounted(async () => { 
  await rankStore.getUserRank();
  userRank.value = rankStore.userRank;
});

</script>

<style lang="scss" scoped>
.title {
  font-size: 1.3rem;
  font-weight: 700;
}

.ranking {
  margin-top: 20%;
  font-size: 1.1rem;
  color: white;
  padding: 50px;
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
  padding: 10px;
  text-align: center;
}
</style>