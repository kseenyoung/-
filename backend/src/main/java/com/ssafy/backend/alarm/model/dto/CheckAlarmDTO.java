package com.ssafy.backend.alarm.model.dto;


import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_ALARM_ID;

@Getter
public class CheckAlarmDTO {

    private String userId;

    private Integer alarmId;

    public CheckAlarmDTO() {
    }

    public CheckAlarmDTO(String userId, String alarmId) {
        setUserId(userId);
        setAlarmId(alarmId);
    }

    public CheckAlarmDTO(String userId, int alarmId) {
        setUserId(userId);
        this.alarmId = alarmId;
    }

    public void setUserId(String userId) {
        // TODO 유저 아이디 존재 여부
        this.userId = userId;
    }

    public void setAlarmId(String alarmId) {
        try{
            this.alarmId = Integer.parseInt(alarmId);
        } catch (RuntimeException e){
            throw new BaseException(NOT_EXIST_ALARM_ID);
        }
    }
}
