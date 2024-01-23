package com.ssafy.backend.friend.service;

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

}
