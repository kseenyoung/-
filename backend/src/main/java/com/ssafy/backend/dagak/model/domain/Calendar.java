package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Builder
@Getter
@ToString
@Setter
public class Calendar {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
