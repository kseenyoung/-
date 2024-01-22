package com.ssafy.backend.alarm.service;

import com.ssafy.backend.alarm.mode.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.ListOfAllAlarmDto;
import com.ssafy.backend.alarm.model.dto.ListOfUncheckedtAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;

import java.util.List;

public interface AlarmService {


    void requestAlarm(ReqestAlarmDto reqestAlarmDto);

    void checkAlarm(CheckAlarmDto checkAlarmDto);

    List<Alarm> listofAllAlarm(ListOfAllAlarmDto listOfAllAlarmDto);

    List<Alarm> listOfUncheckedAlarm(ListOfUncheckedtAlarmDto listOfUncheckedAlarmDto);
}
