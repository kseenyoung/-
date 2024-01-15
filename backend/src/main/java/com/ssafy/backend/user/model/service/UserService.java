package com.ssafy.backend.user.model.service;

import com.ssafy.backend.exception.MyException;
import com.ssafy.backend.user.model.UserSignupDto;

public interface UserService {

    void signup(UserSignupDto userSignupDto) throws Exception;

}
