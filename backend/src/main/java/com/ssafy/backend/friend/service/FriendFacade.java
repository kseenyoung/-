package com.ssafy.backend.friend.service;

import com.ssafy.backend.alarm.model.dto.CheckAlarmDTO;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDTO;
import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.friend.model.domain.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.ssafy.backend.common.response.BaseResponseStatus.ALREADY_EXIST_ALARM;
import static com.ssafy.backend.common.response.BaseResponseStatus.ALREADY_EXIST_FRIEND;

@Service
public class FriendFacade {

    @Autowired
    FriendService friendService;

    @Autowired
    AlarmService alarmService;

    @Transactional
    public void requestFriend(String userId, String userId2){
        // 이미 친구인지 확인
        if(friendService.isFriend(new UserId(userId, userId2))){
            throw new BaseException(ALREADY_EXIST_FRIEND);
        }
        // 이미 친구 요청을 했는지 확인(알람 : userId=ssafy, requestedUserId=ssafy123, type=4, isChecked=0)
        if(alarmService.isAlreadyRequestFriend(userId, userId2))
            throw new BaseException(ALREADY_EXIST_ALARM);

        friendService.requestFriend(userId, userId2);
        alarmService.requestAlarm(new ReqestAlarmDTO(userId, userId2, 4));
    }

    @Transactional
    public void accessFriend(String userId, String userId2) {
        // 친구 됨
        friendService.accessFriend(userId, userId2);
        // 친구 요청 승인 알람 보내기
        alarmService.requestAlarm(new ReqestAlarmDTO(userId2, userId, 5));
        // 체크 표시 할 친구 요청 '알람 아이디' 찾기
        int alarmId = alarmService.findAlarmId(userId, userId2, 4);
        // 해당 '알람 아이디' 삭제?? 체크표시??
        alarmService.checkAlarm(new CheckAlarmDTO(userId, alarmId));
    }
}