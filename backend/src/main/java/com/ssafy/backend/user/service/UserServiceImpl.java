package com.ssafy.backend.user.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.EncryptUtil;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.security.model.SecurityDto;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.domain.UserRank;
import com.ssafy.backend.user.model.dto.UserLoginDto;
import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.model.repository.UserRankRepository;
import com.ssafy.backend.user.model.repository.UserRepository;
import com.ssafy.backend.user.model.vo.MyPageVO;
import com.ssafy.backend.user.model.vo.UserViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_MEMBER;


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

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional(rollbackFor = Exception.class)
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
        userRepository.save(user);
    }

    public User isExistUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new MyException("회원이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }

    //길드 삭제시 유저에서 확인해야하는 것
    @Override
    public User deleteMokkojiCheck(String userId) {
        User user = leaderCheck(userId);
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

    //길드장인지 체크
    public User leaderCheck(String userId) {
        User user = isExistUser(userId);
        if(user.getMokkojiId() == null) throw new MyException("해당 회원은 모꼬지가 현재 없습니다", HttpStatus.BAD_REQUEST);
        if (!user.getMokkojiId().getLeaderId().equals(user.getUserId()))
            throw new MyException("모꼬지장이 아닙니다.", HttpStatus.BAD_REQUEST);
        return user;
    }

    //해당 모꼬지 사람인지 체크
    @Override
    public User memberCheck(String memberId, Mokkoji mokkoji) {
        return userRepository.findByMokkojiIdAndUserId(mokkoji, memberId)
                .orElseThrow(() -> new MyException("현재 모꼬지에서 해당 회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
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
    public UserViewVO viewUserInformation(String viewUserNickname) {
        User user = userRepository.findUserByUserNickname(viewUserNickname);
        UserViewVO userViewVO = new UserViewVO();
        userViewVO.setUserId(user.getUserId());
        userViewVO.setUserNickname(user.getUserNickname());
        userViewVO.setUserPicture(user.getUserPicture());
        userViewVO.setUserStatusMessage(user.getUserStatusMessage());
        if (user.getMokkojiId()!=null){  // 모꼬지가 있는 회원일 때
            userViewVO.setMokkoijiName(user.getMokkojiId().getMokkojiName());
        }

        if (user.getUserTotalStudyTime()!=null){  // 총 공부시간이 존재하는 회원일 때
            UserRank userRank = userRankRepository.findUserRankByUserId(user.getUserId());
            userViewVO.setUserRank(userRank.getUserRank());
        }

        System.out.println(userViewVO);
        return userViewVO;
    }

    @Override
    public List<UserViewVO> viewUserInformationByMokkoji(Mokkoji mokkoji) {
        List<User> user = userRepository.findAllByMokkojiId(mokkoji);
        List<UserViewVO> data = new ArrayList<UserViewVO>();
        for(User u : user){
            UserViewVO userViewVO = new UserViewVO();
            userViewVO.setUserId(u.getUserId());
            userViewVO.setUserNickname(u.getUserNickname());
            userViewVO.setUserPicture(u.getUserPicture());
            userViewVO.setUserStatusMessage(u.getUserStatusMessage());
            if (u.getUserTotalStudyTime()!=null){  // 총 공부시간이 존재하는 회원일 때
                UserRank userRank = userRankRepository.findUserRankByUserId(u.getUserId());
                userViewVO.setUserRank(userRank.getUserRank());
            }
            data.add(userViewVO);
        }
        return data;
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
        body += "<h3>" + "위 인증번호의 유효시간은 30분 입니다." + "</h3>";
        body += "<h3>" + "감사합니다." + "</h3>";
        emailContent.setText(body, "UTF-8", "html"); }
        catch (Exception e){
            throw new MyException("메일 전송에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        javaMailSender.send(emailContent);

        return codeForAuth;
    }

    @Override
    public boolean deleteUser(String deleteUserId, String deleteUserPassword) throws Exception {
        String deleteUserSalt = securityMapper.getSalt(deleteUserId);
        String encryptedDeletePassword = EncryptUtil.getSHA256(deleteUserPassword, deleteUserSalt);

        User user = userRepository.findById(deleteUserId)
                .orElseThrow(() -> new MyException("ERROR", HttpStatus.BAD_REQUEST));

        boolean isMatch = user.checkPassword(encryptedDeletePassword);

        if (isMatch){
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
    public void changePassword(String originUserId, String newPassword) throws Exception {
        String newSalt = UUID.randomUUID().toString();
        String newSafePassword = EncryptUtil.getSHA256(newPassword, newSalt);

        securityMapper.changeSalt(originUserId, newSalt);
        userMapper.changePassword(originUserId, newSafePassword);
    }

    @Override
    public void changeNickname(String changeNicknameUserId, String newNickname) {
        User user = userRepository.findById(changeNicknameUserId).orElseThrow(()->new BaseException(NOT_EXIST_MEMBER));
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
    public void changeEmail(String originUserId, String newEmail) {
        User user = userRepository.findById(originUserId).orElseThrow(()->new BaseException(NOT_EXIST_MEMBER));
        user.setUserEmail(newEmail);

        userRepository.save(user);
    }

    @Override
    public MyPageVO viewMyPage(String viewUserId) {
        User user = userRepository.findUserByUserId(viewUserId);
        MyPageVO myPageVO = new MyPageVO();
        myPageVO.setUserId(user.getUserId());
        myPageVO.setUserNickname(user.getUserNickname());
        myPageVO.setUserPicture(user.getUserPicture());

        if (user.getMokkojiId()!=null){  // 모꼬지가 있는 회원일 때
//            myPageVO.setMokkoijiName(user.getMokkojiId().getMokkojiName());
        }

        if (user.getUserTotalStudyTime()!=null){  // 총 공부시간이 존재하는 회원일 때
            UserRank userRank = userRankRepository.findUserRankByUserId(user.getUserId());
//            myPageVO.setUserRank(userRank.getUserRank());
        }

//        System.out.println(userViewVO);
        return null;
    }
}
