<template>
  <div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <form @submit.prevent="savePost" class="postList">
      <div class="title">게시글 작성</div>
      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">제목</label>
        <input v-model="title" class="form-control" id="title" />
      </div>
      <div class="dropdown">
        <button
          class="btn btn-secondary dropdown-toggle"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          태그
        </button>
        <ul class="dropdown-menu">
          <li v-for="tag in tagList" :key="tag.boardTagId">
            <a class="dropdown-item" href="#" @click="selectTag(tag)">{{
              tag.boardTagName
            }}</a>
          </li>
        </ul>
        <div v-if="selectedTagName" class="tag-div">
          선택된 태그 이름: {{ selectedTagName }}
        </div>
        <div class="mb-3"></div>
        <label for="content" class="form-label">내용</label>
        <textarea
          v-model="content"
          class="form-control"
          id="exampleFormControlTextarea1"
          rows="3"
        ></textarea>
      </div>
      <div class="pt-4">
        <button type="button" class="btn common-btn-light me-2" @click="goListPage">
          취소
        </button>
        <button class="btn common-btn-light" type="submit">글쓰기</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import axios from "axios";
const router = useRouter();
const title = ref("");
const content = ref("");
const tagList = ref([]);
const selectedTagId = ref("");
const selectedTagName = ref("");

// 목록 페이지로 돌아가기
const goListPage = () => {
  router.push({
    name: "postList",
  });
};
// 게시글 저장 메서드
const savePost = async () => {
  try {
    const body = {
      sign: "addBoard",
      boardTitle: title.value,
      boardContent: content.value,
      tagId: selectedTagId.value,
    };
    const response = await axios.post(`${import.meta.env.VITE_API_BASE_URL}board`, body);
    if (response.data.code == 1000) {
      goListPage();
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.log("Error saving post:", error);
  }
};
const getBoardTagList = async () => {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_API_BASE_URL}board/tag/list`
    );
    tagList.value = response.data.result;
  } catch (error) {
    console.log("Error saving post:", error);
  }
};
const selectTag = (tag) => {
  selectedTagId.value = tag.boardTagId;
  selectedTagName.value = tag.boardTagName;
};
onMounted(async () => {
  await getBoardTagList();
});
</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
.boardPage {
  background-image: url("@/assets/background.gif");
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  flex-direction: column; /* 컬럼 방향으로 요소들을 정렬 */
  align-items: center; /* 수평 방향으로 중앙 정렬 */
  justify-content: center; /* 수직 방향으로 중앙 정렬 */
}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
}
.postList {
  z-index: 1;
  min-width: 400px;
}
.form-label {
  color: white;
}
.title {
  color: white;
  font-size: 1.5rem;
  margin-bottom: 10px;
}
.tag-div {
  color: white;
}
</style>
