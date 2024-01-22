package com.ssafy.backend.mokkoji.model.domain;

import com.ssafy.backend.common.model.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Mokkoji extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mokkojiId;

    @Column(columnDefinition = "varchar(15)")
    private String mokkojiName;

    @Column(columnDefinition = "varchar(20)")
    private String leaderId;

    @Column(columnDefinition = "char(60)")
    private String mokkojiStatus;

    @Builder
    public Mokkoji(String mokkojiName, String leaderId,String mokkojiStatus) {
        this.mokkojiName = mokkojiName;
        this.leaderId = leaderId;
        this.mokkojiStatus = mokkojiStatus;
    }
}