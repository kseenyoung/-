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
    private Integer dagak_id;

    @Column
    private String user_id;

    @Column
    private Integer total_time;

    @Column
    private LocalDateTime created_date, updated_date;

    public Dagak(Integer dagak_id, String user_id, Integer total_time, LocalDateTime created_date, LocalDateTime updated_date) {
        this.dagak_id = dagak_id;
        this.user_id = user_id;
        this.total_time = total_time;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Dagak() {
    }
}
