package com.ssafy.backend.user.service;


import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.dto.UserLoginDto;
import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.vo.MyPageVO;
import com.ssafy.backend.user.model.vo.UserViewVO;

import java.util.List;

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

    User leaderCheck(String userId);

    User memberCheck(String memberId, Mokkoji mokkoji);

    void kickMokkojiUser(User memberCheck);

    UserViewVO viewUserInformation(String viewUserNickname);

    List<UserViewVO> viewUserInformationByMokkoji(Mokkoji mokkoji);

    String sendEmail(String userEmailForAuth) throws MyException;

    boolean deleteUser(String deleteUserId, String deleteUserPassword) throws Exception;

    void changePassword(String originUserId, String newPassword) throws Exception;

    void changeNickname(String changeNicknameUserId, String newNickname);

    User isKakaoUser(String kakaoEmail);

    void linkKakao(String user, String kakaoEmail);

    User isGoogleUser(String googleEamil);

    void linkGoogle(String loginUserId, String googleEmail);

    void changeEmail(String originUserId, String newEmail);

    MyPageVO viewMyPage(String viewUserId);
}
