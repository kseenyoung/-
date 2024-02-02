package com.ssafy.backend.security.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Data;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_SIGN_UP;


public class SecurityDTO {
    private String userId, salt;

    public SecurityDTO() {}

    public SecurityDTO(String userId, String salt){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId != null || !"".equals(userId)){
            this.userId = userId;
        } else {
            throw new BaseException(FAIL_SIGN_UP);
        }

    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        if (salt != null || !"".equals(salt)) {
            this.salt = salt;
        } else {
            throw new BaseException(FAIL_SIGN_UP);
        }
    }
}
