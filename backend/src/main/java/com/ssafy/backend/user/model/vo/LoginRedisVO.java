package com.ssafy.backend.user.model.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRedisVO {
    String loginId;
    String userId;

    boolean isLogin;

    public LoginRedisVO(String userId, boolean isLogin) {
        this.userId = userId;
        this.isLogin = isLogin;
    }

    public LoginRedisVO(){}

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
