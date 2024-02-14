<template>

    <div class="parent">
        <div class="background">
       <div class="part one">
        
        <div class="title" style="color:#000000; font-size:10rem;">
          다각
        <h2><div style="color:black ;font-weight:bold" class="">다같이 랜덤 스터디</div></h2>
        </div>
        <div
          style="color: white;"
          v-if="userStore.loginUserInfo.userId == null"
          @click="navigateToLogin"
>
          <div class="is-typed">
            <h3 style="display:inline-block;"> 
              <VueWriter :array="arr" style="display:inline-block;" :caret="underscore" />
            </h3>
        <p style="display: inline-block;" class="font-weight-bold"><h3> 공부하기</h3></p>
        </div>
      </div>


      <!-- <div
        style="color: white;"
        v-else-if="userStore.loginUserInfo.userId != null && arr.length ===0"
        @click="navigateToMyPageSchedule">

        <div class="is-typed">
          <h3 style="display:inline-block;"> </h3>
          <p style="display: inline-block;" class="font-weight-bold"><h3> 다각 만들러가기</h3></p>
        </div>

        <div style="display: inline-block;">
          <div class="pixel"><p>하이</p></div>
        </div>
      </div> -->



      <div
        style="color: white;"
        v-if="userStore.loginUserInfo.userId != null && arr.length ===0"
        >
        <div class="is-typed" style="" @click="navigateToMyPageSchedule">
          <h3 style=""> </h3>
          <p style="" class="font-weight-bold"><h3> 다각 만들러가기</h3></p>
        </div>
      </div>
      <div
          style="color: white;"
          v-else
          @click="navigateToStudyRoom"
          >
          <div class="is-typed">
            <h3 style="display:inline-block;"> 
              <VueWriter :array="arr" style="display:inline-block;" :caret="underscore" />
            </h3>
        <p style="display: inline-block;" class="font-weight-bold"><h3> 공부하기</h3></p>
        </div>
      </div>
        <div v-if="userStore.loginUserInfo.userId" class="friends">
            <div class="bubble medium bottom" style="margin-left : 80%;">
                친구 <b style="color: red;">{{ loginFriends.length }}</b> 명이 <br/> 로그인중이에요
                <br/>
            </div>
              <img  src="@/assets/friends.png" @click="showFriends" style="width : 13%; margin-left : 80%; margin-bottom : 10%"/>        
        </div>
      </div>
      </div>
        <div class="background">
      <div class="part2">
          <img src="@/assets/board.png" class="board">
          <div class="two"> 
            <MyRanking style="z-index : 1" />
            <MokkojiRanking style="z-index : 1" />
          </div>
      </div>
      </div>
        <div class="background">
          <div class="part2">
          <img src="@/assets/computer.png" class="board">
          <div class="two"> 
            <StudyRoomRanking style="z-index : 1" />
          </div>
      </div>
        </div>
    </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import MyRanking from '@/components/home/MyRanking.vue';
import MokkojiRanking from '@/components/home/MokkojiRanking.vue';
import StudyRoomRanking from '@/components/home/StudyRoomRanking.vue';
import { useUserStore } from '@/stores/user';
import { useCategoryStore } from '@/stores/category';
import { useAlarmStore } from '@/stores/alarm';
import { useDagakStore } from '@/stores/dagak';
import { useFriendStore } from '@/stores/friend'

const arr = ref([
  " \"정보처리기사\"",
  "\"SQLD\"",
  "\"생활과윤리\"",
  "\"JLPT\"",
]);
const myGaks = ref([]);
const categories = ref([]);
const isFriendList = ref(false);
const userStore = useUserStore();
const categoryStore = useCategoryStore();
const alarmStore = useAlarmStore();
const router = useRouter();
const dagakStore = useDagakStore();
const friendStore = useFriendStore();
const loginFriends = computed(() => {
  return friendStore.loginFriends.filter(friend => friend.login);
});

const showFriends = ()=>{
  alert("친구들!");
  isFriendList.value = isFriendList.value == true?false:true;
};

const navigateToMyPageSchedule= () =>{
  router.push('/mypage');
};

const navigateToLogin = () => {
  alert("로그인해주세요!");
  router.push('/login');
};


const navigateToStudyRoom = () => {
  if(dagakStore.todayDagak.value == null)
    router.push('/studyroom');
};
const getGaks = async () =>{ 
  myGaks.value = dagakStore.todayDagak.gaks;
  categories.value = categoryStore.categoryList;
  arr.value = [];
  if (!(myGaks.value)) return;
  myGaks.value.forEach(gak =>{
    categories.value.forEach(category =>{
      if(gak.categoryId === category.categoryId){
        arr.value.push(`\"${category.categoryName}\"`);
        dagakStore.categoryNameToStudy.value = arr;
      }
    });
  });
};
onMounted(async () => {
  // store.login();
  if (userStore.loginUserInfo.userId != null) {
    alarmStore.getUnReadAlarmList();
    await categoryStore.getCategoryList();
    await dagakStore.getTodayDagak();
    await getGaks();
  }
});
watch(() => userStore.loginUserInfo.userId, (newUserId) => {
  if (newUserId != null) {
    getGaks();
  }
});
</script>

<style lang="scss" scoped>
.is-typed {
  user-select: none;
  
  
}
.is-typed:hover {
  cursor: pointer;
  color : black;
}
.title {
  font-size: 15rem;
  font-weight: bold;
  position: relative;
  z-index: 30;
  color: black;
  padding-top: 17%;
  padding-bottom: 0;
}

.title::before {
  content: attr(data-text);
  /* 글자 내용을 속성값으로 가져와서 적용 */
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
  color: white;
  /* 흰색으로 설정 */
  overflow: hidden;
  clip: rect(0, auto, 0, auto);
  /* 추가된 텍스트를 숨기기 위해 사용 */
}
.startbutton {
  // 기존 스타일 유지
  background-color: #639b9d;
  /* 적절한 배경색으로 변경 */
  bottom: 50px;
  z-index: 100;
  border: none;
  color: white;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  cursor: pointer;
  border-radius: 8px;
  /* 둥근 모서리 추가 */
  transition: background-color 0.3s;
  /* 부드러운 전환 효과 */

  &:hover {
    background-color: #3e6271;
    /* 마우스 호버 시 색상 변경 */
  }

  span {
    position: relative;
    z-index: 1;
  }

  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    // background: linear-gradient(45deg, #FF8C00, #FFD700); /* 그라데이션 효과 추가 */
    background: linear-gradient(45deg, #00ff7f, #48d1cc);
    /* 그라데이션 효과 추가 */
    z-index: -1;
    border-radius: 8px;
    /* 둥근 모서리에 그라데이션도 적용 */
    opacity: 0;
    transition: opacity 0.3s;
  }

  &:hover:before {
    opacity: 1;
  }
}

.parent {
  overflow: scroll;
  height: 100vh;
  scroll-snap-type: y mandatory;
  text-align: center;
  margin: 0 auto;
}

// 스크롤 숨기기
.parent::-webkit-scrollbar {
  display: none;
}

.part {
  width: 100%;
  scroll-snap-align: start;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh; /* 뷰포트 높이로 제한 */
  overflow: hidden; /* 페이지를 벗어나는 내용 숨김 */

}

.one {

  background-size: cover;
  background-position: center;
}

@keyframes move1h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 25vh;
    left: 65vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

@keyframes move1h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    left: 90vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move2h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 35vh;
    left: 80vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

@keyframes move3h {
  from {
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    left: 90vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move4h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 70vh;
    left: 80vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move5h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 65vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move5h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 85vw;
    width: 225px;
    height: 150px;
    // background-color:rgb(207, 207, 207);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move6h {
  from {
    top: 50vh;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 93vh;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move7h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 30vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move7h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 85vh;
    left: 10vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
  }
}

@keyframes move8h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 70vh;
    left: 20vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move9h {
  from {
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    left: 10vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move10h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 35vh;
    left: 20vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'Eulyoo1945';
  }
}

@keyframes move10h_1 {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    left: 10vw;
    width: 225px;
    height: 150px;
    // background-color:gainsboro;
    font-size: x-large;
    font-family: 'Roboto Mono', monospace;
  }
}

@keyframes move11h {
  from {
    top: 50vh;
    left: 50vw;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 25vh;
    left: 35vw;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
  }
}

@keyframes move12h {
  from {
    top: 50vh;
    width: 30px;
    height: 20px;
    // background-color:gray;
    font-size: 0;
  }

  to {
    top: 20vh;
    width: 150px;
    height: 100px;
    // background-color:rgb(190, 190, 190);
    font-size: x-large;
    font-family: 'ChosunGs';
  }
}

div[id^='square'] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 150px;
  height: 100px;
  // background-color: rgb(190, 190, 190);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  // box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  // border: 20px solid #000;
  // border-radius: 4px 4px 4px 4px;
  color: black;
}

div[id^='bsquare'] {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 225px;
  height: 150px;
  // background-color: rgb(255, 61, 61);
  text-align: center;
  line-height: 100px;
  z-index: 0;
  // box-shadow: 4px 4px 0px #acacac, 8px 8px 0px #d9d9d9, 12px 12px 0px #ffffff;
  // border: 20px solid #373737;
  // border-radius: 4px 4px 4px 4px;
}
.friends {
  width: 100%;
  height: auto;
  scroll-snap-align: start;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.two {
  background-size: cover;
  background-position: center;
  width: 70%;
  height: 50%;  
  justify-content: center;
  align-items: center;
  display: flex;
  align-items: flex-start;
}

.three {
  // background-color: rgb(195, 195, 195);
  background-size: cover;
  background-position: center;
}
.background {
  position: relative;
  width: 100%;
  height: 100vh;
  background: url('@/assets/background.gif') no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
.part2{
  width: 100%;
  height: 100vh;
  scroll-snap-align: start;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}
.board {
  margin-top: 10%;
  position: absolute; 
  z-index: 0; 
  width : 70% ; 
  height: 70%;
}


.font-weight-bold {
  margin-top: 10%;
}

.container {
  margin: 50px;
  display: block;
  width: 400px;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

$black: #000;
$white: #fff;
$shadow: rgba(0,0,0,0.1);

$px: 4px;

body {
	background-color:#6e6565;
	padding: 60px;
}

$bubble-border: 0 -1*$px $white, 
		0 -2*$px $black, 
		$px 0 $white, 
		$px -1*$px $black, 
		2*$px 0 $black, 
		0 $px $white, 
		0 2*$px $black, 
		-1*$px 0 $white, 
		-1*$px  $px $black, 
		-2*$px 0 $black, 
		-1*$px -1*$px $black, 
		$px $px $black;

.bubble {
	position: relative;
	display: inline-block;
	text-align: center;
	font-size: 16px;
	line-height:1.3em;
	letter-spacing: -0.04em;
	background-color: $white;
	color: $black;
	padding: 3*$px;
	box-shadow: $bubble-border;
		
	box-sizing: border-box;
	width:200px;

	&::after {
		content: '';
		display: block;
		position: absolute;
		box-sizing: border-box;	
	}

	&.medium { width:10%; }
	
	&.top::after {
		height: $px;
		width: $px;
		top: -2*$px;
		left: 8*$px;
		box-shadow: 
			0 -1*$px $black, 
			0 -2*$px $black, 
			0 -3*$px $black, 
			0 -4*$px $black, 
			-1*$px -3*$px $black, 
			-2*$px -2*$px $black, 
			-3*$px -1*$px $black, 
			-1*$px -1*$px $white, 
			-2*$px -1*$px $white, 
			-1*$px -2*$px $white, 
			-1*$px 0 $white, 
			-2*$px 0 $white, 
			-3*$px 0 $white;
	}
	
	&.right::after {
		height: $px;
		width: $px;
		top: 21*$px;
		right: -2*$px;
		background: white;
		box-shadow: 
			1*$px -1*$px $white,
			1*$px 0 $white,
			2*$px 0 $white,
			0 -2*$px $white,
			1*$px 1*$px $black, 
			2*$px 1*$px $black, 
			3*$px 1*$px $black, 
			4*$px 1*$px $black,
			3*$px 0 $black, 
			2*$px -1*$px $black, 
			1*$px -2*$px $black,
			0 -1*$px $white;
	}
	
	&.bottom::after {
		height: $px;
		width: $px;
		bottom: -2*$px;
		left: 6*$px;
		box-shadow: 
			0 $px $black, 
			0 2*$px $black, 
			0 3*$px $black, 
			0 4*$px $black, 
			-1*$px 3*$px $black, 
			-2*$px 2*$px $black, 
			-3*$px 1*$px $black, 
			-1*$px $px $white, 
			-2*$px $px $white, 
			-1*$px 2*$px $white, 
			-1*$px 0 $white, 
			-2*$px 0 $white, 
			-3*$px 0 $white;
	}
	
	&.left::after {
		height: $px;
		width: $px;
		top: 5*$px;
		left: -2*$px;
		background: white;
		box-shadow: 
			-1*$px -1*$px $white,
			-1*$px 0 $white,
			-2*$px 0 $white,
			0 -2*$px $white,
			-1*$px 1*$px $black, 
			-2*$px 1*$px $black, 
			-3*$px 1*$px $black, 
			-4*$px 1*$px $black,
			-3*$px 0 $black, 
			-2*$px -1*$px $black, 
			-1*$px -2*$px $black,
			0 -1*$px $white;
	}
}



</style>

