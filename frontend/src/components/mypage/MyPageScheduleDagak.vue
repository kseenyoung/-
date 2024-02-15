<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 다각 목록</div>
    <button class="btn common-btn go-back-btn" @click="goToCalander">
      <i class="bi bi-arrow-90deg-left"></i>
    </button>
    <button
      class="btn common-btn add-btn"
      data-bs-toggle="modal"
      data-bs-target="#MyPageScheduleDagakAddModal"
    >
      새 다각 만들기
    </button>
  </div>
  <!-- 다각 생성 모달 -->
  <MyPageScheduleDagakAddModal @updateDagakList="getAllDagakList" />

  <div class="dagak-list-wrapper" v-if="dagakList.length != 0">
    <div
      class="dagak-detail-wrapper common-pointer"
      v-for="dagak in dagakList"
      :key="dagak.dagakId"
      data-bs-toggle="modal"
      data-bs-target="#MyPageScheduleDagakModal"
      @click="openModal(dagak.dagakId, dagak.dagakName)"
    >
      <!-- <img src="@/assets/img/mypage/hexagon_thin.png" class="dagak-figure" /> -->
      <DagakImg :gak-length="dagak.gakLength" />
      <div class="dagak-title">{{ dagak.dagakName }}</div>
    </div>
    <!-- 각 상세정보 모달 -->
    <div
      class="modal fade"
      id="MyPageScheduleDagakModal"
      tabindex="-1"
      aria-labelledby="MyPageScheduleDagakModal"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="MyPageScheduleDagakModal">
              다각 상세정보
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="modal-body-title">[ {{ selectedDagakName }} ]</div>
            <div class="gak-wrapper">
              <draggable
                v-model="convertedGakList"
                :element="'div'"
                :animation="100"
                :list="convertedGakList"
                class="drag-container"
                @change="updateGakOrder"
                item-key="id"
              >
                <template #item="{ element }">
                  <div :key="element.gakId" class="gak-detail-wrapper">
                    <div class="gak-detail-wrapper-left">
                      <div class="gak-detail-order">
                        {{ element.gakOrder }}.
                      </div>
                      <div class="gak-detail-tag">
                        <select
                          class="form-select"
                          v-model="element.categoryId"
                        >
                          <option
                            v-for="category in categoryStore.categoryList"
                            :key="category.categoryId"
                            :value="category.categoryId"
                          >
                            {{ subjectMapping(category.categoryName) }}
                          </option>
                        </select>
                      </div>
                      <div class="gak-detail-time">
                        <input
                          class="form-control"
                          type="number"
                          v-model="element.runningTimeInMinutes"
                          :id="'gakRunnginTime_' + element.gakId"
                        />
                        분
                      </div>
                    </div>
                    <div class="gak-detail-wrapper-right common-pointer">
                      <i
                        class="bi bi-pencil-square"
                        @click="
                          updateGak(
                            element.gakId,
                            element.categoryId,
                            element.runningTimeInMinutes,
                          )
                        "
                      ></i>
                      <i
                        class="bi bi-trash"
                        @click="deleteGak(element.gakId)"
                      ></i>
                    </div>
                  </div>
                </template>
              </draggable>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">
              닫기
            </button>
            <button
              type="button"
              class="btn btn-danger"
              data-bs-dismiss="modal"
              @click="deleteDagak"
            >
              다각 삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="dagak-list-wrapper">
    생성한 다각이 없습니다. 새로 생성해주세요.
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useCategoryStore } from '@/stores/category';
import { useRouter } from 'vue-router';
import axios from 'axios';
import draggable from 'vuedraggable';
import MyPageScheduleDagakAddModal from './MyPageScheduleDagakAddModal.vue';
import DagakImg from '@/components/dagak/DagakImg.vue';
import { subjectMapping } from '@/utils/subjectMapping';

const categoryStore = useCategoryStore();
const router = useRouter();

const dagakList = ref([]);
const gakList = ref([]);
const selectedDagakId = ref(null);
const selectedDagakName = ref('');

onMounted(() => {
  getAllDagakList();
});

//전체 다각 목록 불러오기
// const getAllDagakList = function () {
//   axios
//     .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllDagakList`)
//     .then((res) => {
//       dagakList.value = res.data.result;

//       //다각의 각 개수 가져오기
//       dagakList.value.forEach((dagak) => {
//         const id = dagak.dagakId;
//         axios
//           .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
//             params: { dagakId: id },
//           })
//           .then((res) => {
//             dagak.gakLength = res.data.result.length;
//           });
//       });
//     });
// };
//다각 리스트 불러오기 + 다각의 각 갯수 불러와서 저장
const getAllDagakList = async function () {
  try {
    //전체 다각 목록
    const response = await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}dagak/getAllDagakList`,
    );
    const dagaks = response.data.result;

    //다각의 각 목록
    const gakLengthPromises = dagaks.map((dagak) =>
      axios.get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
        params: { dagakId: dagak.dagakId },
      }),
    );

    const gakLengthResponses = await Promise.all(gakLengthPromises);

    //다각 리스트에 각 개수 데이터 저장
    dagakList.value = dagaks.map((dagak, index) => ({
      ...dagak,
      gakLength: gakLengthResponses[index].data.result.length,
    }));
  } catch (error) {
    console.error('Error:', error);
  }
};

//초 -> 분 단위 변경
const convertedGakList = computed(() => {
  return gakList.value.map((gak) => ({
    ...gak,
    runningTimeInMinutes: gak.runningTime / 60,
  }));
});

//뒤로가기(캘린더로)
const goToCalander = function () {
  router.go(-1);
};

//다각 클릭 시 각 목록+상세정보 불러와서 저장
const openModal = function (id, name) {
  selectedDagakId.value = id;
  selectedDagakName.value = name;
  selectedDagakId.value = id;
  selectedDagakName.value = name;

  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllGakList`, {
      params: { dagakId: id },
    })
    .then((res) => {
      //각 목록을 gakOrder 기준으로 오름차순 정렬
      const sortedGakList = res.data.result.sort(
        (a, b) => a.gakOrder - b.gakOrder,
      );
      gakList.value = sortedGakList;
    });
};

//다각 삭제
const deleteDagak = function () {
  if (window.confirm('삭제하시겠습니까?')) {
    const body = {
      sign: 'deleteDagak',
      deleteDagakId: selectedDagakId.value,
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //삭제 성공
          getAllDagakList();
          // localStorage.removeItem('dagakStore');
        } else {
          alert('실패했습니다.');
        }
      });
  }
};

//각 삭제
const deleteGak = function (gakId) {
  if (window.confirm('정말로 삭제하시겠습니까?')) {
    const updatedGakList = gakList.value.filter((gak) => gak.gakId !== gakId);
    const remainGakInformation = updatedGakList.map((gak, index) => ({
      gakId: gak.gakId,
      gakOrder: index + 1,
    }));
    const body = {
      sign: 'deleteGak',
      gakId: gakId,
      remainGakInformation: remainGakInformation,
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //삭제 성공
          openModal(selectedDagakId.value, selectedDagakName.value);
        } else {
          alert('실패했습니다.');
        }
      });
  }
};

//각 수정
const updateGak = function (gakId, categoryId, runningTime) {
  if (window.confirm('수정하시겠습니까?')) {
    //삭제 후 각 리스트 다시 호출
    const body = {
      sign: 'modifyGak',
      dagakId: selectedDagakId.value,
      gakId: gakId,
      categoryId: categoryId,
      runningTime: runningTime * 60,
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body)
      .then((res) => {
        if (res.data.code === 1000) {
          //수정 성공
          openModal(selectedDagakId.value, selectedDagakName.value);
          alert('수정되었습니다.');
        } else {
          alert('실패했습니다.');
        }
      });
  }
};

//각 순서 수정
const updateGakOrder = function () {
  //드래그 한 정보를 gakOrder에 저장
  gakList.value.forEach((gak, index) => {
    gak.gakOrder = index + 1;
  });

  //Map형식으로 저장
  const gakInformation = gakList.value.map((gak) => ({
    gakId: gak.gakId,
    gakOrder: gak.gakOrder,
  }));

  const body = {
    sign: 'modifyGakOrder',
    GakInformation: gakInformation,
  };
  axios.post(`${import.meta.env.VITE_API_BASE_URL}dagak`, body).then((res) => {
    if (res.data.code === 1000) {
      //순서 수정 성공
      getAllCalendarList();
    } else {
      alert('실패했습니다.');
    }
  });
};

const getAllCalendarList = function () {
  axios
    .get(`${import.meta.env.VITE_API_BASE_URL}dagak/getAllCalendarList`)
    .then(() => {});
};
</script>

<style lang="scss" scoped>
.drag-item {
  border: 1px solid black;
}
.dagak-list-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  max-width: 710px;
  margin: 0 auto;
  .dagak-detail-wrapper {
    text-align: center;
    border: 1px solid black;
    border-radius: 4px;
    padding: 20px 10px 0px;
    width: 115px;
    min-height: 160px;
    margin: 0px 10px 30px;
    box-shadow: 5px 5px #ccc;
    .dagak-figure {
      width: 78px;
    }
  }
}
.modal-body {
  .modal-body-title {
    text-align: center;
    font-weight: bold;
    font-size: 1.3rem;
  }
  .gak-wrapper {
    font-size: 1.3rem;
    .gak-detail-wrapper {
      margin: 20px 0px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 10px;
      display: flex;
      justify-content: space-between;
      .gak-detail-wrapper-right {
        > i:nth-child(1) {
          margin-right: 15px;
        }
        > i:nth-child(2) {
          margin-right: 10px;
        }
      }
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
          input {
            display: inline-block;
            width: 80px;
          }
        }
      }
    }
  }
}
.common-mypage-wrapper {
  .common-mypage-title {
    display: inline-block;
  }
  .go-back-btn {
    position: relative;
    top: -8px;
    left: 10px;
  }
  .add-btn {
    display: block;
  }
}
</style>
