package com.ssafy.backend.user.model.domain.redis;

import com.ssafy.backend.room.model.domain.Answer;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@RedisHash("login")
public class LoginRedis {
    @Id
    String loginId;

    @Indexed
    String userId;

    boolean isLogin;



    public LoginRedis(String userId, boolean isLogin) {
        this.userId = userId;
        this.isLogin = isLogin;
    }

    public LoginRedis() {
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
