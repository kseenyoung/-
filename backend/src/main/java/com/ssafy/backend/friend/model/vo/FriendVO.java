package com.ssafy.backend.friend.model.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FriendVO {

    private String userId, userNickname,userPicture;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}
