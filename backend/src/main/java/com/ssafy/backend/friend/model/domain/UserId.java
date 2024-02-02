package com.ssafy.backend.friend.model.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_id2")
    private String userId2;

    public UserId() {
    }

    public UserId(String userId, String userId2) {
        this.userId = userId;
        this.userId2 = userId2;
    }

    @Override
    public String toString() {
        return "UserId{" +
                "userId='" + userId + '\'' +
                ", userId2='" + userId2 + '\'' +
                '}';
    }
}
