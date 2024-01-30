package com.ssafy.backend.user.model.vo;

import com.ssafy.backend.common.exception.BaseException;

import java.util.Date;

// userId, userName, userPicture, userEmail, userBirthday, userPhonenumber, userPoint, userNickname
public class MyPageVO {
    private String userId, userName, userNickname, userPicture, userEmail, userPhonenumber;
    private String userBirthday;
    private Integer userPoint, mokkojiId;

    private String mokkojiName;

    private Integer userRank;

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public String getMokkojiName() {
        return mokkojiName;
    }

    public void setMokkojiName(String mokkojiName) {
        this.mokkojiName = mokkojiName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getMokkojiId() {
        return mokkojiId;
    }

    public void setMokkojiId(Integer mokkojiId) {
        this.mokkojiId = mokkojiId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    @Override
    public String toString() {
        return "MyPageVO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", mokkojiId='" + mokkojiId + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhonenumber='" + userPhonenumber + '\'' +
                ", userBirthday=" + userBirthday +
                ", userPoint=" + userPoint +
                '}';
    }
}
