package com.ssafy.backend.alarm.service;

import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;

import java.util.List;

public interface AlarmService {

    Integer findAlarmId(String userId, String requestedUserId, int tagId);

    void requestAlarm(ReqestAlarmDto reqestAlarmDto);

    void checkAlarm(CheckAlarmDto checkAlarmDto);

    List<Alarm> listofAllAlarm(String userId);

    List<Alarm> listOfUncheckedAlarm(String userId);

    void aVoidDuplicateAlaram(ReqestAlarmDto reqestAlarmDto);

    void deleteAlarm(ReqestAlarmDto alarmDto);

    boolean isAlreadyRequestFriend(String userId, String requestedUserId);
}
