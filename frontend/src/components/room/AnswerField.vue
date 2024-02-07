<template>
  <div class="questionfield">
    <form action="/submit" method="post">
      <label for="message"></label>
      <textarea
        id="message"
        name="message"
        rows="4"
        cols="30"
        placeholder="-- 지식을 공유해주세요^^ --"
      ></textarea>
      <button class="question" type="submit">답변</button>
    </form>
  </div>
</template>

<script setup></script>

<style scoped>
textarea {
  box-sizing: border-box; /* box-sizing을 border-box로 설정 */
  width: calc(100% - 10px);
  border: 2px solid black;
  margin-left: 5px;
  margin-right: 5px;
}
.question {
  border: 1px black dashed;
  font-family: 'NanumSquareNeo';
  font-weight: 800;
  font-size: 20px;
  padding: 5px;
  right: 0;
}
#message {
  font-family: 'NanumSquareNeo';
  font-weight: 600;
  font-size: 15px;
  text-align: left;
  padding-left: 10px;
  padding-right: 10px;
}
</style>
