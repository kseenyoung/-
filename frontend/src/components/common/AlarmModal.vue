<template>
  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">
            <i class="bi bi-alarm"></i>
            알림
            <span>{{ alarmStore.alarmUnReadTotal }}</span>
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>

        <div class="modal-body">
          <div
            v-if="alarmStore.alarmUnReadList.length === 0"
            class="hasNoAlarm"
          >
            읽지 않은 알림이 없습니다.
          </div>
          <div
            v-else
            v-for="alarm in alarmStore.alarmUnReadList"
            :key="alarm.alarmId"
            class="alarm-wrapper"
            :class="{ 'alarm-new': alarm.isChecked === 0 }"
          >
            <div class="alarm-check">
              <i class="bi bi-envelope"></i>
            </div>
            <div class="alarm-content">
              <div class="alarm-content-header">
                <div>{{ getAlarmTag(alarm.tagId) }}</div>
                <div>{{ alarm.createdDate }}</div>
              </div>
              <div class="alarm-content-body">
                <div
                  v-if="
                    alarm.tagId === 2 || alarm.tagId === 4 || alarm.tagId === 5
                  "
                >
                  {{ alarm.requestedUserId }}님의
                  {{ getAlarmMessage(alarm.tagId) }}
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
              >
                <i class="bi bi-check2"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { useAlarmStore } from '@/stores/alarm';

const alarmStore = useAlarmStore();

//알림 확인
const checkAlarm = async function (alarmId) {
  const body = {
    sign: 'checkAlarm',
    alarmId: alarmId,
  };

  await axios.post(`${import.meta.env.VITE_API_BASE_URL}alarms`, body, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  alarmStore.getUnReadAlarmList();
};

const accessAlarm = async function (tagId, requestedUserId) {
  console.log(requestedUserId);
  if (tagId === 2) {
    //모꼬지 승인 API
    const body = {
      sign: 'accessMokkoji',
      memberId: requestedUserId,
    };
    await axios
      .post(`${import.meta.env.VITE_API_BASE_URL}mokkoji`, body, {
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
      .post(`${import.meta.env.VITE_API_BASE_URL}friend`, body, {
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
.modal-title {
  display: flex;
  align-items: center;
  > i {
    margin-right: 5px;
  }
  > span {
    display: inline-block;
    background-color: $color-light-5;
    color: white;
    width: 30px;
    border-radius: 50%;
    text-align: center;
    margin-left: 2px;
  }
}
.modal-body {
  padding: 0px;
  .hasNoAlarm {
    padding: 30px 20px;
  }
  .alarm-wrapper:last-child {
    border-bottom: none;
  }
  .alarm-wrapper {
    padding: 15px 30px;
    border-bottom: 1px solid #ccc;
    display: flex;
    .alarm-check {
      flex-basis: 5%;
      i {
        color: $color-light-5;
        position: relative;
        top: -3px;
        left: -3px;
      }
    }
    .alarm-content {
      display: flex;
      flex-direction: column;
      flex-grow: 1;

      .alarm-content-header {
        display: flex;
        align-items: center;

        div:nth-child(1) {
          color: $color-light-5;
          font-weight: 600;
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
