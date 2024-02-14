package com.ssafy.backend.alarm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDTO;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDTO;
import com.ssafy.backend.alarm.model.repository.AlarmRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.utils.RequestMessage;
import com.ssafy.backend.user.model.dto.OpenviduRequestDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@Service
@Slf4j
public class AlarmServiceImpl implements  AlarmService {

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    RequestMessage requestMessage;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Integer findAlarmId(String userId, String requestedUserId, int tagId) {
        return alarmRepository.findOptionalAlarmByUserIdAndRequestedUserIdAndTagIdAndIsChecked(userId, requestedUserId, tagId,0).orElseThrow(
                () -> {throw new BaseException(NOT_EXIST_ALARM_ID);}
        ).getAlarmId();
    }

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public void requestAlarm(ReqestAlarmDTO reqestAlarmDto) {
        Alarm alarm = Alarm.builder()
                .tagId(reqestAlarmDto.getTagId())
                .userId(reqestAlarmDto.getUserId())
                .isChecked(0)
                .requestedUserId(reqestAlarmDto.getRequestedUserId())
                .build();
        alarmRepository.save(alarm);

            requestMessage.RequestOpenviduMessage(
                    OpenviduRequestDTO.builder()
                            .session(reqestAlarmDto.getUserId())
                            .type("alarm")
                            .data(objectMapper.writeValueAsString(alarm))
                            .build());
    }
    @Override
    public void checkAlarm(CheckAlarmDTO checkAlarmDto) {
        Alarm alarm = alarmRepository.findById(checkAlarmDto.getAlarmId()).orElseThrow(() -> new BaseException(NOT_EXIST_ALARM_ID));
        alarm.setIsChecked(1);

//        log.info("Alarm id : {}", alarm.getAlarmId());
        alarmRepository.save(alarm);

    }

    @Override
    public List<Alarm> getAllAlarmList(String userId) {

        List<Alarm> allByUserId = alarmRepository.findAllByUserIdOrderByIsCheckedAscCreatedDateDesc(userId);

        return allByUserId;
    }

    @Override
    public List<Alarm> getUncheckAlarmList(String userId) {

        List<Alarm> byUserIdAndIsChecked = alarmRepository.findByUserIdAndIsChecked(userId, 0);

        return byUserIdAndIsChecked;
    }

    @Override
    public void aVoidDuplicateAlaram(ReqestAlarmDTO reqestAlarmDto,int checked) {
        alarmRepository.findOptionalAlarmByUserIdAndRequestedUserIdAndTagIdAndIsChecked(
                reqestAlarmDto.getUserId(),
                reqestAlarmDto.getRequestedUserId(),
                reqestAlarmDto.getTagId(), checked).ifPresent(
                        e -> {
                            throw new BaseException(AVOID_DUPLICATE_ALARM);
                        });
    }

    @Override
    public void deleteAlarm(ReqestAlarmDTO alarmDto) {
        System.out.println(alarmDto);
        Alarm alarm = alarmRepository.findOptionalAlarmByUserIdAndRequestedUserIdAndTagIdAndIsChecked(
                alarmDto.getUserId(),
                alarmDto.getRequestedUserId(),
                alarmDto.getTagId(),
                0
        ).orElseThrow(() -> new BaseException(ALREADY_DELETE_ALARM));
        alarmRepository.delete(alarm);

    }

    @Override
    public boolean isAlreadyRequestFriend(String userId, String requestedUserId) {
        Alarm byUserIdAndRequestedUserIdAndTagIdAndIsChecked
                = alarmRepository.findByUserIdAndRequestedUserIdAndTagIdAndIsChecked(userId, requestedUserId, 4, 0);

        if(byUserIdAndRequestedUserIdAndTagIdAndIsChecked != null)
            return true;
        return false;
    }
}
