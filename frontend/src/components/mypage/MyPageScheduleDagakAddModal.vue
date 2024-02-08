<template>
  <div
    class="modal fade"
    id="MyPageScheduleDagakAddModal"
    tabindex="-1"
    aria-labelledby="MyPageScheduleDagakAddModal"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="MyPageScheduleDagakAddModal">
            새 다각 만들기
          </h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="modal-body-form">
            <label for="dagakName" class="form-label">다각 제목</label>
            <input
              type="text"
              id="dagakName"
              class="form-control form-dagak-name"
              placeholder="다각 제목 입력"
              :value="dagakName"
              @input="onInputGakName"
            />
            <label for="dagakCategory" class="form-label">카테고리</label>
            <input
              type="text"
              id="dagakCategory"
              class="form-control"
              v-model="categorySearch"
              placeholder="카테고리 검색"
              style="margin-bottom: 5px"
            />
            <div class="input-group mb-3">
              <select class="form-select" v-model="gakCategory">
                <option disabled value="" selected>- 카테고리 선택 -</option>
                <option
                  v-for="category in filteredCategoryList"
                  :key="category.categoryId"
                  :value="category.categoryId"
                >
                  {{ category.categoryName }}
                </option>
              </select>
              <input
                class="form-control"
                type="number"
                id="gakRunningTime"
                placeholder="공부시간"
                v-model="gakRunningTime"
                min="1"
                required
                @keyup.enter="addGak(gakCategory, gakRunningTime)"
              />
              <button
                class="btn common-btn"
                @click="addGak(gakCategory, gakRunningTime)"
                :disabled="!gakCategory"
              >
                <i class="bi bi-plus-lg"></i>
              </button>
            </div>
          </div>
          <div class="modal-body-result">
            <div class="modal-body-result-title" v-if="dagakName != ''">
              [ {{ dagakName }} ]
            </div>
            <div class="modal-body-result-title" v-else>[ 다각 이름 ]</div>
            <div
              class="modal-body-result-detail common-pointer"
              v-for="(gak, index) in gaks"
              :key="index"
              @click="deleteGak"
            >
              <div>{{ index + 1 }}.</div>
              <div>{{ getCategoryName(gak.category) }}</div>
              <div>{{ gak.runningTime }}분</div>
              <div>
                <i class="bi bi-trash common-pointer" @click="deleteGak"></i>
              </div>
            </div>
            <div class="modal-body-result-totalTime">총 {{ totalTime }}분</div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">
            닫기
          </button>
          <button class="btn common-btn" @click="clear">지우기</button>
          <button
            class="btn btn-primary"
            data-bs-dismiss="modal"
            @click="addDagak"
            :disabled="gaks.length == 0 || dagakName == ''"
          >
            생성
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useCategoryStore } from '@/stores/category';
import axios from 'axios';

const categoryStore = useCategoryStore();
const emit = defineEmits(['updateDagakList']);

const categorySearch = ref('');
const gakCategory = ref('');
const gakRunningTime = ref(1);
const totalTime = ref(0);
const dagakName = ref('');
const gaks = ref([]);

//한글 입력 이슈
const onInputGakName = function (event) {
  dagakName.value = event.currentTarget.value;
};

//태그 검색 메서드
const filteredCategoryList = computed(() => {
  return categoryStore.categoryList.filter((category) =>
    category.categoryName
      .toLowerCase()
      .includes(categorySearch.value.toLowerCase()),
  );
});

//카테고리Id를 카테고리Name으로 반환
const getCategoryName = (categoryId) => {
  const category = categoryStore.categoryList.find(
    (cat) => cat.categoryId === categoryId,
  );
  return category ? category.categoryName : 'Unknown Category';
};

//각 생성
const addGak = function (category, runningTime) {
  if (category) {
    gaks.value.push({
      category: category,
      runningTime: runningTime,
    });
    totalTime.value = gaks.value.reduce(
      (total, gak) => total + gak.runningTime,
      0,
    );
    gakCategory.value = '';
    gakRunningTime.value = 1;
  } else {
    alert('카테고리를 입력해주세요');
  }
};

//각 삭제
const deleteGak = function (index) {
  gaks.value.splice(index, 1);
  updateTime();
};
//totalTime 수정
const updateTime = function () {
  totalTime.value = gaks.value.reduce(
    (total, gak) => total + gak.runningTime,
    0,
  );
};

//다각 만들기
const addDagak = function () {
  const body = {
    sign: 'addDagak',
    dagakName: dagakName.value,
    gaks: gaks.value.map(({ category, runningTime }) => ({
      category: String(category),
      runningTime: String(runningTime),
    })),
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body).then((res) => {
    if (res.data.code === 1000) {
      //생성 성공
      emit('updateDagakList');
      clear();
    } else {
      alert('실패했습니다.');
    }
  });
};

//초기화
const clear = function () {
  gakCategory.value = '';
  gakRunningTime.value = 1;
  totalTime.value = 0;
  gaks.value = [];
  dagakName.value = '';
};
</script>

<style lang="scss" scoped>
.modal-body {
  .modal-body-form {
    .form-label {
      font-weight: bold;
    }
    .form-dagak-name {
      margin-bottom: 20px;
    }
  }
  .modal-body-result {
    margin: 0px 80px;
    text-align: center;
    padding: 10px;
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 5px 5px #ccc;
    .modal-body-result-title {
      border-bottom: 1px solid #ccc;
      font-weight: 500;
      font-size: 1.3rem;
    }
    .modal-body-result-detail {
      border-radius: 6px;
      background-color: aliceblue;
      padding: 5px 15px;
      box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
      margin: 10px 0px;
      text-align: center;
      > div {
        display: inline-block;
      }
      > div:nth-child(2) {
        margin-left: 10px;
      }
      > div:nth-child(3) {
        margin-left: 10px;
      }
      > div:nth-child(4) {
        margin-left: 20px;
      }
    }
    .modal-body-result-totalTime {
      font-weight: 500;
    }
  }
}
</style>
