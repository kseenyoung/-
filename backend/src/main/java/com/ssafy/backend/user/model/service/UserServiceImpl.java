package com.ssafy.backend.user.model.service;

import com.ssafy.backend.security.model.SecurityDto;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.domain.User;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityMapper securityMapper;

    @Autowired
    UserRepository userRepository;


    @Override
    public void signup(UserSignupDto userSignupDto) throws Exception {
        userMapper.signup(userSignupDto);
        securityMapper.insertSalt(new SecurityDto(userSignupDto.getUserId(), "ㅋㅋ"));
    }

    @Override
    public void test(UserSignupDto userSignupDto) throws Exception {
        userRepository.save(userSignupDto.toEntity());
    }

    @Override
    public UserSignupDto findById(String userId) throws Exception {
        System.out.println(userId);

        Optional<User> byUserId = userRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            return new UserSignupDto(byUserId.get().getUserId());
        }

        return null;
    }


}
