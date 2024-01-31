package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@ToString
@Getter
@Builder
public class Dagak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer dagakId;

    @Column
    private String userId;

    @Column
    private Integer totalTime;

    @Column
    private String createdDate, updatedDate;

    public Dagak(Integer dagakId, String userId, Integer totalTime, String createdDate, String updatedDate) {
        this.dagakId = dagakId;
        this.userId = userId;
        this.totalTime = totalTime;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Dagak() {
    }
}
