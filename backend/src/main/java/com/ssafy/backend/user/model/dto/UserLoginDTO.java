package com.ssafy.backend.user.model.dto;

import com.ssafy.backend.common.exception.MyException;
import org.springframework.http.HttpStatus;

public class UserLoginDTO {
    private String userId, userPassword;

    public UserLoginDTO(String userId, String userPassword) throws MyException {
        setUserId(userId);
        setUserPassword(userPassword);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) throws MyException {
        if (userId == null || "".equals(userId)){
            throw new MyException("아이디를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) throws MyException {
        if (userId == null || "".equals(userPassword)){
            throw new MyException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
