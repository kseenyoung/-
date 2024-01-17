<template>
  <div class="marketpage">
  <p class="title">다각<br>상점</p>
  <div class="cardlist">
  <div class="card" style="width: 18rem;">
    <img src="@/assets/horse1.png">
    <div class="card-body">
      <h5 class="card-title">Lv.1 망아지</h5>
      <p class="card-text">망아지 설명</p>
      <a href="#" class="btn btn-primary">구매하기</a>
    </div>
  </div>
  <div class="card" style="width: 18rem;">
    <img src="@/assets/horse1.png">
    <div class="card-body">
      <h5 class="card-title">Lv.2 경주마</h5>
      <p class="card-text">경주마 설명</p>
      <a href="#" class="btn btn-primary">구매하기</a>
    </div>
  </div>
  <div class="card" style="width: 18rem;">
    <img src="@/assets/horse1.png">
    <div class="card-body">
      <h5 class="card-title">Lv.3 적토마</h5>
      <p class="card-text">적토마 설명</p>
      <a href="#" class="btn btn-primary">구매하기</a>
    </div>
  </div>
  <div class="card" style="width: 18rem;">
    <img src="@/assets/horse1.png">
    <div class="card-body">
      <h5 class="card-title">Lv.4 유니콘</h5>
      <p class="card-text">유니콘 설명</p>
      <a href="#" class="btn btn-primary">구매하기</a>
    </div>
  </div>
</div>
</div>
  </template>
  
  <script setup>
  
  </script>
  
  <style scoped>
  .title {
    font-weight: 900;
    font-size: 50px;
  }
  .cardlist{
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  .marketpage{
    margin-top: 80px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .card img{
    border: 10px solid black;
  }
  </style>