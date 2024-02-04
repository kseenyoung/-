package com.ssafy.backend.dagak.model.vo;

import com.ssafy.backend.dagak.model.domain.Gak;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CalendarDagakVO {
    private Integer calendarDagakId;
    private Integer dagakId;
    private LocalDate calendarDate;
    private List<Gak> gaks;
    private String userId;

    public CalendarDagakVO() {
    }

    public CalendarDagakVO(Integer calendarDagakId, Integer dagakId, LocalDate calendarDate) {
        setCalendarDagakId(calendarDagakId);
        setDagakId(dagakId);
        setCalendarDate(calendarDate);
    }

    public void setGaks(List<Gak> gaks) {
        this.gaks = gaks;
    }

    public void setCalendarDagakId(Integer calendarDagakId) {
        this.calendarDagakId = calendarDagakId;
    }

    public void setDagakId(Integer dagakId) {
        this.dagakId = dagakId;
    }

    public void setCalendarDate(LocalDate calendarDate) {
        this.calendarDate = calendarDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
