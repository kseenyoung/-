package com.ssafy.backend.loginhistory.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Data;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Data
public class LoginHistoryDTO {
    private String userId, userIp;
    private int tryLoginCount;
    private String createdDate, updatedDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId != null && !"".equals(userId)){
            this.userId = userId;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
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
        if (updatedDate != null && "".equals(updatedDate)) {
            this.updatedDate = updatedDate;
        } else {
            throw new BaseException(FAIL_TO_CONNECT);
        }
    }

}
