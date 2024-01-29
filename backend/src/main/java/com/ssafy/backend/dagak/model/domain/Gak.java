package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Builder
public class Gak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer gakId;

    @Column
    private Integer dagakId, categoryId, order, runningTime;

    @Column
    private String userId;

    public Gak() {
    }

    public Gak(Integer gakId, Integer dagakId, Integer categoryId, Integer order, Integer runningTime, String userId) {
        this.gakId = gakId;
        this.dagakId = dagakId;
        this.categoryId = categoryId;
        this.order = order;
        this.runningTime = runningTime;
        this.userId = userId;
    }
}
