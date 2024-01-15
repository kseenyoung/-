package com.ssafy.backend.user.model.service;

import com.ssafy.backend.exception.MyException;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public void signup(UserSignupDto userSignupDto) throws Exception {
        userMapper.signup(userSignupDto);
    }

    @Override
    public void test(UserSignupDto userSignupDto) throws Exception {
        userRepository.save(userSignupDto.toEntity(userSignupDto));
    }
}
