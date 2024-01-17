package com.ssafy.backend.user.model.service;

import com.ssafy.backend.common.utils.EncryptUtil;
import com.ssafy.backend.security.model.SecurityDto;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.domain.User;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityMapper securityMapper;

    @Autowired
    UserRepository userRepository;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public void signup(UserSignupDto userSignupDto) throws Exception {
        SecurityDto securityDto = new SecurityDto();
        String userPassword = userSignupDto.getUserPassword();
        String salt = UUID.randomUUID().toString();
        String safePassword = EncryptUtil.getSHA256(userPassword, salt);

        securityDto.setUserId(userSignupDto.getUserId());
        securityDto.setSalt(salt);

        userSignupDto.setUserPassword(safePassword);

        securityMapper.insertSalt(securityDto);
        userMapper.signup(userSignupDto);
    }

    @Override
    public void test(UserSignupDto userSignupDto) throws Exception {
        userRepository.save(userSignupDto.toEntity());
    }
}
