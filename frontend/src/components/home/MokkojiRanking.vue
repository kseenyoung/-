<template>
  <div class="ranking">
    <div>
      <p class="title">모꼬지 순위</p>
      <br /><br />
    </div>
    <table>
      <tbody>
        <td class="table-head"><b>순위</b></td>
        <td class="table-head"><b>이름</b></td>
        <td class="table-head"><b>순공 시간</b></td>

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
import { useRankStore } from '@/stores/rank'
import { ref, onMounted } from 'vue'

const rankStore = useRankStore()
const mokkojiRank = ref([])
onMounted(async () => {
  await rankStore.getMokkojiRank()
  mokkojiRank.value = rankStore.mokkojiRank
})
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

th,
td {
  padding: 10px;
  text-align: center;
}

.table-head {
  border-bottom: 2px dashed white;
}
</style>
