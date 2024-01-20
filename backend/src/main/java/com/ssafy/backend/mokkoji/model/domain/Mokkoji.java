package com.ssafy.backend.mokkoji.model.domain;

import com.ssafy.backend.common.model.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "mokkoji")
    private List<MokkojiCategory> mokkojiCategories = new ArrayList<>();

    @Builder
    public Mokkoji(String mokkojiName, String leaderId) {
        this.mokkojiName = mokkojiName;
        this.leaderId = leaderId;
    }
}