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
        .get(`${import.meta.env.VITE_API_BASE_URL}alarms/getUncheckAlarmList`)
        .then((res) => {
          const sortedAlarmList = res.data.result.sort(
            (a, b) => new Date(b.createdDate) - new Date(a.createdDate),
          );
          alarmUnReadTotal.value = sortedAlarmList.length;
          alarmUnReadList.value = sortedAlarmList;
        });
    };
    const updateAlarm = async (newValue) => {
      alarmUnReadTotal.value = alarmUnReadList.value.unshift(newValue);
    };
    return {
      alarmUnReadTotal,
      alarmUnReadList,
      getUnReadAlarmList,
      updateAlarm,
    };
  },
  { persist: true },
);
