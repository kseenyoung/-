package com.ssafy.backend.user.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.EncryptUtil;
import com.ssafy.backend.friend.model.mapper.FriendMapper;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.security.model.dto.SecurityDTO;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.domain.UserRank;
import com.ssafy.backend.user.model.dto.UserLoginDTO;
import com.ssafy.backend.user.model.dto.UserSignupDTO;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.model.repository.UserRankRepository;
import com.ssafy.backend.user.model.repository.UserRepository;
import com.ssafy.backend.user.model.vo.MyPageVO;
import com.ssafy.backend.user.model.vo.UserInformationVO;
import com.ssafy.backend.user.model.vo.UserViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;


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

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRankRepository userRankRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FriendMapper friendMapper;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional(rollbackFor = {Exception.class} ,propagation = Propagation.REQUIRES_NEW)
    @Override
    public void signUp(UserSignupDTO userSignupDTO) throws Exception {
        SecurityDTO securityDTO = new SecurityDTO();
        String userPassword = userSignupDTO.getUserPassword();

        String signUpSalt = UUID.randomUUID().toString();
        String safePassword = EncryptUtil.getSHA256(userPassword, signUpSalt);

        securityDTO.setUserId(userSignupDTO.getUserId());
        securityDTO.setSalt(signUpSalt);

        userSignupDTO.setUserPassword(safePassword);

        securityMapper.addSalt(securityDTO);
        userMapper.signUp(userSignupDTO);
    }

    @Override
    public boolean isExistId(String userLoginId) {
        return userRepository.existsById(userLoginId);
    }
    @Override
    public boolean login(UserLoginDTO userLoginDTO) throws Exception {
        String loginPassword = userLoginDTO.getUserPassword();
        String loginUserId = userLoginDTO.getUserId();
        String loginSalt = securityMapper.getSalt(loginUserId);
        String encryptedLoginPassword = EncryptUtil.getSHA256(loginPassword, loginSalt);
        User user = userRepository.findUserByUserId(loginUserId);
        if (user == null) {
            return false;
        } else {
            return user.checkPassword(encryptedLoginPassword);
        }


    }

    @Override
    public boolean isExistNickname(String userTriedNickname) {
        User user = userRepository.findUserByUserNickname(userTriedNickname);
        if (user!=null){
            return true;
        } else {
            return false;
        }
    }

    //포인트 null이면 오류 널 체크 해야됨
    @Override
    public User canAddMokkoji(String userId, int point) {
        User user = isExistUser(userId);
        log.info("모꼬지가 있는지 확인합니다. mokkojiId : {}",user.getMokkojiId());
        // 이미 존재하는 길드, 포인트 부족
        if(user.getMokkojiId() != null ) throw new BaseException(OOPS);
        user.usePoint(point);
        return user;
    }

    @Override
    public void modifyMokkojiId(User user, Mokkoji mokkoji) {
        user.saveMokkoji(mokkoji);
        userRepository.save(user);
    }

    public User isExistUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
    }

    //길드 삭제시 유저에서 확인해야하는 것
    @Override
    public User deleteMokkojiCheck(String userId) {
        User user = leaderCheck(userId);
        LocalDateTime createdDateTime = user.getMokkojiId().getCreatedDate().plusDays(PLUS_DAYS);
        LocalDateTime now = LocalDateTime.now();
        //7일 이전인지 체크
        if (!now.isAfter(createdDateTime))
            throw new BaseException(REFUSED_TO_DELETE_MOKKOJI);
        return user;
    }

    @Override
    @Transactional
    public void deleteMokkojiUser(Mokkoji mokkojiId) {
        List<User> users = userRepository.findAllByMokkojiId(mokkojiId);
        users.forEach(user -> user.saveMokkoji(null));
        userRepository.saveAll(users);
    }

    //길드장인지 체크
    public User leaderCheck(String userId) {
        User user = isExistUser(userId);
        if(user.getMokkojiId() == null) throw new BaseException(NOT_EXIST_USER_MOKKOJI);
        if (!user.getMokkojiId().getLeaderId().equals(user.getUserId()))
            throw new BaseException(NOT_MOKKOJI_LEADER);
        return user;
    }

    //해당 모꼬지 사람인지 체크
    @Override
    public User memberCheck(String memberId, Mokkoji mokkoji) {
        return userRepository.findByMokkojiIdAndUserId(mokkoji, memberId)
                .orElseThrow(() -> new BaseException(NOT_MOKKOJI_MEMBER));
    }

    @Override
    @Transactional
    public void kickMokkojiUser(User user) {
        user.saveMokkoji(null);
        userRepository.save(user);
    }

    /*
     * [POST] 회원 정보 조회
     * 유저아이디, 유저닉네임, 유저사진, 유저상태메세지, 유저모꼬지이름, 유저누적공부시간, 유저랭크
     */
    @Override
    public UserInformationVO getUserInformation(String getUserNickname) {
        User user = userRepository.findUserByUserNickname(getUserNickname);
        UserInformationVO userInformationVO = new UserInformationVO();
        userInformationVO.setUserId(user.getUserId());
        userInformationVO.setUserNickname(user.getUserNickname());
        userInformationVO.setUserPicture(user.getUserPicture());
        userInformationVO.setUserStatusMessage(user.getUserStatusMessage());
        if (user.getMokkojiId()!=null){  // 모꼬지가 있는 회원일 때
            userInformationVO.setMokkoijiName(user.getMokkojiId().getMokkojiName());
        }

        if (user.getUserTotalStudyTime()!=null){  // 총 공부시간이 존재하는 회원일 때
            UserRank userRank = userRankRepository.findUserRankByUserId(user.getUserId());
            userInformationVO.setUserRank(userRank.getUserRank());
        }
        return userInformationVO;
    }

    @Override
    public List<UserInformationVO> getUserInformationByMokkoji(Mokkoji mokkoji) {
        List<User> user = userRepository.findAllByMokkojiId(mokkoji);
        List<UserInformationVO> userInformationVOs = new ArrayList<UserInformationVO>();
        for(User u : user){
            UserInformationVO userInformationVO = new UserInformationVO();
            userInformationVO.setUserId(u.getUserId());
            userInformationVO.setUserNickname(u.getUserNickname());
            userInformationVO.setUserPicture(u.getUserPicture());
            userInformationVO.setUserStatusMessage(u.getUserStatusMessage());
            if (u.getUserTotalStudyTime()!=null){  // 총 공부시간이 존재하는 회원일 때
                UserRank userRank = userRankRepository.findUserRankByUserId(u.getUserId());
                userInformationVO.setUserRank(userRank.getUserRank());
            }
            userInformationVOs.add(userInformationVO);
        }
        return userInformationVOs;
    }

    @Override
    public String sendEmail(String userEmailForAuth) throws MyException {
        String codeForAuth = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        MimeMessage emailContent = javaMailSender.createMimeMessage();

        try {
            emailContent.setFrom(new InternetAddress(senderEmail, "다각", "UTF-8"));
            emailContent.setRecipients(MimeMessage.RecipientType.TO, userEmailForAuth);
            emailContent.setSubject("다각 이메일 인증");

            String body = "";
            body += "<h3>" + "요청하신 인증번호 입니다." + "</h3>";
            body += "<h1>" + "인증 번호 : " + codeForAuth + "</h1>";
            body += "<h3>" + "인증번호를 정확하게 입력해주세요." + "</h3>";
//            body += "<h3>" + "위 인증번호의 유효시간은 30분 입니다." + "</h3>";
            body += "<h3>" + "감사합니다." + "</h3>";
            emailContent.setText(body, "UTF-8", "html");
        } catch (Exception e) {
            throw new BaseException(FAIL_TO_CONNECT);
        }
        javaMailSender.send(emailContent);
        return codeForAuth;
    }

    @Override
    public boolean deleteUser(String deleteUserId, String deleteUserPassword) throws Exception {
        String deleteUserSalt = securityMapper.getSalt(deleteUserId);
        String encryptedDeletePassword = EncryptUtil.getSHA256(deleteUserPassword, deleteUserSalt);

        User user = userRepository.findById(deleteUserId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        boolean isMatch = user.checkPassword(encryptedDeletePassword);
        if (isMatch) {
            // TODO : 뭘 지울 지 정해야 함...
            userRepository.deleteById(deleteUserId);
            securityMapper.deleteSalt(deleteUserId);
            return isMatch;
        } else {
            return isMatch;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyPassword(String originUserId, String newPassword) throws Exception {
        String newSalt = UUID.randomUUID().toString();
        String newSafePassword = EncryptUtil.getSHA256(newPassword, newSalt);

        securityMapper.modifySalt(originUserId, newSalt);
        userMapper.modifyPassword(originUserId, newSafePassword);
    }

    @Override
    public void modifyNickname(String modifyNicknameUserId, String newNickname) {
        User user = userRepository.findById(modifyNicknameUserId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        user.setUserNickname(newNickname);

        userRepository.save(user);
    }

    @Override
    public User isKakaoUser(String kakaoEmail) {
        return userRepository.findByKakaoEmail(kakaoEmail);
    }

    @Override
    public void linkKakao(String userId, String kakaoEmail) {
        User user = userRepository.findUserByUserId(userId);
        user.setKakaoEmail(kakaoEmail);
        userRepository.save(user);
    }

    @Override
    public User isGoogleUser(String googleEamil) {
        return userRepository.findByGoogleEmail(googleEamil);
    }

    @Override
    public void linkGoogle(String loginUserId, String googleEmail) {
        User user = userRepository.findUserByUserId(loginUserId);
        user.setGoogleEmail(googleEmail);
        userRepository.save(user);
    }

    @Override
    public void modifyEmail(String originUserId, String newEmail) {
        User user = userRepository.findById(originUserId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        user.setUserEmail(newEmail);

        userRepository.save(user);
    }

    //// userId, userName, userPicture, userNickname,userPicture, userEmail, userBirthday, userPhonenumber, userPoint,
    @Override
    public MyPageVO getMyPage(String getUserId) {
        User user = userRepository.findUserByUserId(getUserId);
        MyPageVO myPageVO = new MyPageVO();
        myPageVO.setUserId(user.getUserId());
        myPageVO.setUserName(user.getUserName());
        myPageVO.setUserNickname(user.getUserNickname());
        myPageVO.setUserPicture(user.getUserPicture());
        myPageVO.setUserEmail(user.getUserEmail());
        myPageVO.setUserBirthday(user.getUserBirthday());
        myPageVO.setUserPoint(user.getUserPoint());
        myPageVO.setUserPhonenumber(user.getUserPhonenumber());

        if (user.getMokkojiId() != null) {  // 모꼬지가 있는 회원일 때
            myPageVO.setMokkojiId(user.getMokkojiId().getMokkojiId());
            myPageVO.setMokkojiName(user.getMokkojiId().getMokkojiName());
        }

        if (user.getUserTotalStudyTime() != null) {  // 총 공부시간이 존재하는 회원일 때
            UserRank userRank = userRankRepository.findUserRankByUserId(user.getUserId());
            myPageVO.setUserRank(userRank.getUserRank());
        }
        return myPageVO;
    }

    @Override
    public String getUserEmail(User userEmailChange) {
        User user = userRepository.findUserByUserId(userEmailChange.getUserId());
        return user.getUserEmail();
    }

    @Override
    public void modifyUserStatusMessage(String changeStatusUserId, String newStatusMessage) {
        User user = userRepository.findById(changeStatusUserId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        user.setUserStatusMessage(newStatusMessage);

        userRepository.save(user);
    }

    @Override
    public void saveProfile(User user,String url) {
        user.changeImage(url);
        userRepository.save(user);
    }

    @Override
    public List<UserViewVO> getAllUserList(String userId) {
        List<UserViewVO> userListAtFriendBoard = userMapper.getAllUserList(userId);
        return userListAtFriendBoard;
    }
}
