package com.ssafy.backend.dagak.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
public class GakHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer historyId;

    @Column
    private Integer categoryId, gakId, calendarId, memoryTime;

    @Column
    private LocalDate createdDate, updatedDate;

    @Column
    private String userId;

    public GakHistory() {
    }

    public GakHistory(Integer historyId, Integer categoryId, Integer gakId, Integer calendarId, Integer memoryTime, LocalDate createdDate, LocalDate updatedDate, String userId) {
        this.historyId = historyId;
        this.categoryId = categoryId;
        this.gakId = gakId;
        this.calendarId = calendarId;
        this.memoryTime = memoryTime;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userId = userId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setGakId(Integer gakId) {
        this.gakId = gakId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public void setMemoryTime(Integer memoryTime) {
        this.memoryTime = memoryTime;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GakHistory{" +
                "historyId=" + historyId +
                ", categoryId=" + categoryId +
                ", gakId=" + gakId +
                ", calendarId=" + calendarId +
                ", memoryTime=" + memoryTime +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}
