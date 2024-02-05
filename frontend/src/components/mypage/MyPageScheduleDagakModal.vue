<template>
  <div
    class="modal fade modal-sm"
    :id="'MyPageScheduleDagakModal1-' + dagakId"
    tabindex="-1"
    :aria-labelledby="'MyPageScheduleDagakModal1-' + dagakId"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="MyPageScheduleDagakModal">
            [{{ dagakId }} - 다각아이디(제목으로)]
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="gak-wrapper">
            <div
              v-for="gak in gakList"
              :key="gak.gakId"
              class="gak-detail-wrapper"
            >
              <div class="gak-detail-wrapper-left">
                <div class="gak-detail-order">{{ gak.gakOrder }}.</div>
                <div class="gak-detail-tag">
                  {{ getCategoryName(gak.categoryId) }}
                </div>
                <div class="gak-detail-time">{{ gak.runningTime }}분</div>
              </div>
              <div
                class="gak-detail-wrapper-right common-pointer"
                @click="deleteGak(gak.gakId)"
              >
                <i class="bi bi-x"></i>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary">닫기</button>
          <button type="button" class="btn btn-danger" data-bs-dismiss="modal">
            다각 삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/category';
import axios from 'axios';

const categoryStore = useCategoryStore();

const props = defineProps({
  dagakId: String,
});
const dagakId = props.dagakId;
const gakList = ref([]);

onMounted(() => {
  getAllGakList(dagakId);
});

const getAllGakList = function (id) {
  console.log(id);
  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
      params: { dagakId: id },
    })
    .then((res) => {
      console.log(res);
      gakList.value = res.data.result;
    });
};

const getCategoryName = (categoryId) => {
  const category = categoryStore.categoryList.find(
    (cat) => cat.categoryId === categoryId,
  );
  return category ? category.categoryName : 'Unknown Category';
};

const deleteGak = function (id) {
  console.log(id);
  if (window.confirm('정말로 삭제하시겠습니까?')) {
    alert('dd');
  }
};
</script>

<style lang="scss" scoped>
.modal-body {
  .gak-wrapper {
    font-size: 1.4rem;
    .gak-detail-wrapper {
      margin: 20px 0px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 10px;
      display: flex;
      justify-content: space-between;
      .gak-detail-wrapper-left {
        > div {
          display: inline-block;
        }
        .gak-detail-order {
          width: 30px;
          height: 30px;
          text-align: center;
        }
        .gak-detail-tag {
          margin-left: 5px;
        }
        .gak-detail-time {
          margin-left: 10px;
        }
      }
    }
  }
}
</style>
