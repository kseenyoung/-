package com.ssafy.backend.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friend  {

    @Column
    @EmbeddedId
    private UserId userId;

    @Column
    private Integer isFriend;

}
