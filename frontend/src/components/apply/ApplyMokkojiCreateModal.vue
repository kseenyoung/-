<template>
  <div class="modal fade" id="createMokkoji" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createMokkoji">모꼬지 만들기 : 5000P</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label for="mokkojiName">모꼬지 이름</label>
            <input
              type="text"
              class="form-control"
              v-model="mokkojiName"
              id="mokkojiName"
              required
            />
          </div>
          <div class="form-group">
            <label for="mokkojiStatus">모꼬지 상태</label>
            <input
              type="text"
              class="form-control"
              v-model="mokkojiStatus"
              id="mokkojiStatus"
              required
            />
          </div>
          <div class="form-group">
            <label for="mokkojiCategories">모꼬지 카테고리</label>
            <!-- <input
              type="text"
              class="form-control"
              v-model="categorySearch"
              placeholder="카테고리 검색"
            /> -->
            <select
              class="form-select"
              v-model="selectedCategory"
              @change="addSelectedCategory"
            >
              <option disabled value="" selected>- 카테고리 선택 -</option>
              <option
                v-for="category in filteredCategoryList"
                :key="category.categoryId"
                :value="category.categoryId"
              >
                {{ subjectMapping(category.categoryName) }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>선택된 카테고리:</label><br />
            <div
              v-for="tag in tagSelectList"
              :key="tag.categoryId"
              class="selectedTag common-pointer"
              @click="deleteTag(tag)"
            >
              # {{ subjectMapping(tag.categoryName) }}
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            닫기
          </button>
          <button class="btn btn-secondary" @click="clear">지우기</button>
          <button
            class="btn common-btn"
            data-bs-dismiss="modal"
            @click="createMokkoji"
            :disabled="mokkojiName == '' || mokkojiStatus == ''"
          >
            만들기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useUserStore } from '@/stores/user';
import { useCategoryStore } from '@/stores/category';
import { subjectMapping } from '@/utils/subjectMapping';
import axios from 'axios';

const userStore = useUserStore();
const categoryStore = useCategoryStore();
const mokkojiName = ref('');
const mokkojiStatus = ref('');
const mokkojiCategories = ref([]);
const tagSelectList = ref([]);
const selectedCategory = ref('');
const categorySearch = ref('');
const emit = defineEmits(['updateList']);

//태그 검색 메서드
const filteredCategoryList = computed(() => {
  return categoryStore.categoryList.filter((category) =>
    category.categoryName
      .toLowerCase()
      .includes(categorySearch.value.toLowerCase()),
  );
});

//태그 선택 시 mokkojiCategories에 추가하는 메서드
const addSelectedCategory = function () {
  const categoryId = selectedCategory.value;

  if (
    !tagSelectList.value.some((category) => category.categoryId === categoryId)
  ) {
    const category = categoryStore.categoryList.find(
      (c) => c.categoryId === categoryId,
    );

    if (category) {
      tagSelectList.value.push(category);
      mokkojiCategories.value.push(category.categoryId);
    }
  }
};

//태그 삭제 메서드
const deleteTag = function (tagToDelete) {
  tagSelectList.value = tagSelectList.value.filter(
    (tag) => tag.categoryId !== tagToDelete.categoryId,
  );
  mokkojiCategories.value = mokkojiCategories.value.filter(
    (categoryId) => categoryId !== tagToDelete.categoryId,
  );
};

const createMokkoji = function () {
  const body = {
    sign: 'addMokkoji',
    mokkojiName: mokkojiName.value,
    mokkojiStatus: mokkojiStatus.value,
    mokkojiCategories: mokkojiCategories.value,
  };
  axios
    .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body)
    .then((res) => {
      if (res.data.code === 1000) {
        alert('생성되었습니다.');
        //생성 후 모꼬지 목록 불러오기
        emit('updateList');
        //유저 정보 업데이트
        userStore.getLoginUserInfo();
      } else {
        alert(res.data.message);
      }
    });
};

//내용 초기화
const clear = function () {
  mokkojiName.value = '';
  mokkojiStatus.value = '';
  selectedCategory.value = '';
  tagSelectList.value = [];
  categorySearch.value = '';
};
</script>

<style lang="scss" scoped>
.form-group {
  margin-bottom: 20px;

  label {
    font-size: 1.1rem;
    font-weight: 500;
  }

  .selectedTag {
    display: inline-block;
    background-color: $color-light-6;
    border-radius: 10px;
    padding: 3px 10px;
    margin: 0px 3px;
  }
}
</style>
