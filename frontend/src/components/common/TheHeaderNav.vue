<template>
  <header>
    <nav>
      <div>
        <RouterLink to="/">다각</RouterLink>
      </div>
      <div class="d-flex">
        <!-- 알람창 -->
        <RouterLink to="/store"><span class="underline">상점</span></RouterLink>
        <RouterLink to="/login"><span class="underline">로그인</span></RouterLink>
        <div class="dropdown-toggle common-pointer" data-bs-toggle="dropdown" aria-expanded="false">
          <img class="profile" src="@/assets/img/기본프로필_갈색.jpg">
        </div>
        <ul class="dropdown-menu">
          <div class="d-flex profile-info">
            <div>
              <img class="profile" src="@/assets/img/기본프로필_갈색.jpg">
            </div>
            <div>
              <div>이름</div>
              <div>아이디or이메일</div>
            </div>
          </div>
          <RouterLink to="/mypage" class="dropdown-item"><span class="underline">마이페이지</span></RouterLink>
          <li><a href="#" class="logout dropdown-item"><span>로그아웃</span></a></li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script setup>
</script>

<style lang="scss" scoped>
header {
  position: fixed;
  top: 0;
  z-index: 100;
  width: 100%;
  height: 70px;
  line-height: 70px;
  padding: 0px 80px;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

nav a.router-link-exact-active {
  // color: var(--color-text);
  color: #ff6347;
}
nav a.router-link-exact-active > .underline {
  transition: 0.8s;
  padding-bottom: 10px;
  background: linear-gradient(to top, #ff6347 8%, transparent 8%);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
}

nav a:hover {
  color: tomato;
}

.profile {
  width: 40px;
  border-radius: 50px;
}

.profile-info {
  padding: 0px 16px;
}

.profile-info > div:nth-child(2) {
  position: relative;
  top: -6px;
  left: 10px;
}

.profile-info > div:nth-child(2) > div {
  height: 23px;
}

</style>