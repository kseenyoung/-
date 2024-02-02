package com.ssafy.backend.dagak.model.domain;

import com.ssafy.backend.dagak.model.dto.GakDTO;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Builder
@Getter
public class Gak {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Integer gakId;

    @Column
    private Integer dagakId, categoryId, gakOrder, runningTime;

    @Column
    private String userId;

    public Gak() {
    }

    public Gak(Integer gakId, Integer dagakId, Integer categoryId, Integer gakOrder, Integer runningTime, String userId) {
        this.gakId = gakId;
        this.dagakId = dagakId;
        this.categoryId = categoryId;
        this.gakOrder = gakOrder;
        this.runningTime = runningTime;
        this.userId = userId;
    }

    public GakDTO entiryToGakDto(){
        return new GakDTO(categoryId, gakOrder, runningTime, userId);
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setRunningTime(Integer runningTime) {
        this.runningTime = runningTime;
    }

    public void setGakOrder(Integer gakOrder) {
        this.gakOrder = gakOrder;
    }

    @Override
    public String toString() {
        return "Gak{" +
                "gakId=" + gakId +
                ", dagakId=" + dagakId +
                ", categoryId=" + categoryId +
                ", gakOrder=" + gakOrder +
                ", runningTime=" + runningTime +
                ", userId='" + userId + '\'' +
                '}';
    }
}
