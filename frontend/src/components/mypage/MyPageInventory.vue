<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">인벤토리</div>
    <div class="inven-content-wrapper">
      <div class="inven-wearing">
        <div class="inven-wearing-now">
          <img src="@/assets/img/item/정육면체.png" class="main-item">
          <img src="@/assets/img/item/아이템_1.png" class="main-item">
          <img src="@/assets/img/item/아이템_2.png" class="main-item">
        </div>
        <div class="inven-wearing-list text-center">
          <div>착용중</div>
          <div>
            <img src="@/assets/img/item/아이템_1.png">
            <img src="@/assets/img/item/아이템_2.png">
          </div>
        </div>
      </div>

      <div class="inven-list text-center">
        <img src="@/assets/img/item/아이템_1.png" class="is-wearing">
        <img src="@/assets/img/item/아이템_2.png" class="is-wearing">
        <img src="@/assets/img/item/아이템_3.png">
      </div>
    </div>

  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
$box-radius: 8px;
$box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
.inven-content-wrapper {
  display: flex;
  padding: 10px;
  .inven-wearing {
    flex-basis: 40%;
    margin: 0px 30px;
    padding: 50px 0px 20px;
    border-radius: $box-radius;
    box-shadow: $box-shadow;

    .inven-wearing-now {
      text-align: center;
      position: absolute;
      .main-item {
        width: 120px;
        height: 120px;
        left: 60px;
        border: none;
        box-shadow: none;
        position: absolute;
      }
    }
    .inven-wearing-list {
      margin-top: 200px;
      background-color: aliceblue;
      > div:last-child {
        display: flex;
        flex-wrap: wrap;
        > img {
          background-color: white;
        }
      }
    }
  }
  .inven-list {
    padding: 10px;
    flex-grow: 1;
    flex-basis: 60%;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    overflow-y: auto;
    background-color: aliceblue;
    border-radius: $box-radius;
    box-shadow: $box-shadow;
    > img {
      margin: 10px;
      background-color: white;
    }
    .is-wearing {
      border: 3px solid tomato;
    }
  }
}
img {
  width: 50px;
  height: 50px;
  padding: 5px;
  margin: 10px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
  cursor: pointer;
}
</style>