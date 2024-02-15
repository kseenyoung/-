import { ref, onMounted, onBeforeUnmount } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { cookiesStorage } from '@/utils/CookiesUtil';
import { useAlarmStore } from '@/stores/alarm';
axios.defaults.headers.post['Content-Type'] = 'application/json';

export const useFriendStore = defineStore(
  'friendStore',
  () => {
    const loginFriends = ref([]);
    const APPLICATION_SERVER_URL =
      process.env.NODE_ENV === 'production'
        ? `${import.meta.env.VITE_API_BASE_URL}`
        : `${import.meta.env.VITE_API_BASE_URL}`;

    const getLoginFriends = function () {
      axios
        .post(
          APPLICATION_SERVER_URL + 'friend',
          { sign: 'getLoginFriends'},
          {
            headers: { 'Content-Type': 'application/json' },
          }
          )
        .then((res) => {
          console.log(res);
          if (res.data.code === 1000) {
            loginFriends.value = res.data.result;
          }
        });
    };

    return {
      loginFriends,
      getLoginFriends
    };
  },

  //store를 localStorage에 저장하기 위해서(새로고침 시 데이터 날라감 방지)
  {
    persist: {
      storage: cookiesStorage,
    },
  },
);
