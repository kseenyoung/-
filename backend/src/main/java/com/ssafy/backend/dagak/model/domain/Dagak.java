package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@DynamicInsert
public class Dagak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer dagakId;

    @Column
    private String userId, dagakName;

    @Column
    private Integer totalTime;

    @Column
    private String createdDate, updatedDate;

    public Dagak(Integer dagakId, String userId, String dagakName, Integer totalTime, String createdDate, String updatedDate) {
        this.dagakId = dagakId;
        this.userId = userId;
        this.dagakName = dagakName;
        this.totalTime = totalTime;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Dagak() {
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "Dagak{" +
                "dagakId=" + dagakId +
                ", userId='" + userId + '\'' +
                ", totalTime=" + totalTime +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}
