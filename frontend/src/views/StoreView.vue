<template>
  <div class="marketpage">
    <img src="@/assets/board.png" class="board" />
    <div class="market-wrapper">
      <div>보유 포인트:</div>
      <div class="market-index">
        <button>전체</button>
        <button>전체</button>
      </div>
      <div class="market-content">
        <div
          class="market-items"
          v-for="(categoryItems, category) in products"
          :key="category"
        >
          <div class="market-items-category">{{ category }}</div>
          <div class="market-items-detail-wrapper">
            <div
              class="market-items-detail"
              v-for="item in categoryItems"
              :key="item.productId"
            >
              <img
                src="@/assets/img/item/아이템_1.png"
                class="market-items-detail-img"
              />
              <div class="market-items-detail-name">{{ item.productName }}</div>
              <div class="market-items-detail-price">
                {{ item.productPrice }}
              </div>
              <button class="market-items-detail-buy">구매</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const products = ref({});
const totalPage = ref(1);

onMounted(() => {
  getProductList();
});

const getProductList = function () {
  axios.get(`${import.meta.env.VITE_API_BASE_URL}product/list`).then((res) => {
    console.log(res.data.result.productList);
    if (res.data.code === 1000) {
      //전체 상품 목록
      const productList = res.data.result.productList;

      //상품 카테고리 별로 나눠서 저장
      productList.forEach((product) => {
        const categoryName = product.productCategoryDto.productCategoryName;
        if (!products.value[categoryName]) {
          // 해당 카테고리에 대한 배열이 없을 경우 초기화
          products.value[categoryName] = [];
        }
        products.value[categoryName].push(product);
      });
      //페이지 저장
      totalPage.value = res.data.result.totalPage;
      console.log(products.value);
    } else {
      alert(res.data.message);
    }
  });
};
const buyProduct = function (productId) {
  const body = { sign: 'buy', productId: `${productId}` };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}product`, body)
    .then((res) => {
      console.log(res);
      alert(res.data.message);
    });
};
</script>

<style scoped>
.marketpage {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.board {
  width: 70%;
  height: 80%;
  position: absolute;
}
.market-wrapper {
  color: white;
  z-index: 1;
  width: 55%;
  height: 54%;
  display: flex;
  flex-direction: column;
  .market-index {
  }
  .market-content {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    .market-items {
      display: flex;
      flex-direction: column;
      border-bottom: 2px dashed white;
      padding: 10px 0px;
      .market-items-category {
        font-weight: bold;
        font-size: 1.3rem;
      }
      .market-items-detail-wrapper {
        display: flex;
        flex-wrap: wrap;
        .market-items-detail {
          color: black;
          font-size: 1rem;
          text-align: center;
          border: 5px solid white;
          background: linear-gradient(to top, #9c9c9c, #fff);
          margin: 5px 10px 5px 0px;
          border-radius: 5px;
          .market-items-detail-img {
            width: 90px;
            padding: 0px 10px;
          }
          .market-items-detail-name {
            font-weight: bold;
          }
          .market-items-detail-price {
            font-weight: bold;
          }
        }
      }
    }
  }
}
</style>
