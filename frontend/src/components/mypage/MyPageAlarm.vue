<template>
  <div class="common-mypage-wrapper">
    <div class="common-mypage-title">내 알림</div>
    <div class="alarm-content-wrapper">
      <div class="alarm-content-total">
        <i class="bi bi-alarm"></i> {{ alarmTotal }}개
      </div>
      <!-- 
        게시글(1), 모꼬지 신청(2), 모꼬지 승인(3),  친구 신청(4), 친구 승인(5),  답변(6), DM(7)
        모꼬지, 친구 승인은 수락, 거절 버튼 2개. 나머지 확인버튼 1개
      -->

      <div
        v-for="alarm in alarmList"
        :key="alarm.alarmId"
        class="alarm-wrapper"
        :class="{ 'alarm-new': alarm.isChecked === 0 }"
      >
        <div class="alarm-check">
          <i
            :class="
              alarm.isChecked === 0 ? 'bi bi-envelope' : 'bi bi-envelope-paper'
            "
          ></i>
        </div>
        <div class="alarm-content">
          <div class="alarm-content-header">
            <div>{{ getAlarmTag(alarm.tagId) }}</div>
            <div>{{ alarm.createdDate }}</div>
          </div>
          <div class="alarm-content-body">
            <div v-if="alarm.tagId === 2 || alarm.tagId === 4">
              {{ alarm.requestedUserId }}님의 {{ getAlarmMessage(alarm.tagId) }}
            </div>
            <div v-else>
              {{ getAlarmMessage(alarm.tagId) }}
            </div>
            <!-- <div>ssafy님의 길드가입 신청</div> -->
          </div>
        </div>
        <div class="alarm-btn">
          <button
            v-if="alarm.tagId === 2 || alarm.tagId === 4"
            class="btn common-btn"
            :disabled="alarm.isChecked != 0"
            @click="accessAlarm(alarm.tagId, alarm.requestedUserId)"
          >
            수락
          </button>
          <button
            v-if="alarm.tagId === 2 || alarm.tagId === 4"
            class="btn common-btn"
            :disabled="alarm.isChecked != 0"
            @click="checkAlarm(alarm.alarmId)"
          >
            거절
          </button>
          <button
            v-else
            class="btn common-btn"
            @click="checkAlarm(alarm.alarmId)"
            :disabled="alarm.isChecked != 0"
          >
            <i class="bi bi-check2"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// 게시글(1), 모꼬지 신청(2), 모꼬지 승인(3),  친구 신청(4), 친구 승인(5),  답변(6), DM(7)
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useAlarmStore } from '@/stores/alarm';

const alarmStore = useAlarmStore();

const alarmTotal = ref();
const alarmList = ref([]);

onMounted(() => {
  getAlarmList();
});

//전체 알람 목록 불러오기
const getAlarmList = function () {
  axios.get('https://localhost:8080/dagak/alarms/listOfAll').then((res) => {
    alarmTotal.value = res.data.result.length;
    alarmList.value = res.data.result;
  });
};

//알림 확인
const checkAlarm = async function (alarmId) {
  const body = {
    sign: 'check',
    alarmId: alarmId,
  };
  await axios
    .post('https://localhost:8080/dagak/alarms', body, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((res) => res.data)
    .then(() => {});
  getAlarmList();
  alarmStore.getUnReadAlarmList();
};

const accessAlarm = async function (tagId, requestedUserId) {
  console.log(requestedUserId);
  if (tagId === 2) {
    //모꼬지 승인 API
    const body = {
      sign: 'AcceptMokkoji',
      memberId: requestedUserId,
    };
    await axios
      .post('https://localhost:8080/dagak/mokkoji', body, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then((res) => {
        if (res.data.code === 1105) {
          //성공
          alert(res.data.message);
        } else if (res.data.code === 2100) {
          //이미 모꼬지가 있는 유저
          alert(res.data.message);
        }
      });
  } else if (tagId === 4) {
    //친구 승인 API
    const body = {
      sign: 'accessFriend',
      userId: requestedUserId,
    };
    await axios
      .post('https://localhost:8080/dagak/friend', body, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then((res) => {
        if (res.data.code === 1000) {
          //성공
          alert('친구가 되었습니다');
        }
      });
  } else {
    alert(tagId + ': tagId가 잘못되었습니다.');
  }
  getAlarmList();
  alarmStore.getUnReadAlarmList();
};

const getAlarmTag = (tagId) => {
  switch (tagId) {
    case 1:
      return '게시글';
    case 2:
      return '모꼬지 신청';
    case 3:
      return '모꼬지 승인';
    case 4:
      return '친구 신청';
    case 5:
      return '친구 승인';
    case 6:
      return '답변';
    case 7:
      return 'DM';
  }
};
const getAlarmMessage = (tagId) => {
  switch (tagId) {
    case 1:
      return '게시글에 댓글이 달렸습니다';
    case 2:
      return '모꼬지 신청';
    case 3:
      return '모꼬지 신청이 승인되었습니다';
    case 4:
      return '친구 신청';
    case 5:
      return '친구 신청이 승인되었습니다';
    case 6:
      return '질문에 답변이 달렸습니다';
    case 7:
      return 'DM이 왔습니다';
  }
};
</script>

<style lang="scss" scoped>
.alarm-content-wrapper {
  font-size: 1.05rem;
  margin: 0px 40px;
  overflow-y: auto;
  max-height: 600px;
  .alarm-content-total {
    font-size: 1.5rem;
  }
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
