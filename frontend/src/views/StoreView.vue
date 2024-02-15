<template>
  <div class="marketpage">
    <img src="@/assets/board.png" class="board" />
    <div class="market-wrapper">
      <div v-if="userPoint != null">
        보유 포인트: {{ userPoint }}
        <img src="@/assets/img/item/coin.png" class="coin" />
      </div>
      <div v-else>보유 포인트: 로그인이 필요한 서비스입니다.</div>
      <div class="market-index">
        <button
          @click="selectCategory(null)"
          class="btn my-bc-btn common-btn-light"
        >
          전체
        </button>
        <template v-for="(categoryItems, category) in products" :key="category">
          <button
            @click="selectCategory(category)"
            class="btn my-bc-btn common-btn-light"
          >
            {{ category }}
          </button>
        </template>
      </div>
      <div class="market-content">
        <div
          class="market-items"
          v-for="(categoryItems, category) in filteredProducts"
          :key="category"
        >
          <div class="market-items-category">{{ category }}</div>
          <div class="market-items-detail-wrapper">
            <div
              class="market-items-detail"
              v-for="item in categoryItems"
              :key="item.productId"
              :title="item.productDescription"
            >
              <img
                :src="`/src/assets/img/store/${item.productImage}.png`"
                class="market-items-detail-img"
              />
              <div
                class="market-items-detail-descript"
                :class="{ visible: item.showDescription }"
              >
                {{ item.productDescription }}
              </div>
              <div class="market-items-detail-name">{{ item.productName }}</div>
              <div class="market-items-detail-price">
                {{ item.productPrice }}
                <img src="@/assets/img/item/coin.png" class="coin" />
              </div>
              <button
                class="market-items-detail-buy btn my-bc-btn common-btn-light"
                @click="buyProduct(item.productId)"
              >
                구매하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import {useUserStore} from '@/stores/user';
const router = useRouter();

const products = ref({});
const totalPage = ref(1);
const userPoint = ref(null);
const selectedCategory = ref(null);
const userStore = useUserStore();

onMounted(() => {
  getProductList();
  getLoginUserPoint();
});

const getProductList = function () {
  axios.get(`${import.meta.env.VITE_API_BASE_URL}product/list`).then((res) => {
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
    } else {
      alert(res.data.message);
    }
  });
};

const buyProduct = function (productId) {
  if (userPoint.value == null) {
    if (window.confirm('로그인이 필요한 서비스입니다. 로그인 하시겠습니까?')) {
      //로그인창으로 이동
      router.push({
        name: 'login',
      });
    }
  } else {
    const body = { sign: 'buy', productId: `${productId}` };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}product`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          alert('구매하였습니다.');
          getLoginUserPoint(); //포인트 새로고침
        } else {
          alert(res.data.message);
        }
      });
  }
};

//로그인 한 유저의 포인트 정보
const getLoginUserPoint = async function () {
  const body = {
    sign: 'getMyPage',
  };

  axios.post(`${import.meta.env.VITE_API_BASE_URL}user`, body).then((res) => {
    userPoint.value = res.data.result.userPoint;
    userStore.updatePoint(userPoint.value);
  });
};

//클릭한 카테고리 저장
const selectCategory = function (category) {
  selectedCategory.value = category;
};

//카테고리 별 목록 저장
const filteredProducts = computed(() => {
  if (!selectedCategory.value) {
    return products.value;
  }
  return { [selectedCategory.value]: products.value[selectedCategory.value] };
});
</script>

<style lang="scss" scoped>
.marketpage {
  background-image: url('@/assets/background.gif');
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
}
.my-bc-btn {
  color: black;
  background-color: $color-light-3;
  margin: 0px 5px;
  border: none;
  transition: none;
  box-shadow: 2px 3px 3px black;
}
.my-bc-btn:active {
  box-shadow: none;
  color: black;
  background-color: $color-light-3;
  box-shadow: 1px 1px 1px black;
}
.market-wrapper {
  color: white;
  z-index: 1;
  width: 55%;
  height: 65%;
  display: flex;
  flex-direction: column;
  .coin {
    width: 35px;
  }
  .market-index {
    height: 50px;
    margin-bottom: 10px;
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
          width: 150px;
          padding: 0px 5px 8px;
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
          .market-items-detail-descript {
            color: white;
            background-color: #494949;
            padding: 15px 20px;
            border-radius: 5px;
            display: none;
            transition: all ease 0.5s;
            position: absolute;
          }
          .market-items-detail-price {
            font-weight: bold;
            .coin {
              width: 25px;
              position: relative;
              top: -1px;
              left: -3px;
            }
          }
        }
      }
    }
  }
}
</style>
