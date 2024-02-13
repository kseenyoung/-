package com.ssafy.backend.user.service;


import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.domain.User;

import com.ssafy.backend.user.model.dto.UserLoginDTO;
import com.ssafy.backend.user.model.dto.UserSignupDTO;
import com.ssafy.backend.user.model.vo.MyPageVO;
import com.ssafy.backend.user.model.vo.UserInformationVO;
import com.ssafy.backend.user.model.vo.UserViewVO;

import java.util.List;

public interface UserService {

    void signUp(UserSignupDTO userSignupDTO) throws Exception;

    boolean isExistId(String userLoginId) throws Exception;

    boolean login(UserLoginDTO userLoginDTO) throws Exception;

    boolean isExistNickname(String userTriedNickname);

    User canAddMokkoji(String userId, int point);

    void modifyMokkojiId(User user, Mokkoji mokkoji);

    User isExistUser(String userId);

    User deleteMokkojiCheck(String userId);

    void deleteMokkojiUser(Mokkoji mokkojiId);

    User leaderCheck(String userId);

    User memberCheck(String memberId, Mokkoji mokkoji);

    void kickMokkojiUser(User memberCheck);

    UserInformationVO getUserInformation(String getUserNickname);

    List<UserInformationVO> getUserInformationByMokkoji(Mokkoji mokkoji);

    String sendEmail(String userEmailForAuth) throws MyException;

    boolean deleteUser(String deleteUserId, String deleteUserPassword) throws Exception;

    void modifyPassword(String originUserId, String newPassword) throws Exception;

    void modifyNickname(String modifyNicknameUserId, String newNickname);

    User isKakaoUser(String kakaoEmail);

    void linkKakao(String user, String kakaoEmail);

    User isGoogleUser(String googleEamil);

    void linkGoogle(String loginUserId, String googleEmail);

    void modifyEmail(String originUserId, String newEmail);

    MyPageVO getMyPage(String getUserId);

    String getUserEmail(User userEmailModify);

    void modifyUserStatusMessage(String ModifyStatusUserId, String newStatusMessage);

    List<UserViewVO> getAllUserList(String userIdForFriendBoard);

    void saveProfile(User user,String url);

    void logout(String userId);
}
