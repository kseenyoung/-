package com.ssafy.backend.loginhistory.model.vo;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Setter;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

public class LoginHistoryVO {
    private String userId, userIp;
    private int loginHistoryId, tryLoginCount;
    private String createdDate, updatedDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        if (userIp != null && !"".equals(userIp)){
            this.userIp = userIp;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }

    public int getTryLoginCount() {
        return tryLoginCount;
    }

    public void setTryLoginCount(int tryLoginCount) {
        if (tryLoginCount >= 0) {
            this.tryLoginCount = tryLoginCount;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        if (createdDate != null && !"".equals(createdDate)) {
            this.createdDate = createdDate;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        if (updatedDate != null && !"".equals(updatedDate)) {
            this.updatedDate = updatedDate;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }

    public int getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(int loginHistoryId) {
        if (loginHistoryId >= 0) {
            this.loginHistoryId = loginHistoryId;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }
}
