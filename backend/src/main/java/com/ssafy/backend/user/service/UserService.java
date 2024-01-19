package com.ssafy.backend.user.service;


import com.ssafy.backend.user.model.UserLoginDto;
import com.ssafy.backend.user.model.UserSignupDto;

public interface UserService {

    void signup(UserSignupDto userSignupDto) throws Exception;

    boolean isExistId(String userLoginId) throws Exception;

    boolean login(UserLoginDto userLoginDto) throws Exception;

    boolean isExistNickname(String userTriedNickname);
}
