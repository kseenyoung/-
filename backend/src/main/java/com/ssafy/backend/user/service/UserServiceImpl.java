package com.ssafy.backend.user.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.EncryptUtil;
import com.ssafy.backend.security.model.SecurityDto;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.UserLoginDto;

import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        String signUpSalt = UUID.randomUUID().toString();
        String safePassword = EncryptUtil.getSHA256(userPassword, signUpSalt);

        securityDto.setUserId(userSignupDto.getUserId());
        securityDto.setSalt(signUpSalt);

        userSignupDto.setUserPassword(safePassword);

        securityMapper.insertSalt(securityDto);

        userMapper.signup(userSignupDto);
    }

    @Override
    public boolean isExistId(String userLoginId) throws Exception {
        return userRepository.existsById(userLoginId);
    }
    @Override
    public boolean login(UserLoginDto userLoginDto) throws Exception {
        String loginPassword = userLoginDto.getUserPassword();
        String loginUserId = userLoginDto.getUserId();
        String loginSalt = securityMapper.getSalt(loginUserId);
        String encryptedLoginPassword = EncryptUtil.getSHA256(loginPassword, loginSalt);


        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new MyException("ERROR", HttpStatus.BAD_REQUEST));
        return user.checkPassword(encryptedLoginPassword);
    }

    @Override
    public boolean isExistNickname(String userTriedNickname) {
        User user = userRepository.findUserByUserNickname(userTriedNickname);
        System.out.println(user);
        if (user!=null){
            return true;
        } else {
            return false;
        }
    }
}
