import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  //로그인 세션 test
  const loginUser = ref('');
  const login = function() {
    const id = ref('yj');
    const sub = ref('정보처리기사');
    const user = ref({
      id: id.value,
      sub: sub.value,
    });

    const userString = JSON.stringify(user.value);
    sessionStorage.setItem('login-user', userString);
    loginUser.value = JSON.parse(sessionStorage.getItem('login-user'));
    alert('로그인성공')
    
  }

  return { loginUser, login, }
})
