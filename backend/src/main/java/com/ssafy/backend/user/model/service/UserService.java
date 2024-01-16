package com.ssafy.backend.user.model.service;

import com.ssafy.backend.user.domain.User;
import com.ssafy.backend.user.model.UserSignupDto;

public interface UserService {

    void signup(UserSignupDto userSignupDto) throws Exception;

    void test(UserSignupDto userSignupDto) throws Exception;

    UserSignupDto findById(String userId) throws Exception;

}
