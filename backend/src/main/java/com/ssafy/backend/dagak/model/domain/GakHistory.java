package com.ssafy.backend.dagak.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GakHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer historyId;

    @Column
    private Integer categoryId, gakId, calendarId, memoryTime;

    @Column
    private LocalDate createdDate, updatedDate;

    @Column
    private String userId;
}
