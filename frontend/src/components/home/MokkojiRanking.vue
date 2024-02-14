<template>
  <div class="ranking">
    <div>
      <p class="title">모꼬지 순위</p>
    </div>
    <table>
      <tbody>
        <tr v-for="item in mokkojiRank" :key="item.mokkoji.mokkojiId">
          <td>{{ item.mokkoji.rank }}위</td>
          <td>{{ item.mokkoji.mokkojiName }}</td>
          <td>{{ item.mokkoji.totalMemoryTime }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useRankStore } from '@/stores/rank';
import { ref, onMounted } from 'vue';

const rankStore = useRankStore();
const mokkojiRank = ref([]);
onMounted(async () => { 
  await rankStore.getMokkojiRank();
  mokkojiRank.value = rankStore.mokkojiRank;
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
