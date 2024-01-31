package com.ssafy.backend.dagak.model.vo;

import com.ssafy.backend.dagak.model.domain.Gak;

import java.util.List;

public class CalendarDagakVO {
    private Integer calendarDagakId;
    private Integer dagakId;
    private String calendarDate;
    private List<Gak> gaks;

    public Integer getCalendarDagakId() {
        return calendarDagakId;
    }

    public Integer getDagakId() {
        return dagakId;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public List<Gak> getGaks() {
        return gaks;
    }

    public void setGaks(List<Gak> gaks) {
        this.gaks = gaks;
    }

    public CalendarDagakVO(Integer calendarDagakId, Integer dagakId, String calendarDate) {
        this.calendarDagakId = calendarDagakId;
        this.calendarDate = calendarDate;
        this.dagakId = dagakId;
    }

    @Override
    public String toString() {
        return "CalendarDagakVO{" +
                "calendarDagakId=" + calendarDagakId +
                ", dagakId=" + dagakId +
                ", calendarDate=" + calendarDate +
                ", gaks=" + gaks +
                '}';
    }
}
