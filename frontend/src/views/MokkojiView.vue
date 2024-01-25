<template>
  <div class="container">
    <img src="@/assets/img/mypage/clip.png" class="img-clip">
    <div class="mok-content-wrapper">
      <div class="mok-content-left">
        <div class="mok-left-action">
          <div class="mok-subtitle">모꼬지명</div>
          <button class="btn common-btn">모꼬지 신청</button>
          <button class="btn common-btn">모꼬지 탈퇴</button>
          <button class="btn common-btn">모꼬지 삭제</button>
        </div>
        <div class="mok-left-list">
          <div class="mok-subtitle">모꼬지원 목록</div>
          <div class="mok-list-wrapper">

            <div class="mok-list-detail">
              <img src="@/assets/img/기본프로필_갈색.jpg">
              <div>hong</div>
              <button class="btn btn-sm btn-danger">강퇴</button>
            </div>
            <div class="mok-list-detail">
              <img src="@/assets/img/기본프로필_갈색.jpg">
              <div>hong</div>
              <button class="btn btn-sm btn-danger">강퇴</button>
            </div>
            <div class="mok-list-detail">
              <img src="@/assets/img/기본프로필_갈색.jpg">
              <div>hong</div>
              <button class="btn btn-sm btn-danger">강퇴</button>
            </div>
            
          </div>
        </div>
      </div>

      <div class="mok-content-right">
        <div class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 정보</div>
          <div class="mok-right-info-content">
            <div>길드명</div>
            <div>길드장</div>
            <div>길드 생성일</div>
            <div>인사말</div>
            <div>카테고리</div>
            <div>순위</div>
          </div>
        </div>
        <div class="mok-right-info">
          <div class="mok-right-info-subtitle">길드 수정</div>
          <div class="mok-right-info-content">
            <div>길드장만 보임</div>
            <div>길드 수정 폼</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
$my-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
.container {
  margin: 80px auto;
  padding: 0px 200px;
  min-height: 600px;
  // background-color: aliceblue;
  // font-size: 1.0rem;
  display: flex;
  flex-direction: column;
  .img-clip {
    width: 100px;
    position: relative;
    top: 35px;
    left: 100px;
  }
  .mok-title {
    font-size: 1.8rem;
    font-weight: 800;
    text-align: center;
  }
  .mok-content-wrapper {
    flex-grow: 1;
    display: flex;
    box-shadow: $my-shadow;
    padding: 20px;
    background-color: whitesmoke;
    .mok-content-left {
      flex-basis: 40%;
      padding: 30px;
      > div {
        background-color: cornsilk;
        box-shadow: $my-shadow;
      }
      .mok-subtitle {
        font-size: 1.5rem;
        font-weight: 600;
        text-align: center;
        padding: 5px;
        margin: 0px 25px;
        border-bottom: 1px solid black;
      }
      .mok-left-action {
        min-height: 150px;
        margin-bottom: 40px;
        > button {
          display: block;
          margin: 0 auto;
        }
      }
      .mok-left-list {
        .mok-list-wrapper {
          overflow-y: scroll;
          min-height: 300px;
          max-height: 300px;
          // background-color: antiquewhite;
          .mok-list-detail {
            display: flex;
            align-items: center;
            justify-content: space-around;
            padding: 10px;
        
            img {
              width: 30px;
              border-radius: 50%;
              margin-right: 10px;
            }
          }
        }
      }
    }
    .mok-content-right {
      flex-grow: 1;
      // border: 1px solid black;
      margin: 30px;
      .mok-right-info {
        // border: 1px solid black;
        margin-bottom: 50px;
        border-bottom: 1px solid black;
        min-height: 300px;
        box-shadow: $my-shadow;
        background-color: white;
        .mok-right-info-subtitle {
          background-color: lightblue;
          text-align: center;
          font-size: 1.5rem;
          font-weight: 600;
          padding: 5px;
          box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
        }
        .mok-right-info-content {
          font-size: 1.3rem;
          padding: 20px 20px 0px;
        }
      }
    }
  }
}
</style>