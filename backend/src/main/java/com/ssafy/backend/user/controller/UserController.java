package com.ssafy.backend.user.controller;


import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.utils.RegEx;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.friend.service.FriendService;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.dto.OpenviduRequestDto;
import com.ssafy.backend.loginhistory.service.LoginHistoryService;
import com.ssafy.backend.user.model.dto.UserLoginDto;

import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.vo.UserViewVO;
import com.ssafy.backend.user.service.UserService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.ALREADY_EXIST_USER;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_MEMBER;


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

    @PostMapping("test")
    public void test(@RequestBody Map<String, Object> body) throws Exception {
        RegEx.isValidUserEmail("");
    }


    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> user(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        HttpSession session = null;
        ResponseEntity<HttpResponseBody<?>> response = null;

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
                        HttpResponseBody<String> responseBody = new HttpResponseBody<>("Fail", "회원 가입 실패!!!");
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }
                    HttpResponseBody<String> responseBody = new HttpResponseBody<>("OK", "회원 가입 성공!!!");
                    return new ResponseEntity<>(responseBody, HttpStatus.OK);

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
                        responseBody = new HttpResponseBody<>("OK", "로그인 성공!!!");
                        User user = new User(loginUserId);
                        session = request.getSession();
                        session.setAttribute("User", user);
                        
                        // 로그인 성공시 친구들에게 시그널 전송
                        List<FriendVO> friendList = friendService.listFriends(loginUserId);
                        System.out.println("친구 목록: "+friendList);

                        for(FriendVO friend: friendList){
                            System.out.println(friend.getUserId()+"에게 친구요청");
                            OpenviduRequestDto openviduRequestDto = new OpenviduRequestDto(friend.getUserId(),loginUserId);
                            URI uri = UriComponentsBuilder
                                    .fromUriString(OPENVIDU_URL)
                                    .path("/openvidu/api/signal")
                                    .encode()
                                    .build()
                                    .toUri();

                            String secret = "Basic "+OPENVIDU_SECRET;
                            secret = Base64.getEncoder().encodeToString(secret.getBytes());

                            RequestEntity<OpenviduRequestDto> requestEntity = RequestEntity
                                    .post(uri)
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                                    .body(openviduRequestDto);

                            RestTemplate restTemplate = new RestTemplate();
                            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                            ResponseEntity<QuestionDto> responseEntity = restTemplate.exchange(
                                    uri, HttpMethod.POST,requestEntity, QuestionDto.class
                            );

                        }

                        loginHistoryService.successLogin(loginUserId, loginUserIp);
                        return new ResponseEntity<>(responseBody, HttpStatus.OK);
                    } else {  // 로그인 실패 시 카운트 시작.
                        int remainTime = loginHistoryService.failLogin(loginUserId, loginUserIp);
                        if (remainTime == 0) {
                            responseBody = new HttpResponseBody<>("Fail", "로그인 실패!!!");
                        } else {
                            // TODO : 프론트에 전달해 줄 데이터를 바꿔야 할 것 같음...
                            responseBody = new HttpResponseBody<>("Fail", remainTime + "초 이후 로그인이 가능합니다.");
                        }
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }

                case "logout":
                    session = request.getSession(false);
                    if (session!=null){
                        session.invalidate();
                    }
                    /*
                     * [POST] 아이디 중복 검사
                     */
                case "isExistId":
                    String userTriedId = (String) body.get("userId");
                    try {
                        boolean isExistId = userService.isExistId(userTriedId);
                        if (isExistId) {
                            throw new BaseException(ALREADY_EXIST_USER);
//                            responseBody = new HttpResponseBody<>("Fail", "이미 존재하는 아이디입니다.");
//                            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                        } else {
                            responseBody = new HttpResponseBody<>("ok", "사용 가능한 아이디입니다.");
                            return new ResponseEntity<>(responseBody, HttpStatus.OK);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                     * [POST] 닉네임 중복 검사
                     */
                case "isExistNickname":
                    String userTriedNickname = (String) body.get("userNickname");
                    try {
                        boolean isExistNickname = userService.isExistNickname(userTriedNickname);
                        if (isExistNickname) {
                            responseBody = new HttpResponseBody<>("Fail", "이미 존재하는 닉네임입니다.");
                            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                        } else {
                            responseBody = new HttpResponseBody<>("ok", " 사용 가능한 닉네임입니다.");
                            return new ResponseEntity<>(responseBody, HttpStatus.OK);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    /*
                     * [POST] 회원 정보 보기
                     * 유저아이디, 유저닉네임, 유저사진, 유저상태메세지, 유저모꼬지이름, 유저누적공부시간, 유저랭크
                     */
                case "viewUserInformation":
                    String viewUserNickname = (String) body.get("userNickname");
                    if (viewUserNickname != null) {
                        boolean isExistNickname = userService.isExistNickname(viewUserNickname);
                        if (isExistNickname) {
                            UserViewVO userViewVO = userService.viewUserInformation(viewUserNickname);
                            return new ResponseEntity<>(new HttpResponseBody<>("ok", userViewVO), HttpStatus.BAD_REQUEST);
                        } else {
                            return new ResponseEntity<>(new HttpResponseBody<>("Fail", "존재하지 않는 회원입니다."), HttpStatus.BAD_REQUEST);
                        }

                    } else {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "닉네임을 입력해주세요."), HttpStatus.BAD_REQUEST);
                    }

                    /*
                     * [POST] 회원 가입 이메일 인증 보내기 ...
                     */
                case "sendEmailForSignUp":
                    String userEmailForAuth = (String) body.get("userEmail");
                    RegEx.isValidUserEmail(userEmailForAuth);
                    try {
                        String codeForAuth = userService.sendEmail(userEmailForAuth);
                        session = request.getSession();
                        session.setAttribute("codeForAuth", codeForAuth);
                        System.out.println(codeForAuth);
                        return new ResponseEntity<>(new HttpResponseBody<>("ok", "메일을 전송했습니다. 메일함을 확인해주세요."), HttpStatus.OK);
                    } catch (Exception e) {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "메일 전송에 실패했습니다."), HttpStatus.BAD_REQUEST);
                    }

                    /*
                     * [POST] 인증번호 확인하기 ...
                     */
                case "confirmCode":
                    String userCodeForAuth = (String) body.get("userCodeForAuth");
                    if (userCodeForAuth == null || "".equals(userCodeForAuth)) {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "올바른 인증번호를 입력해주세요.."), HttpStatus.BAD_REQUEST);
                    }

                    session = request.getSession(false);
                    if (session!=null) {
                        String originCodeForAuth = (String) session.getAttribute("codeForAuth");
                        if (originCodeForAuth!=null) {
                            if (userCodeForAuth.equals(originCodeForAuth)) {
                                session.removeAttribute("codeForAuth");
                                return new ResponseEntity<>(new HttpResponseBody<>("ok", "인증에 성공했습니다."), HttpStatus.OK);
                            } else {
                                return new ResponseEntity<>(new HttpResponseBody<>("Fail", "인증번호가 일치하지 않습니다."), HttpStatus.BAD_REQUEST);
                            }
                        }
                    }

                    /*
                     * [POST] 회원 탈퇴 ...
                     */
                case "deleteUser":
                    session = request.getSession(false);
                    if(session!=null){
                        User deleteUser = (User) session.getAttribute("User");
                        if(deleteUser!=null){
                            String deleteUserId = deleteUser.getUserId();
                            String deleteUserPassword = (String) body.get("userPassword");
                            boolean isSuccess = userService.deleteUser(deleteUserId, deleteUserPassword);
                            if (isSuccess){
                                return new ResponseEntity<>(new HttpResponseBody<>("ok", "회원 탈퇴 성공."), HttpStatus.BAD_REQUEST);
                            } else {
                                return new ResponseEntity<>(new HttpResponseBody<>("Fail", "회원 탈퇴 실패."), HttpStatus.BAD_REQUEST);
                            }
                        } else {
                            return new ResponseEntity<>(new HttpResponseBody<>("Fail", "로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
                    }

                    /*
                     * [POST] 비밀번호 변경
                     */
                case "changePassword":
                    session = request.getSession(false);
                    if (session!=null){
                        User originUser = (User) session.getAttribute("User");

                        String originUserId = originUser.getUserId();
                        String originPassword = (String) body.get("userPassword");
                        String newPassword = (String) body.get("newPassword");

                        UserLoginDto userOriginDto = new UserLoginDto(originUserId, originPassword);
                        boolean isMatched = userService.login(userOriginDto);

                        if (isMatched){  // TODO:  try catch 필요
                            userService.changePassword(originUserId, newPassword);
                            session.invalidate();
                            return new ResponseEntity<>(new HttpResponseBody<>("ok", "비밀번호 변경 성공. 다시 로그인 하세요"), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new HttpResponseBody<>("Fail", "기존 비밀번호가 일치하지 않습니다."), HttpStatus.BAD_REQUEST);
                        }

                    } else {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
                    }

                case "changeNickname":
                    session = request.getSession(false);
                    if (session!=null){
                        User changeNicknameUser = (User) session.getAttribute("User");

                        // 닉네임 중복 확인 받았다는 가정 하에 ...
                        String changeNicknameUserId = changeNicknameUser.getUserId();
                        String newNickname = (String) body.get("newNickname");

                        userService.changeNickname(changeNicknameUserId, newNickname);
                        return new ResponseEntity<>(new HttpResponseBody<>("ok", "닉네임이 변경되었습니다."), HttpStatus.OK);

                    } else {
                        return new ResponseEntity<>(new HttpResponseBody<>("Fail", "로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
                    }
            }
        }
        return response;
    }
}
