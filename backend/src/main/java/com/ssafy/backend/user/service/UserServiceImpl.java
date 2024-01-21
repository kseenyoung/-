package com.ssafy.backend.user.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.EncryptUtil;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.security.model.SecurityDto;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.UserLoginDto;

import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final int PLUS_DAYS = 7;
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

    //포인트 null이면 오류 널 체크 해야됨
    @Override
    public User canCreateMokkoji(String userId, int point) {
        User user = isExistUser(userId);
        log.info("모꼬지가 있는지 확인합니다. mokkojiId : {}",user.getMokkojiId());
        if(user.getMokkojiId() != null ) throw new MyException("이미 유저는 길드가 존재합니다.", HttpStatus.BAD_REQUEST);
        if(user.getUserPoint() - point <0) throw new MyException("포인트가 부족합니다." , HttpStatus.BAD_REQUEST);
        user.usePoint(point);
        return user;
    }

    @Override
    public void saveMokkojiId(User user, Mokkoji mokkoji) {
        user.saveMokkoji(mokkoji);
        updateUser(user);
    }

    public User isExistUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new MyException("회원이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }

    //길드 삭제시 유저에서 확인해야하는 것
    @Override
    public User deleteMokkojiCheck(String userId) {
        User user = isExistUser(userId);
        //길드장인지 체크
        if (!user.getMokkojiId().getLeaderId().equals(user.getUserId()))
            throw new MyException("길드장이 아닙니다.", HttpStatus.BAD_REQUEST);

        LocalDateTime createdDateTime = user.getMokkojiId().getCreatedDate().plusDays(PLUS_DAYS);
        LocalDateTime now = LocalDateTime.now();
        //7일 이전인지 체크
        if (!now.isAfter(createdDateTime))
            throw new MyException("7일 이후에 모꼬지를 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
        return user;
    }

    @Override
    @Transactional
    public void deleteMokkojiUser(Mokkoji mokkojiId) {
        List<User> users = userRepository.findAllByMokkojiId(mokkojiId);
        users.forEach(user -> user.saveMokkoji(null));
        userRepository.saveAll(users);
    }

    private void updateUser(User user) {
        userRepository.save(user);
    }


}
