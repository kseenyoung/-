package com.ssafy.backend.user.controller;

import com.ssafy.backend.user.model.UserLoginDto;

import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.service.UserService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("test")
    public void test(@RequestBody Map<String, Object> body) throws Exception {
        String userLoginId = (String) body.get("userLoginId");
        System.out.println(userLoginId);
        boolean isExistId = userService.isExistId(userLoginId);
        System.out.println(isExistId);
    }



    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> user(@RequestBody Map<String, Object> body) throws Exception {
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
                 */
                case "login":
                    String loginUserId = (String) body.get("userId");
                    String loginUserPassword = (String) body.get("userPassword");
                    UserLoginDto userLoginDto = new UserLoginDto(loginUserId, loginUserPassword);
                    if (userService.login(userLoginDto)){
                        responseBody = new HttpResponseBody<>("OK", "로그인 성공!!!");
                        return new ResponseEntity<>(responseBody, HttpStatus.OK);
                    } else {
                        responseBody = new HttpResponseBody<>("Fail", "로그인 실패!!!");
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }

                /*
                 * [POST] 아이디 중복 검사
                 */
                case "isExistId":
                    String userTriedId = (String) body.get("userId");
                    try {
                        boolean isExistId = userService.isExistId(userTriedId);
                        if (isExistId){
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
                    System.out.println(userTriedNickname);
                    try {
                        boolean isExistNickname = userService.isExistNickname(userTriedNickname);
                        if (isExistNickname){
                            responseBody = new HttpResponseBody<>("Fail", "이미 존재하는 닉네임입니다.");
                            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return response;
    }
}
