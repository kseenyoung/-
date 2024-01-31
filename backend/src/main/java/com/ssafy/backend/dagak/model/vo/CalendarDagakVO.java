package com.ssafy.backend.dagak.model.vo;

import com.ssafy.backend.dagak.model.domain.Gak;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
public class CalendarDagakVO {
    private Integer calendarDagakId;
    private Integer dagakId;
    private String calendarDate;
    private List<Gak> gaks;
    private String userId;

    public CalendarDagakVO(Integer calendarDagakId, Integer dagakId, LocalDate calendarDate) {
    }

    public Integer getCalendarDagakId() {
        return calendarDagakId;
    }
    public CalendarDagakVO() {
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
