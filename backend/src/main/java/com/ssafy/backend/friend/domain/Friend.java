package com.ssafy.backend.friend.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Friend  {

    @Column
    @EmbeddedId
    private UserId userId;

    @Column
    private Integer isFriend;

}
