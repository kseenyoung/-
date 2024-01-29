package com.ssafy.backend.dagak.model.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
public class Dagak {

    @Id
    @Column
    private Integer dagak_id;

    @Column
    private String user_id;

    @Column
    private Integer total_time;

    @Column
    private LocalDateTime created_date, updated_date;
}
