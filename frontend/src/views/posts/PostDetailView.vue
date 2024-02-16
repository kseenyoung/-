<template>
  <div class="boardPage">
    <img src="@/assets/board.png" class="board" />
    <div class="content">
      <div class="post">
        <button class="btn tag" v-if="detail.boardTag">
          {{ detail.boardTag.boardTagName }}
        </button>
        <h2>{{ detail.boardTitle }}</h2>
        {{ detail.userId }}

        <p class="date" v-if="detail.createdDate">
          {{ formatDate(detail.createdDate) }}
        </p>
        <p class="post-content">{{ detail.boardContent }}</p>
      </div>

      <div class="comment-section">
        <input
          v-if="userStore.loginUserInfo.userId"
          type="text"
          class="comment-input form-control"
          @keydown.enter="addComment"
          v-model="newComment"
          placeholder="댓글을 입력해주세요..."
        />
        <!-- 기존 댓글을 보여주는 부분 -->
        <div class="comment_body" v-for="comment in comments" :key="comment.commentId">
          <div v-if="!comment.isEditing">
            <div class="comment-comment">
              {{ comment.userId }} : {{ comment.comment }}
              <div class="comment_date" v-if="comment.createdDate">
                {{ formatDate(detail.createdDate) }}
              </div>
            </div>
            <button
              v-if="userStore.loginUserInfo.userId == comment.userId"
              @click="startEditing(comment)"
              class="btn common-btn-light"
            >
              수정하기
            </button>

            <button
              v-if="userStore.loginUserInfo.userId == comment.userId"
              @click="deleteComment(comment)"
              class="btn common-btn-light"
            >
              삭제하기
            </button>
          </div>

          <!-- 수정 중인 댓글을 보여주는 부분 -->
          <div v-else>
            <div>댓글 수정</div>
            <input
              class="comment-input form-control"
              type="text"
              v-model="comment.editedComment"
            />
            <button @click="finishEditing(comment)" class="btn common-btn-light">
              완료
            </button>
            <button @click="cancelEditing(comment)" class="btn common-btn-light">
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="content-header">
      <div class="row g-2">
        <div class="col-auto">
          <button class="btn common-btn-light" @click="goListPage">
            <i class="bi bi-list-task"></i>목록
          </button>
        </div>
        <div class="col-auto">
          <button
            v-if="detail.userId === userStore.loginUserInfo.userId"
            class="btn common-btn-light"
            @click="goEditPage"
          >
            <i class="bi bi-pencil-square"></i>수정
          </button>
        </div>
        <div class="col-auto">
          <button
            v-if="detail.userId === userStore.loginUserInfo.userId"
            class="btn common-btn-light"
            @click="deletePost"
          >
            <i class="bi bi-trash"></i>삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { useBoardStore } from "@/stores/board";
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import axios from "axios";

const boardStore = useBoardStore();
const route = useRoute();
const router = useRouter();
const postId = route.params.id;
const detail = ref([]);
const comments = ref([]);
const newComment = ref("");

const userStore = useUserStore();

const deletePost = async () => {
  let flag = confirm("정말로 삭제하시겠습니까?");
  if (flag) {
    try {
      const body = {
        sign: "deletePost",
        boardId: detail.value.boardId,
      };
      const response = await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}board`,
        body
      );
      goListPage();
    } catch (error) {
      console.log("Error saving post:", error);
    }
  }
};
const startEditing = (comment) => {
  comment.isEditing = true;
  comment.editedComment = comment.comment;
};
const finishEditing = async (comment) => {
  if (comment.editedComment) {
    const body = {
      sign: "modifyComment",
      commentId: comment.commentId,
      comment: comment.editedComment,
      boardId: detail.value.boardId,
    };
    const response = await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}board/comment`,
      body
    );
    if (response.data.code == 1000) {
      await fetchDetail(detail.value.boardId);
    }
  }
};

const cancelEditing = (comment) => {
  comment.isEditing = false;
};

const addComment = async () => {
  const body = {
    sign: "addComment",
    boardId: detail.value.boardId,
    comment: newComment.value,
  };
  const response = await axios.post(
    `${import.meta.env.VITE_API_BASE_URL}board/comment`,
    body
  );
  if (response.data.code == 1000) {
    await fetchDetail(detail.value.boardId);
  }
  newComment.value = "";
};

const deleteComment = async (comment) => {
  let flag = confirm("정말로 삭제하시겠습니까?");
  if (flag) {
    const body = {
      sign: "deleteComment",
      commentId: comment.commentId,
      boardId: detail.value.boardId,
    };
    const response = await axios.post(
      `${import.meta.env.VITE_API_BASE_URL}board/comment`,
      body
    );
    if (response.data.code == 1000) {
      await fetchDetail(detail.value.boardId);
    }
  }
};

const goListPage = () => {
  router.push({
    name: "postList",
  });
};

const goEditPage = () => {
  router.push({
    name: "postEdit",
    params: {
      id: postId,
    },
  });
};

const fetchDetail = async (id) => {
  await boardStore.getPostDetail(id);
  detail.value = boardStore.postDetail;
  comments.value = boardStore.comments;
  comments.value = comments.value.map((comment) => ({
    ...comment,
    isEditing: false,
    editedComment: "",
  }));
};
const formatDate = (timestampArray) => {
  const date = new Date(...timestampArray);
  return date.toLocaleString();
};

onMounted(async () => {
  fetchDetail(postId);
});
</script>

<style lang="scss" scoped>
.container {
  margin: 80px;
}
.tag {
  background-color: $color-light-3;
  padding: 2px 5px;
  margin-bottom: 10px;
}
.container {
  margin: 80px auto;
  font-size: 1rem;
}
.post-content {
  margin-top: 1%;
  margin-bottom: 30%;
}
.boardPage {
  width: 100%;
  background-image: url("@/assets/background.gif");
  background-color: rgba(0, 0, 0, 0.5);
  background-size: cover;
  height: 100vh;
  padding-top: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
}
.board {
  width: 70%;
  height: 85%;
  position: absolute;
  z-index: 0;
}

.post {
  width: 70%;
  margin: 1%;
}
.postList::-webkit-scrollbar {
  display: none;
}
p .date {
  color: white;
  font-size: 0.1rem;
}
.content {
  z-index: 1;
  width: 60%;
  display: flex;
  flex-wrap: wrap;
  overflow-y: auto;
  height: 50vh;
  align-items: center;
  justify-content: center;
}

.comment-section {
  width: 700px;
}
.content h2 {
  margin-bottom: 1%;
}
.content .date {
  margin-bottom: 10%;
}
.content-header {
  z-index: 1;
}
.comment_body {
  margin: 20px 0px;
  border: 2px solid white;
  width: 100%;
  border-radius: 5px;
  padding: 10px;
}
.comment_date {
  font-size: 0.8rem;
}
.comment-comment {
  padding: 10px;
}
.comment-input {
  margin: 10px 0px;
  border: 2px solid white;
  width: 100%;
}
input:focus {
  outline: 3px solid gray;
}
.content::-webkit-scrollbar {
  display: none;
}
</style>
