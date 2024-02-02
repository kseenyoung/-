package com.ssafy.backend.user.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.utils.RegEx;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.friend.service.FriendService;
import com.ssafy.backend.loginhistory.service.LoginHistoryService;
import com.ssafy.backend.security.model.mapper.SecurityMapper;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.dto.OpenviduRequestDTO;
import com.ssafy.backend.user.model.dto.UserLoginDTO;
import com.ssafy.backend.user.model.dto.UserSignupDTO;
import com.ssafy.backend.user.model.mapper.UserMapper;
import com.ssafy.backend.user.model.vo.MyPageVO;
import com.ssafy.backend.user.model.vo.UserInformationVO;
import com.ssafy.backend.user.model.vo.UserViewVO;
import com.ssafy.backend.user.service.GoogleOAuthService;
import com.ssafy.backend.user.service.KakaoOAuthService;
import com.ssafy.backend.user.service.ReCaptchaService;
import com.ssafy.backend.user.service.UserService;
import io.openvidu.java.client.OpenVidu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.time.LocalDate;
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

    @Autowired
    KakaoOAuthService kakaoOAuthService;

    @Autowired
    GoogleOAuthService googleOAuthService;

    @Autowired
    ReCaptchaService reCaptchaService;


    // Transaction test
    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityMapper securityMapper;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("test")
    public void test(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        List<Integer> date = (List<Integer>) body.get("test");
        System.out.println(date);
        LocalDate today = LocalDate.of(date.get(0), date.get(1), date.get(2));
        System.out.println(today);

    }

    @PostMapping("")
    public BaseResponse<?> user(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        HttpSession session = request.getSession(false);

        if (sign != null) {
            switch (sign) {
                /*
                 * [POST] 회원가입
                 */
                case "signUp":
                    String userSignUpId = (String) body.get("userId");
                    String userSignUpBirthday = (String) body.get("userBirthday");
                    String userSignUpName = (String) body.get("userName");
                    String userSignUpPassword = (String) body.get("userPassword");
                    String userSignUpPhonenumber = (String) body.get("userPhonenumber");
                    String userSignUpEmail = (String) body.get("userEmail");
                    String userSignUpNickname = (String) body.get("userNickname");
                    UserSignupDTO userSignupDTO = new UserSignupDTO(userSignUpId, userSignUpBirthday, userSignUpName, userSignUpPassword, userSignUpPhonenumber, userSignUpEmail, userSignUpNickname);

                    userService.signUp(userSignupDTO);

                    if (session != null) {
                        session.invalidate();
                    }
                    return new BaseResponse<>(SUCCESS);

                /*
                 * [POST] 로그인
                 * 로그인 반복 시도 시 5회 제한...
                 */
                case "login":
//                    session = request.getSession(false);
                    // 리캡챠 인증 하드코딩
                    session = request.getSession();
                    session.setAttribute("recaptcha", "ok");
                    if (session != null) {
                        String isBot = (String) session.getAttribute("recaptcha");
                        if ("ok".equals(isBot)) {

                            String loginUserId = (String) body.get("userId");
                            String loginUserPassword = (String) body.get("userPassword");
                            String loginUserIp = request.getRemoteAddr();

                            UserLoginDTO userLoginDto = new UserLoginDTO(loginUserId, loginUserPassword);
                            if (userService.login(userLoginDto)) {  // 로그인 성공 시...

                                User user = new User(loginUserId);
                                session = request.getSession();
                                session.setAttribute("User", user);
                                System.out.println("session : " + session);

                                if (session.getAttribute("kakaoEmail") != null) {
                                    // 세션에 kakaoEmail 이 있으면 연동함.
                                    String kakaoEmail = (String) session.getAttribute("kakaoEmail");
                                    userService.linkKakao(loginUserId, kakaoEmail);
                                    session.invalidate();
                                    return new BaseResponse<>(SUCCESS);
                                }

                                if (session.getAttribute("googleEmail") != null) {
                                    // 세션에 googleEmail 이 있으면 연동함.
                                    String googleEmail = (String) session.getAttribute("googleEmail");
                                    userService.linkGoogle(loginUserId, googleEmail);
                                    session.invalidate();
                                    return new BaseResponse<>(SUCCESS);
                                }

                                // 로그인 성공시 친구들에게 시그널 전송
                                List<FriendVO> friendList = friendService.listFriends(loginUserId);

                                for (FriendVO friend : friendList) {
                                    System.out.println(friend.getUserId() + "에게 로그인 신호");
                                    OpenviduRequestDTO openviduRequestDto = new OpenviduRequestDTO(friend.getUserId(), "login", loginUserId);
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

                                    RestTemplate restTemplate = new RestTemplate();
                                    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                                    try {
                                        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri, requestEntity, Object.class);
                                    } catch (Exception e) {
                                        System.out.println("error: " + e);
                                    }
                                }
                                loginHistoryService.successLogin(loginUserId, loginUserIp);
                                return new BaseResponse<>(SUCCESS);
                            } else {  // 로그인 실패 시
                                if (session != null) {
                                    session.invalidate();
                                }
                                // 로그인 실패 시 카운트 시작.
                                int remainTime = loginHistoryService.failLogin(loginUserId, loginUserIp);
                                if (remainTime != 0) {
                                    return new BaseResponse<>(FAIL_TO_LOGIN, remainTime + "초 뒤에 다시 시도해주세요");
                                } else {
                                    return new BaseResponse<>(FAIL_TO_LOGIN, "로그인에 실패했습니다.");
                                }
                            }
                        } else {
                            throw new BaseException(NEED_RECAPTCHA);
                        }
                    } else {
                        throw new BaseException(NEED_RECAPTCHA);
                    }


                case "logout":
                    session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    return new BaseResponse<>(SUCCESS);
                /*
                 * [POST] 아이디 중복 검사
                 */
                case "isExistId":
                    String userTriedId = (String) body.get("userId");
                    boolean isExistId = userService.isExistId(userTriedId);
                    if (isExistId) {
                        throw new BaseException(ALREADY_EXIST_ID);
                    } else {
                        return new BaseResponse<>(SUCCESS);
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
                        return new BaseResponse<>(SUCCESS);
                    }

                    /*
                     * [POST] 회원 정보 보기
                     * 유저아이디, 유저닉네임, 유저사진, 유저상태메세지, 유저모꼬지이름, 유저누적공부시간, 유저랭크
                     */
                case "getUserInformation":
                    session = request.getSession(false);
                    if (session != null) {
                        String getUserNickname = (String) body.get("userNickname");
                        if (getUserNickname != null) {
                            boolean isExistNicknameForView = userService.isExistNickname(getUserNickname);
                            if (isExistNicknameForView) {
                                UserInformationVO userInformationVO = userService.getUserInformation(getUserNickname);
                                return new BaseResponse<>(userInformationVO);
                            } else {
                                throw new BaseException(PLZ_ENTER_NICKNAME);
                            }
                        } else {
                            throw new BaseException(PLZ_ENTER_NICKNAME);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
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
                    return new BaseResponse<>(SUCCESS);

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
                                session.invalidate();
                                return new BaseResponse<>(SUCCESS);
                            } else {
                                session.invalidate();
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
                                session.invalidate();
                                return new BaseResponse<>(SUCCESS);
                            } else {
                                throw new BaseException(FAIL_TO_DELETE_USER);
                            }
                        } else {
                            session.invalidate();
                            throw new BaseException(NEED_LOGIN);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * [POST] 비밀번호 변경
                     */
                case "modifyPassword":
                    session = request.getSession(false);
                    if (session != null) {
                        User originUser = (User) session.getAttribute("User");

                        String originUserId = originUser.getUserId();
                        String originPassword = (String) body.get("userPassword");
                        String newPassword = (String) body.get("newPassword");

                        UserLoginDTO userOriginDTO = new UserLoginDTO(originUserId, originPassword);
                        boolean isMatch = userService.login(userOriginDTO);

                        if (isMatch) {
                            userService.modifyPassword(originUserId, newPassword);
                            // session.invalidate(); 
                            return new BaseResponse<>(SUCCESS);
                        } else {
                            throw new BaseException(NOT_MATCH_PASSWORD);
                        }

                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                case "modifyNickname":
                    session = request.getSession(false);
                    if (session != null) {
                        User modifyNicknameUser = (User) session.getAttribute("User");

                        // 닉네임 중복 확인 받았다는 가정 하에 ...
                        String modifyNicknameUserId = modifyNicknameUser.getUserId();
                        String newNickname = (String) body.get("newNickname");

                        userService.modifyNickname(modifyNicknameUserId, newNickname);
                        return new BaseResponse<>(SUCCESS);
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * 이메일 변경을 위한 인증
                     */
                case "sendEmailForModifyEmail":
                    session = request.getSession(false);
                    if (session != null) {
                        User userEmailModify = (User) session.getAttribute("User");
                        if (userEmailModify != null) {
                            String originTryUserEmail = (String) body.get("originTryUserEmail");
                            String originUserEmail = userService.getUserEmail(userEmailModify);

                            if (originTryUserEmail != null && originTryUserEmail.equals(originUserEmail)) {
                                String userEmailforModify = (String) body.get("userEmailforModify");
                                RegEx.isValidUserEmail(userEmailforModify);
                                String codeForModify = userService.sendEmail(userEmailforModify);

                                session.setAttribute("codeForModify", codeForModify);
                                System.out.println(codeForModify);
                                return new BaseResponse<>(SUCCESS);
                            } else {  // 이메일을 다시 입력해주세요.
                                throw new BaseException(NOT_MATCH_EMAIL);
                            }
                        } else {
                            throw new BaseException(NEED_LOGIN);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }


                    /*
                     * [POST] 이메일 변경을 위한 인증번호 확인하기 ...
                     */
                case "confirmCodeforModify":
                    String userCodeForModify = (String) body.get("userCodeForModify");
                    if (userCodeForModify == null || "".equals(userCodeForModify)) {
                        throw new BaseException(INVALID_AUTH_CODE);
                    }
                    session = request.getSession(false);
                    if (session != null) {
                        String originCodeForModify = (String) session.getAttribute("codeForModify");
                        if (originCodeForModify != null) {
                            if (userCodeForModify.equals(originCodeForModify)) {
                                session.removeAttribute("codeForModify");
                                session.setAttribute("emailChecked", "yes");
                                return new BaseResponse<>(SUCCESS);
                            } else {
                                session.setAttribute("emailChecked", "no");
                                session.invalidate();
                                throw new BaseException(INVALID_AUTH_CODE);
                            }
                        } else {
                            throw new BaseException(INVALID_AUTH_CODE);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                case "modifyEmail":
                    session = request.getSession(false);
                    if (session != null) {
                        String emailChecked = (String) session.getAttribute("emailChecked");
                        if (emailChecked != null && emailChecked.equals("yes")) {
                            User originUser = (User) session.getAttribute("User");

                            String originUserId = originUser.getUserId();
                            String newEmail = (String) body.get("newEmail");

                            userService.modifyEmail(originUserId, newEmail);
                            return new BaseResponse<>(SUCCESS);
                        } else {
                            session.invalidate();
                            throw new BaseException(NEED_LOGIN);
                        }

                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * [POST] 마이페이지
                     * userId, userName, userPicture, userEmail, userBirthday, userPhonenumber, userPoint
                     */
                case "getMyPage":
                    session = request.getSession(false);
                    if (session != null) {
                        User user = (User) session.getAttribute("User");
                        String getMyPageUserId = user.getUserId();
                        MyPageVO myPageVO = userService.getMyPage(getMyPageUserId);
                        return new BaseResponse<>(myPageVO);
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * [POST] 유저 상태메세지 변경
                     */
                case "ModifyUserStatusMessage":
                    User changeStatusUser = (User) session.getAttribute("User");
                    if (changeStatusUser != null) {

                        String modifyStatusUserId = changeStatusUser.getUserId();
                        if (modifyStatusUserId != null) {
                            String newStatusMessage = (String) body.get("newStatusMessage");
                            userService.modifyUserStatusMessage(modifyStatusUserId, newStatusMessage);
                            return new BaseResponse<>(SUCCESS);
                        } else {
                            throw new BaseException(NEED_LOGIN);
                        }
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }

                    /*
                     * [POST] 친구 게시판에서 모든 유저 보기.
                     * 유저 아이디, 유저 아이콘, 유저 닉네임, 친구 여부
                     */
                case "getAllUserList":
                    session = request.getSession(false);
                    if (session != null) {
                        String userIdForFriendBoard = (String) body.get("userId");
                        List<UserViewVO> userListAtFriendBoard = userService.getAllUserList(userIdForFriendBoard);
                        return new BaseResponse<>(userListAtFriendBoard);
                    } else {
                        throw new BaseException(NEED_LOGIN);
                    }
            }
        }
        throw new BaseException(NOT_MATCH_SIGN);
    }


    /*
     * 카카오 로그인
     */
    @GetMapping("kakaoOauth")
    public BaseResponse<?> kakaoOauth(@RequestParam String code, HttpServletRequest request) {
        HttpSession session;

        String access_Token = kakaoOAuthService.getKaKaoAccessToken(code);
        String kakaoEmail = kakaoOAuthService.createKakaoUser(access_Token);

        User kakaoUser = userService.isKakaoUser(kakaoEmail);
        if (kakaoUser != null) {
            session = request.getSession();
            session.setAttribute("User", kakaoUser);
            return new BaseResponse<>(SUCCESS);
        } else {
            // TODO: 프론트에서 연동 할 건지 말 건지 화면 전환해줘야함. 연동한다고 하면 이메일에 세션 들고 로그인 화면으로,,
            session = request.getSession();
            session.setAttribute("kakaoEmail", kakaoEmail);
            throw new BaseException(NEED_KAKAO_LINK);
        }
    }

    /*
     * 구글 로그인
     */
    @RequestMapping("googleOauth")
    public BaseResponse<?> googleOauth(HttpServletRequest request, @RequestParam(value = "code") String authCode, HttpServletResponse response) throws Exception {
        HttpSession session;
        String googleEamil = googleOAuthService.getGoogleAccessToken(authCode);

        User googleUser = userService.isGoogleUser(googleEamil);
        if (googleUser != null) {
            session = request.getSession();
            session.setAttribute("User", googleUser);
            return new BaseResponse<>(SUCCESS);
        } else {
            // TODO: 프론트에서 연동 할 건지 말 건지 화면 전환해줘야함. 연동한다고 하면 이메일에 세션 들고 로그인 화면으로,,
            session = request.getSession();
            session.setAttribute("googleEamil", googleEamil);
            throw new BaseException(NEED_GOOGLE_LINK);
        }
    }

    /*
     * reCAPTCHA
     */
    @PostMapping("recaptcha")
    public void isBot(@RequestBody Map response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String recaptchaResponse = (String) response.get("recaptchaResponse");
        if ("만료".equals(recaptchaResponse)) {
            boolean isNotBot = false;
            session.invalidate();
        } else {
            boolean isNotBot = ReCaptchaService.isBot(recaptchaResponse);
            session.setAttribute("recaptcha", "ok");
            System.out.println(isNotBot);
        }

    }


}
