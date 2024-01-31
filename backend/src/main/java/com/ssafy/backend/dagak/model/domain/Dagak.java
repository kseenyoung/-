package com.ssafy.backend.dagak.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdDate, updatedDate;

    public Dagak(Integer dagak_id, String user_id, Integer total_time, LocalDateTime created_date, LocalDateTime updated_date) {
        this.dagakId = dagak_id;
        this.userId = user_id;
        this.totalTime = total_time;
        this.createdDate = created_date;
        this.updatedDate = updated_date;
    }

    public Dagak() {
    }
}
