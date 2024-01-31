package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDate;


@Entity
@Builder
@Getter
@ToString
public class Calendar {

    @Id
    @Column
    private Integer calendarDagakId;

    @Column
    private Integer dagakId;

    @Column
    private String userId;

    @Column
    private LocalDate calendarDate;

    public Calendar() {
    }

    public Calendar(Integer calendarDagakId, Integer dagakId, String userId, LocalDate calendarDate) {
        this.calendarDagakId = calendarDagakId;
        this.dagakId = dagakId;
        this.userId = userId;
        this.calendarDate = calendarDate;
    }
}
