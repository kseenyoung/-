package com.ssafy.backend.dagak.model.vo;

import com.ssafy.backend.dagak.model.domain.Gak;

import java.time.LocalDateTime;
import java.util.List;

public class CalendarDagakVO {
    private Integer dagakId;
    private LocalDateTime calendarDate;
    private List<Gak> gaks;

    public Integer getDagakId() {
        return dagakId;
    }

    public void setGaks(List<Gak> gaks) {
        this.gaks = gaks;
    }

    public CalendarDagakVO(Integer dagakId, LocalDateTime calendarDate) {
        this.dagakId = dagakId;
        this.calendarDate = calendarDate;
    }

    @Override
    public String toString() {
        return "CalendarDagakVO{" +
                "dagakId=" + dagakId +
                ", calendarDate=" + calendarDate +
                ", gaks=" + gaks +
                '}';
    }
}
