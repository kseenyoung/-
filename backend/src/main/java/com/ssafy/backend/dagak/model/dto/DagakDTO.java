package com.ssafy.backend.dagak.model.dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
public class DagakDTO {

    @Id
    @Column
    private Integer dagakId;

    @Column
    private String userId;

    @Column
    private Integer totalTime;

    public DagakDTO() {
    }

    public DagakDTO(String userId, Integer totalTime) {
        this.userId = userId;
        this.totalTime = totalTime;
    }

    public void setDagakId(Integer dagakId) {
        this.dagakId = dagakId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "DagakDTO{" +
                "dagakId=" + dagakId +
                ", userId='" + userId + '\'' +
                ", totalTime=" + totalTime +
                '}';
    }
}
