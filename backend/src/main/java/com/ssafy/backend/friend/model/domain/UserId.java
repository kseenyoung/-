package com.ssafy.backend.friend.model.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_id2")
    private String userId2;

}
