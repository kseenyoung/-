package com.ssafy.backend.user.controller;

import com.ssafy.backend.exception.MyException;
import com.ssafy.backend.user.model.UserSignupDto;
import com.ssafy.backend.user.model.service.UserService;
import com.ssafy.backend.utils.HttpResponseBody;
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

    @PostMapping("/test")
    public void Test(@RequestBody UserSignupDto dto) throws Exception {
        System.out.println(dto);
        userService.test(dto);
        System.out.println("성공");
    }

    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> user(@RequestBody Map<String, Object> body) throws MyException {
        String sign = (String) body.get("sign");
        ResponseEntity<HttpResponseBody<?>> response = null;

        if (sign != null) {
            switch (sign) {
                case "signup":
                    String userId = (String) body.get("userId");
                    String userBirthday = (String) body.get("userBirthday");
                    String userName = (String) body.get("userName");
                    String userPassword = (String) body.get("userPassword");
                    String userPhonenumber = (String) body.get("userPhonenumber");
                    String userEmail = (String) body.get("userEmail");
                    String userNickname = (String) body.get("userNickname");

                    UserSignupDto userSignupDto = new UserSignupDto(userId, userBirthday, userName, userPassword, userPhonenumber, userEmail, userNickname);
                    System.out.println(userSignupDto);
                    try {
                        userService.signup(userSignupDto);
                    } catch (Exception e) {
                        // throw new MyException("회원가입 실패", HttpStatus.BAD_REQUEST);
                        HttpResponseBody<String> responseBody = new HttpResponseBody<>("Fail", "회원 가입 실패!!!");
                        System.out.println(e);
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }

                    HttpResponseBody<String> responseBody = new HttpResponseBody<>("OK", "회원 가입 성공!!!");
                    return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }

        }
        return response;
    }
}
