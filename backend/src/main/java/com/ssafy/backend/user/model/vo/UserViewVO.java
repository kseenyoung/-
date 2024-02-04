package com.ssafy.backend.user.model.vo;

import lombok.Data;

@Data
public class UserViewVO {
    private String userId, userNickname, userPicture;
    private boolean isFriend;
}
