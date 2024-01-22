package com.ssafy.backend.user.model.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name="user_rank")
@Getter
@ToString
public class UserRank {

    @Id
    @Column
    private String userId;

    @Column
    private String userName;

    @Column
    private Integer userTotalStudyTime;

    @Column
    private Integer userRank;

}
