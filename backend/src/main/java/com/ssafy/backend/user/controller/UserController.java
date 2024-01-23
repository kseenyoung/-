package com.ssafy.backend.user.controller;

import com.ssafy.backend.friend.model.vo.FriendListVO;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.friend.service.FriendService;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.service.RoomService;
import com.ssafy.backend.user.model.dto.OpenviduRequestDto;
import com.ssafy.backend.user.model.dto.UserLoginDto;

import com.ssafy.backend.user.model.dto.UserSignupDto;
import com.ssafy.backend.user.model.domain.User;
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
import java.util.Map;

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
                    try {
                        boolean isExistNickname = userService.isExistNickname(userTriedNickname);
                        if (isExistNickname){
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
                    if (viewUserNickname!=null){
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


            }
        }
        return response;
    }
}
