<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">
      인벤토리
      <span>
        보유: {{ userStore.loginUserInfo.userPoint }}
        <img src="@/assets/img/item/coin.png" class="coin" />
      </span>
      <button @click="saveInventory" class="btn common-btn inven-save-btn">
        아바타 저장
      </button>
    </div>
    <div class="inven-content-wrapper">
      <div class="inven-wearing">
        <div class="inven-wearing-now" ref="captureArea">
          <!-- <img src="/public/img/item/정육면체.png" class="main-item" /> -->
          <img src="@/assets/img/item/cube.png" class="main-item" />
          <template v-for="item in inventories" :key="item.inventoryId">
            <img
              :src="`/src/assets/img/item/${item.productImage}.png`"
              v-if="item.isWearing"
              class="main-item"
            />
          </template>
        </div>
        <div class="inven-wearing-list text-center">
          <div>착용중</div>
          <div class="inven-wearing-list-item-wrapper">
            <template v-for="item in inventories" :key="item.inventoryId">
              <img
                v-if="item.isWearing"
                class="inven-wearing-list-item item-img"
                :src="`/src/assets/img/store/${item.productImage}.png`"
                @dblclick="changeItem(item.inventoryId)"
              />
            </template>
          </div>
        </div>
      </div>

      <div class="inven-list text-center">
        <div v-for="item in inventories" :key="item.inventoryId">
          <img
            :src="`/src/assets/img/store/${item.productImage}.png`"
            :class="{ 'is-wearing': item.isWearing }"
            class="item-img"
            @dblclick="changeItem(item.inventoryId)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import html2canvas from 'html2canvas';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const captureArea = ref(null);
const inventories = ref([]);

async function changeItem(inventoryId) {
  for (const e of inventories.value) {
    if (e.inventoryId == inventoryId) {
      if (e.isWearing == 1) {
        e.isWearing = 0;
        const body = { sign: 'unEquip', takeOffItem: e.inventoryId };
        axios.post(`${import.meta.env.VITE_API_BASE_URL}inventory/`, body);
      } else {
        inventories.value
          .filter((filterItem) => filterItem.inventoryId != inventoryId)
          .forEach((item) => {
            if (
              e.category.productCategoryId == item.category.productCategoryId
            ) {
              if (item.isWearing == 1) {
                item.isWearing = 0;
                const body = { sign: 'unEquip', takeOffItem: e.inventoryId };
                axios.post(
                  `${import.meta.env.VITE_API_BASE_URL}inventory/`,
                  body,
                );
              }
            }
          });
        e.isWearing = 1;
      }
    }
  }
}

const saveInventory = async function () {
  const itemList = [];
  inventories.value.forEach((e) => {
    if (e.isWearing == 1) {
      itemList.push(e.inventoryId);
    }
  });
  console.log(itemList);
  const body = { sign: 'equip', itemList };
  const response = await axios.post(
    `${import.meta.env.VITE_API_BASE_URL}inventory/`,
    body,
  );
  captureAndSend();
  alert(response.data.message);
};
const getInventory = async function () {
  return await axios.get(`${import.meta.env.VITE_API_BASE_URL}inventory/get`);
};

const captureAndSend = async () => {
  if (!captureArea.value) return;
  const element = document.querySelector('.inven-wearing-now');
  console.log(element);
  const canvas = await html2canvas(element);
  console.log(canvas);
  const dataUrl = canvas.toDataURL('image/png');

  const response = await fetch(dataUrl);

  const blob = await response.blob();

  const file = new File([blob], 'screenshot.png', { type: 'image/png' });

  const formData = new FormData();
  formData.append('file', file); // `images`라는 이름으로 파일 데이터를 추가합니다.

  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}upload/profile`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.error(error);
    });
};

onMounted(async () => {
  const response = await getInventory();
  if (response.data.code === 1000) {
    console.log(response.data);
    inventories.value = response.data.result.inventories;
  } else {
    alert(response.data.message);
  }
});
</script>

<style lang="scss" scoped>
$box-radius: 8px;
$box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
.common-mypage-title {
  > span {
    font-size: 1.3rem;
    font-weight: bold;
  }
  .coin {
    width: 25px;
    height: auto;
    box-shadow: none;
    padding: 0px;
    margin: 0px;
  }
  .inven-save-btn {
    margin-left: 10px;
  }
}
.inven-content-wrapper {
  display: flex;
  padding: 10px;
  .inven-wearing {
    flex-basis: 40%;
    margin: 0px 30px;
    padding: 50px 0px 20px;
    border-radius: $box-radius;
    box-shadow: $box-shadow;

    .inven-wearing-now {
      width: 200px;
      height: 150px;
      text-align: center;
      position: absolute;
      .main-item {
        width: 120px;
        height: 120px;
        left: 3em;
        border: none;
        box-shadow: none;
        position: absolute;
      }
    }
    .inven-wearing-list {
      margin-top: 200px;
      background-color: $color-light-6;
      .inven-wearing-list-item-wrapper {
        display: flex;
        flex-wrap: wrap;
        padding-left: 15px;
      }
    }
  }
  .inven-list {
    padding: 25px 10px 10px 20px;
    flex-grow: 1;
    flex-basis: 52%;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    overflow-y: auto;
    background-color: $color-light-6;
    border-radius: $box-radius;
    box-shadow: $box-shadow;
    > img {
      margin: 10px;
      background-color: white;
    }
    .is-wearing {
      border: 3px solid $color-dark-1;
    }
  }
}
img {
  width: 50px;
  height: 50px;
  padding: 5px;
  margin: 10px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
  cursor: pointer;
}
.item-img {
  background-color: white;
}
</style>
