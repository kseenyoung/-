package com.ssafy.backend.user.model.service;

import com.ssafy.backend.exception.MyException;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public void signup(UserSignupDto userSignupDto) throws Exception {
        userMapper.signup(userSignupDto);
    }
}
