package com.ssafy.backend.dagak.model.dto;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@ToString
public class DagakDto {

    @Id
    @Column
    private Integer dagakId;

    @Column
    private String userId;

    @Column
    private Integer totalTime;

    public DagakDto(String userId, Integer totalTime) {
        this.userId = userId;
        this.totalTime = totalTime;
    }
}
