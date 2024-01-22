package com.ssafy.backend.alarm.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ReqestAlarmDto {

    private String userId, requestedUserId;
    private Integer tagId;

}
