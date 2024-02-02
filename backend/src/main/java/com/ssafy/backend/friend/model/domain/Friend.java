package com.ssafy.backend.friend.model.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
public class Friend  {

    @Column
    @EmbeddedId
    private UserId userId;

    @Column
    private Integer isFriend;

    public Friend(UserId userId, Integer isFriend) {
        this.userId = userId;
        this.isFriend = isFriend;
    }

    public Friend() {
    }

    @Override
    public String toString() {
        return "Friend{" +
                "userId=" + userId +
                ", isFriend=" + isFriend +
                '}';
    }
}
