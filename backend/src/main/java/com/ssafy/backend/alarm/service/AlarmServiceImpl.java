package com.ssafy.backend.alarm.service;

import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;
import com.ssafy.backend.alarm.model.repository.AlarmRepository;
import com.ssafy.backend.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_ALARM_ID;

@Service
@Slf4j
public class AlarmServiceImpl implements  AlarmService {

    @Autowired
    AlarmRepository alarmRepository;

    @Override
    public void requestAlarm(ReqestAlarmDto reqestAlarmDto) {
        alarmRepository.save(
                Alarm.builder()
                        .tagId(reqestAlarmDto.getTagId())
                        .userId(reqestAlarmDto.getUserId())
                        .isChecked(0)
                        .requestedUserId(reqestAlarmDto.getRequestedUserId())
                        .build());
    }
    @Override
    public void checkAlarm(CheckAlarmDto checkAlarmDto) {
        Alarm alarm = alarmRepository.findById(checkAlarmDto.getAlarmId()).orElseThrow(() -> new BaseException(NOT_EXIST_ALARM_ID));
        alarm.setIsChecked(1);

//        log.info("Alarm id : {}", alarm.getAlarmId());
        alarmRepository.save(alarm);

    }

    @Override
    public List<Alarm> listofAllAlarm(String userId) {

        List<Alarm> allByUserId = alarmRepository.findAllByUserId(userId);

        return allByUserId;
    }

    @Override
    public List<Alarm> listOfUncheckedAlarm(String userId) {

        List<Alarm> byUserIdAndIsChecked = alarmRepository.findByUserIdAndIsChecked(userId, 0);

        return byUserIdAndIsChecked;
    }
}
