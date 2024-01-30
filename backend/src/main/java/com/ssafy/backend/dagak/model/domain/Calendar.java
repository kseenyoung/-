package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
public class Calendar {

    @Id
    @Column
    private Integer calendarDagakId;

    @Column
    private Integer dagakId;

    @Column
    private String userId;

    @Column
    private LocalDateTime calendarDate;

    public Calendar() {
    }

    public Calendar(Integer calendarDagakId, Integer dagakId, String userId, LocalDateTime calendarDate) {
        this.calendarDagakId = calendarDagakId;
        this.dagakId = dagakId;
        this.userId = userId;
        this.calendarDate = calendarDate;
    }
}
