package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
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


}
