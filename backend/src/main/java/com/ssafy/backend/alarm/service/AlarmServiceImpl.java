package com.ssafy.backend.alarm.service;

import com.ssafy.backend.alarm.mode.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.ListOfAllAlarmDto;
import com.ssafy.backend.alarm.model.dto.ListOfUncheckedtAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;
import com.ssafy.backend.alarm.model.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements  AlarmService{

    @Autowired
    AlarmRepository alarmRepository;

    @Override
    public void requestAlarm(ReqestAlarmDto reqestAlarmDto) {
    }

    @Override
    public void checkAlarm(CheckAlarmDto checkAlarmDto) {

    }

    @Override
    public List<Alarm> listofAllAlarm(ListOfAllAlarmDto listOfAllAlarmDto) {

        return null;
    }

    @Override
    public List<Alarm> listOfUncheckedAlarm(ListOfUncheckedtAlarmDto listOfUncheckedAlarmDto) {

        return null;
    }
}
