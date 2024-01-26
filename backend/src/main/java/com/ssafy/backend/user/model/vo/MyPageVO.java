package com.ssafy.backend.user.model.vo;

import lombok.Data;

import java.util.Date;

@Data
// userId, userName, userPicture, userEmail, userBirthday, userPhonenumber, userPoint
public class MyPageVO {
    private String userId, userName, userNickname, mokkojiId, userPicture, userEmail, userPhonenumber;
    private Date userBirthday;
    private Integer userPoint;
}
