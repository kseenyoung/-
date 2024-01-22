package com.ssafy.backend.alarm.model.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Alarm {

    @Id
    @Column
    private Integer alarmId;

    @Column
    private String userId, createdDate, requestedUserId;

    @Column
    private Integer tagId, isChecked;

}
