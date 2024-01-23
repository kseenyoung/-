package com.ssafy.backend.user.controller;

import com.ssafy.backend.common.utils.RegEx;
import com.ssafy.backend.loginhistory.service.LoginHistoryService;
import com.ssafy.backend.user.model.dto.UserLoginDto;

import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.vo.UserViewVO;
import com.ssafy.backend.user.service.UserService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoginHistoryService loginHistoryService;


    @PostMapping("test")
    public void test(@RequestBody Map<String, Object> body) throws Exception {
        String userLoginId = (String) body.get("userLoginId");
        System.out.println(userLoginId);
        boolean isExistId = userService.isExistId(userLoginId);
        System.out.println(isExistId);
    }


    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> user(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
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

                    /*
                     * [POST] 아이디 중복 검사
                     */
                case "isExistId":
                    String userTriedId = (String) body.get("userId");
                    try {
                        boolean isExistId = userService.isExistId(userTriedId);
                        if (isExistId) {
                            responseBody = new HttpResponseBody<>("Fail", "이미 존재하는 아이디입니다.");
                            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
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
                     * [POST] 이메일 인증 보내기 ...
                     */
                case "sendEmailForSignUp":
                    String userEmailForAuth = (String) body.get("userEmail");
                    RegEx.isValidUserEmail(userEmailForAuth);
                    try {
                        String codeForAuth = userService.sendEmail(userEmailForAuth);
                        HttpSession session = request.getSession();
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

                    HttpSession session = request.getSession(false);
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
            }
        }
        return response;
    }
}
