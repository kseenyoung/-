package com.ssafy.backend.user.model.vo;

public class UserInformationVO {
    private String userId, userNickname, userPicture, userStatusMessage, mokkoijiName;
    private Integer userTotalStudyTime, userRank;

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public UserInformationVO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserStatusMessage() {
        return userStatusMessage;
    }

    public void setUserStatusMessage(String userStatusMessage) {
        this.userStatusMessage = userStatusMessage;
    }

    public String getMokkoijiName() {
        return mokkoijiName;
    }

    public void setMokkoijiName(String mokkoijiName) {
        this.mokkoijiName = mokkoijiName;
    }

    public Integer getUserTotalStudyTime() {
        return userTotalStudyTime;
    }

    public void setUserTotalStudyTime(Integer userTotalStudyTime) {
        this.userTotalStudyTime = userTotalStudyTime;
    }

    @Override
    public String toString() {
        return "UserViewVO{" +
                "userId='" + userId + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userStatusMessage='" + userStatusMessage + '\'' +
                ", mokkoijiName='" + mokkoijiName + '\'' +
                ", userTotalStudyTime=" + userTotalStudyTime +
                ", userRank=" + userRank +
                '}';
    }
}
