package com.ssafy.backend.mokkoji.domain;

import com.ssafy.backend.user.domain.User;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Mokkoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mokkojiId;

    @Column(columnDefinition = "varchar(15)")
    private String mokkojiName;

    @Column(columnDefinition = "varchar(20)")
    private String leaderId;

    @Column(columnDefinition = "char(60)")
    private String mokkojiStatus;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;


}