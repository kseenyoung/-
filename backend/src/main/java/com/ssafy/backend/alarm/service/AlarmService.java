package com.ssafy.backend.alarm.service;

import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDTO;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDTO;

import java.util.List;

public interface AlarmService {

    Integer findAlarmId(String userId, String requestedUserId, int tagId);

    void requestAlarm(ReqestAlarmDTO reqestAlarmDto);

    void checkAlarm(CheckAlarmDTO checkAlarmDto);

    List<Alarm> getAllList(String userId);

    List<Alarm> getUncheckList(String userId);

    void aVoidDuplicateAlaram(ReqestAlarmDTO reqestAlarmDto);

    void deleteAlarm(ReqestAlarmDTO alarmDto);

    boolean isAlreadyRequestFriend(String userId, String requestedUserId);
}
