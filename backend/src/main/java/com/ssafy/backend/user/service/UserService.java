package com.ssafy.backend.user.service;


import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.UserLoginDto;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.domain.User;

public interface UserService {

    void signup(UserSignupDto userSignupDto) throws Exception;

    boolean isExistId(String userLoginId) throws Exception;

    boolean login(UserLoginDto userLoginDto) throws Exception;

    boolean isExistNickname(String userTriedNickname);

    User canCreateMokkoji(String userId, int point);

    void saveMokkojiId(User user, Mokkoji mokkoji);

    User isExistUser(String userId);

    User deleteMokkojiCheck(String userId);

    void deleteMokkojiUser(Mokkoji mokkojiId);
}
