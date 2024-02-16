<template>
  <div
    class="modal fade"
    id="deleteUserModal"
    tabindex="-1"
    aria-labelledby="deleteUserModal"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">회원탈퇴</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>

        <div class="modal-body">
          <div>정말로 탈퇴하시겠습니까?😢</div>
          <div class="form-floating">
            <input
              type="password"
              id="password"
              class="form-control"
              required
              v-model="password"
            />
            <label for="floatingInput">현재 비밀번호 입력</label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            취소
          </button>
          <button
            type="button"
            class="btn btn-danger"
            data-bs-dismiss="modal"
            @click="deleteUser"
          >
            탈퇴
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
const router = useRouter();

const userStore = useUserStore();
const password = ref("");

//회원탈퇴 axios
const deleteUser = function () {
  const body = {
    sign: "deleteUser",
    userPassword: password.value,
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}user`, body, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => res.data)
    .then((json) => {
      if (json.code == 1000) {
        //탈퇴 성공
        alert("탈퇴되었습니다.");
        userStore.deleteLoginUserInfo();
        const body = {
          sign: "logout",
        };
        axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body);
      } else {
        //실패
        alert(json.message);
      }
      password.value = "";
    });
  router.push({
    name: "home",
  });
};
</script>

<style lang="scss" scoped>
.modal-body {
  > div:nth-child(1) {
    margin-bottom: 20px;
  }
}
</style>
