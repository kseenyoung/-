package com.ssafy.backend.user.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.utils.RegEx;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.friend.service.FriendService;
import com.ssafy.backend.loginhistory.service.LoginHistoryService;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.dto.OpenviduRequestDto;
import com.ssafy.backend.user.model.dto.UserLoginDto;
import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.vo.UserViewVO;
import com.ssafy.backend.user.service.UserService;
import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @Autowired
    LoginHistoryService loginHistoryService;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("test")
    public void test(@RequestBody Map<String, Object> body) throws Exception {
        RegEx.isValidUserEmail("");
    }


    @PostMapping("")
    public BaseResponse<?> user(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        HttpSession session = null;

        if (sign != null) {
            switch (sign) {
                /*
                 * [POST] 회원가입
                 */
                case "signup":
                    String userLoginId = (String) body.get("userId");
                    String userLoginBirthday = (String) body.get("userBirthday");
                    String userLoginName = (String) body.get("userName");
                    String userLoginPassword = (String) body.get("userPassword");
                    String userLoginPhonenumber = (String) body.get("userPhonenumber");
                    String userLoginEmail = (String) body.get("userEmail");
                    String userLoginNickname = (String) body.get("userNickname");

                    UserSignupDto userSignupDto = new UserSignupDto(userLoginId, userLoginBirthday, userLoginName, userLoginPassword, userLoginPhonenumber, userLoginEmail, userLoginNickname);

                    try {
                        userService.signup(userSignupDto);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BaseException(FAIL_SIGN_UP);
//
                    }
                    return new BaseResponse<>(SUCCESS_ID_SIGN_UP);

                /*
                 * [POST] 로그인
                 * 로그인 반복 시도 시 5회 제한...
                 */
                case "login":
                    String loginUserId = (String) body.get("userId");
                    String loginUserPassword = (String) body.get("userPassword");
                    String loginUserIp = request.getRemoteAddr();

                    UserLoginDto userLoginDto = new UserLoginDto(loginUserId, loginUserPassword);
                    if (userService.login(userLoginDto)) {  // 로그인 성공 시...
                        User user = new User(loginUserId);
                        session = request.getSession();
                        session.setAttribute("User", user);

                        // 로그인 성공시 친구들에게 시그널 전송
                        List<FriendVO> friendList = friendService.listFriends(loginUserId);

                        for (FriendVO friend : friendList) {
                            System.out.println(friend.getUserId() + "에게 로그인 신호");

                            OpenviduRequestDto openviduRequestDto = new OpenviduRequestDto(friend.getUserId(), "login",loginUserId);
                            URI uri = UriComponentsBuilder
                                    .fromUriString(OPENVIDU_URL)
                                    .path("/openvidu/api/signal")
                                    .encode()
                                    .build()
                                    .toUri();

                            String secret = "Basic " + OPENVIDU_SECRET;
                            secret = Base64.getEncoder().encodeToString(secret.getBytes());

                            RequestEntity<String> requestEntity = RequestEntity
                                    .post(uri)
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                                    .body(openviduRequestDto.toJson());

                            System.out.println(openviduRequestDto.toJson());

                            RestTemplate restTemplate = new RestTemplate();
                            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

                            ResponseEntity<QuestionDto> responseEntity = restTemplate.postForEntity(uri, requestEntity, QuestionDto.class);
                        }

                        loginHistoryService.successLogin(loginUserId, loginUserIp);
                        return new BaseResponse<>(SUCCESS_LOGIN);
                    } else {  // 로그인 실패 시 카운트 시작.
                        System.out.println(1);
                        int remainTime = loginHistoryService.failLogin(loginUserId, loginUserIp);
                        if (remainTime != 0) {
                            return new BaseResponse<>(remainTime+"초 뒤에 다시 시도해주세요");
                        } else {
                            return new BaseResponse<>("로그인 실패");
                        }
                    }

                case "logout":
                    session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    return new BaseResponse<>("로그아웃 성공");
                /*
                 * [POST] 아이디 중복 검사
                 */
                case "isExistId":
                    String userTriedId = (String) body.get("userId");

                    boolean isExistId = userService.isExistId(userTriedId);
                    if (isExistId) {
                        throw new BaseException(ALREADY_EXIST_USER);
                    } else {
                        return new BaseResponse<>(SUCCESS_ID_CHECK);
                    }

                    /*
                     * [POST] 닉네임 중복 검사
                     */
                case "isExistNickname":
                    String userTriedNickname = (String) body.get("userNickname");

                    boolean isExistNickname = userService.isExistNickname(userTriedNickname);
                    if (isExistNickname) {
                        throw new BaseException(ALREADY_EXIST_NICKNAME);
                    } else {
                        return new BaseResponse<>(SUCCESS_NICKNAME_CHECK);
                    }

                    /*
                     * [POST] 회원 정보 보기
                     * 유저아이디, 유저닉네임, 유저사진, 유저상태메세지, 유저모꼬지이름, 유저누적공부시간, 유저랭크
                     */
                case "viewUserInformation":
                    String viewUserNickname = (String) body.get("userNickname");
                    if (viewUserNickname != null) {
                        boolean isExistNicknameForView = userService.isExistNickname(viewUserNickname);
                        if (isExistNicknameForView) {
                            UserViewVO userViewVO = userService.viewUserInformation(viewUserNickname);
                            return new BaseResponse<>(userViewVO);
                        } else {
                            throw new BaseException(PLZ_ENTER_NICKNAME);
                        }
                    } else {
                        throw new BaseException(PLZ_ENTER_NICKNAME);
                    }

                    /*
                     * [POST] 회원 가입 이메일 인증 보내기 ...
                     */
                case "sendEmailForSignUp":
                    String userEmailForAuth = (String) body.get("userEmail");
                    RegEx.isValidUserEmail(userEmailForAuth);

                    String codeForAuth = userService.sendEmail(userEmailForAuth);
                    session = request.getSession();
                    session.setAttribute("codeForAuth", codeForAuth);
                    System.out.println(codeForAuth);
                    return new BaseResponse<>(SUCCESS_SEND_EMAIL);

                /*
                 * [POST] 인증번호 확인하기 ...
                 */
                case "confirmCode":
                    String userCodeForAuth = (String) body.get("userCodeForAuth");
                    if (userCodeForAuth == null || "".equals(userCodeForAuth)) {
                        throw new BaseException(INVALID_AUTH_CODE);
                    }
                    session = request.getSession(false);
                    if (session != null) {
                        String originCodeForAuth = (String) session.getAttribute("codeForAuth");
                        if (originCodeForAuth != null) {
                            if (userCodeForAuth.equals(originCodeForAuth)) {
                                session.removeAttribute("codeForAuth");
                                return new BaseResponse<>(SUCCESS_AUTH);
                            } else {
                                throw new BaseException(INVALID_AUTH_CODE);
                            }
                        }
                    }

                    /*
                     * [POST] 회원 탈퇴 ...
                     */
                case "deleteUser":
                    session = request.getSession(false);
                    if (session != null) {
                        User deleteUser = (User) session.getAttribute("User");
                        if (deleteUser != null) {
                            String deleteUserId = deleteUser.getUserId();
                            String deleteUserPassword = (String) body.get("userPassword");
                            boolean isSuccess = userService.deleteUser(deleteUserId, deleteUserPassword);
                            if (isSuccess) {
                                return new BaseResponse<>(SUCCESS_DELETE_USER);
                            } else {
                                throw new BaseException(FAIL_TO_DELETE_USER);
                            }
                        } else {
                            throw new BaseException(NEED_LOGIN);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * [POST] 비밀번호 변경
                     */
                case "changePassword":
                    session = request.getSession(false);
                    if (session != null) {
                        User originUser = (User) session.getAttribute("User");

                        String originUserId = originUser.getUserId();
                        String originPassword = (String) body.get("userPassword");
                        String newPassword = (String) body.get("newPassword");

                        UserLoginDto userOriginDto = new UserLoginDto(originUserId, originPassword);
                        boolean isMatched = userService.login(userOriginDto);

                        if (isMatched) {
                            userService.changePassword(originUserId, newPassword);
                            session.invalidate();
                            return new BaseResponse<>(SUCCESS_CHANGE_PASSWORD);
                        } else {
                            throw new BaseException(NOT_MATCH_PASSWORD);
                        }

                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                case "changeNickname":
                    session = request.getSession(false);
                    if (session != null) {
                        User changeNicknameUser = (User) session.getAttribute("User");

                        // 닉네임 중복 확인 받았다는 가정 하에 ...
                        String changeNicknameUserId = changeNicknameUser.getUserId();
                        String newNickname = (String) body.get("newNickname");

                        userService.changeNickname(changeNicknameUserId, newNickname);
                        return new BaseResponse<>(SUCCESS_CHANGE_NICKNAME);
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }
            }
        }
        throw new BaseException(NOT_MATCH_SIGN);
    }

}
