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
          alarmUnReadTotal.value = res.data.result.length;
          alarmUnReadList.value = res.data.result;
        });
    };
    const updateAlarm = async (newValue) => {
      console.log(newValue);
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
