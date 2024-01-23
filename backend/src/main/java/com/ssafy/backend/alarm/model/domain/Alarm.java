package com.ssafy.backend.alarm.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
}
