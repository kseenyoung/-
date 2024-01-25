<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 알림</div>
    <div class="alarm-content-wrapper">

      <!-- 
        게시글, 질문은 확인 버튼 1개만,
        길드, 친구는 수락, 거절 버튼 2개
      -->
      <div class="alarm-wrapper alarm-new">
        <div class="alarm-check">
          <i class="bi bi-envelope"></i>
        </div>
        <div class="alarm-content">
          <div class="alarm-content-header">
            <div>게시글</div>
            <div>24.01.02</div>
          </div>
          <div class="alarm-content-body">
            <div>게시글에 댓글이 달렸습니다.</div>
          </div>
        </div>
        <div class="alarm-btn">
          <button class="btn common-btn"><i class="bi bi-check2"></i></button>
        </div>
      </div>

      <div class="alarm-wrapper alarm-new">
        <div class="alarm-check">
          <i class="bi bi-envelope"></i>
        </div>
        <div class="alarm-content">
          <div class="alarm-content-header">
            <div>길드</div>
            <div>24.01.01</div>
          </div>
          <div class="alarm-content-body">
            <div>ssafy님의 길드가입 신청</div>
          </div>
        </div>
        <div class="alarm-btn">
          <button class="btn common-btn">수락</button>
          <button class="btn common-btn">거절</button>
        </div>
      </div>

      <div class="alarm-wrapper">
        <div class="alarm-check">
          <i class="bi bi-envelope-paper"></i>
        </div>
        <div class="alarm-content">
          <div class="alarm-content-header">
            <div>친구</div>
            <div>24.01.01</div>
          </div>
          <div class="alarm-content-body">
            <div>ssafy님의 친구 신청</div>
          </div>
        </div>
        <div class="alarm-btn">
          <button class="btn common-btn" disabled>수락</button>
          <button class="btn common-btn" disabled>거절</button>
        </div>
      </div>

      <div class="alarm-wrapper">
        <div class="alarm-check">
          <i class="bi bi-envelope-paper"></i>
        </div>
        <div class="alarm-content">
          <div class="alarm-content-header">
            <div>질문</div>
            <div>24.01.01</div>
          </div>
          <div class="alarm-content-body">
            <div>질문에 답변이 달렸습니다</div>
          </div>
        </div>
        <div class="alarm-btn">
          <button class="btn common-btn" disabled><i class="bi bi-check2"></i></button>
        </div>
      </div>

    </div>

  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.alarm-content-wrapper {
  font-size: 1.05rem;
  margin: 0px 40px;
  overflow-y: auto;
  max-height: 600px;
  .alarm-new {
    background-color: rgba(255, 99, 71, 0.088);
  }
  > div:last-child {
    border-bottom: 1px solid black;
  }
  .alarm-wrapper {
    padding: 5px 30px;
    border-top: 1px solid black;
    display: flex;
    .alarm-check {
      flex-basis: 5%;
    }
    .alarm-content {
      display: flex;
      flex-direction: column;
      flex-grow: 1;

      .alarm-content-header {
        display: flex;
        align-items: center;

        div:nth-child(1) {
          color: tomato;
          font-weight: 500;
          margin-right: 6px;
        }
        div:nth-child(2) {
          color: #777;
          font-size: 0.9rem;
        }
      }
      .alarm-content-body {
        display: flex;
        justify-content: space-between;
      }
    }
    .alarm-btn {
      padding-top: 10px;
      > button {
        margin-left: 5px;
      }
    }
  }
}
</style>