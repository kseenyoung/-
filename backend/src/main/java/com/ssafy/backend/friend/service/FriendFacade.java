package com.ssafy.backend.friend.service;

import com.ssafy.backend.alarm.model.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;
import com.ssafy.backend.alarm.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FriendFacade {

    @Autowired
    FriendService friendService;

    @Autowired
    AlarmService alarmService;

    @Transactional
    public void requestFriend(String userId, String userId2){
        friendService.requestFriend(userId, userId2);
        alarmService.requestAlarm(new ReqestAlarmDto(userId, userId2, 3));
    }

    @Transactional
    public void accessFriend(String userId, String userId2) {
        friendService.accessFriend(userId, userId2);
        // 친구 요청 승인 알람 보내기
        alarmService.requestAlarm(new ReqestAlarmDto(userId, userId2, 4));
        // 삭제 할 친구 요청 '알람 아이디' 찾기
        int alarmId = alarmService.findAlarmId(userId, userId2, 3);
        // 해당 '알람 아이디' 삭제?? 체크표시??
        alarmService.checkAlarm(new CheckAlarmDto(userId, alarmId));
    }
}