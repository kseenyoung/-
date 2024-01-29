package com.ssafy.backend.dagak.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
public class GakDto {

    @Id
    @Column
    private Integer gakId;

    @Column
    private Integer dagakId, categoryId, order, runningTime;

    @Column
    private String userId;

}
