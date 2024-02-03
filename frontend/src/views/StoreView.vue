<template>
  <div class="marketpage">
    <p class="title">다각<br />상점</p>
    <div class="cardlist">
      <div
        class="card"
        style="width: 18rem"
        v-for="item in products"
        :key="item.productId"
      >
        <img :src="item.productImage" />
        <div class="card-body">
          <h5 class="card-title">{{ item.productName }}</h5>
          <p class="card-text">💎{{ item.productPrice }}p</p>
          <a
            href="#"
            class="btn btn-primary"
            @click="buyProduct(item.productId)"
            >구매하기</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
const products = ref([]);

const getStoreList = async function () {
  return await axios.get(`${import.meta.env.VITE_API_BASE_URL}product/list`);
};
const buyProduct = async function (productId) {
  const body = { sign: 'buy', productId: `${productId}` };
  const response = await await axios.post(
    `${import.meta.env.VITE_API_BASE_URL}product`, body);
  alert(response.data.message);
};
onMounted(async () => {
  const response = await getStoreList();
  if (response.data.code === 1000) {
    console.log(response.data);
    products.value = response.data.result.productList;
  } else {
    alert(response.data.message);
  }
});
</script>

<style scoped>
.title {
  font-weight: 900;
  font-size: 50px;
}
.cardlist {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.marketpage {
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.card img {
  border: 10px solid black;
}
</style>
