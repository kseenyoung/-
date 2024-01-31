import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useAlarmStore = defineStore(
  'alarmStore',
  () => {
    //헤더용 안읽은 알림 목록, 갯수
    const alarmUnReadTotal = ref();
    const alarmUnReadList = ref([]);
    const getUnReadAlarmList = function () {
      axios
        .get('https://localhost:8080/dagak/alarms/listOfUnchecked')
        .then((res) => {
          alarmUnReadTotal.value = res.data.result.length;
          alarmUnReadList.value = res.data.result;
        });
    };

    return { alarmUnReadTotal, alarmUnReadList, getUnReadAlarmList };
  },
  { persist: true },
);
