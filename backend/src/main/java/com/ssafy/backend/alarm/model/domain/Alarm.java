package com.ssafy.backend.alarm.model.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicInsert
public class Alarm {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alarmId;

    @Column
    private String userId, createdDate, requestedUserId;

    @Column
    private Integer tagId, isChecked;

    public Alarm() {
    }

    public Alarm(Integer alarmId, String userId, String createdDate, String requestedUserId, Integer tagId, Integer isChecked) {
        this.alarmId = alarmId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.requestedUserId = requestedUserId;
        this.tagId = tagId;
        this.isChecked = isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
}
