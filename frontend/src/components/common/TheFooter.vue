<template>
  <footer class="d-flex justify-content-center">
    <div
      class="footer-con d-flex flex-column justify-content-center align-items-center"
    >
      <div>🐴(로고)</div>
      <div>다각의 의미</div>
      <div>Copyright &copy 2024 Dagak all rights reserved.</div>
    </div>
  </footer>
</template>

<script setup></script>

<style lang="scss" scoped>
footer {
  background-color: #9cd5c2;
  height: $footer-height;
  // position : relative;
  // transform : translateY(-100%);
}

.footer-con > div:nth-child(3) {
  font-size: 0.9rem;
}
</style>
