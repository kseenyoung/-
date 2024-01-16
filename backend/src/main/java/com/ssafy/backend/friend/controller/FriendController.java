package com.ssafy.backend.friend.controller;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.friend.model.service.FriendService;
import com.ssafy.backend.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("")
    public ResponseEntity<HttpResponseBody<String>> friend(@RequestBody Map<String, String> body, HttpServletRequest request) throws MyException {
        String sign = body.get("sign");
        HttpSession session = request.getSession();

        if(sign == null){
            return new ResponseEntity<HttpResponseBody<String>>(new HttpResponseBody<String>("msg", "성공^^"), HttpStatus.OK);
        }

        try{
            switch (sign){
                case "request":
//                    User user = (User) session.getAttribute("User");
//                    String userId = user.getUserId();
                    String userId = "ssafy";  // request session Id
                    String userId2 = body.get("userId");

                    friendService.requestFriend(userId, userId2);

                    return ResponseEntity.ok(new HttpResponseBody<String>("성공^^", "성공^^"));

                case "access":
//                    User user = (User) session.getAttribute("User");
//                    String userId = user.getUserId();
                    String accessUserId = "ssafy";  // request session Id
                    String accessUuserId2 = body.get("userId");

                    friendService.accessFriend(accessUserId, accessUuserId2);

                    return ResponseEntity.ok(new HttpResponseBody<String>(
                            new StringBuilder().append(accessUserId).append("님이 ").append(accessUuserId2).append("님의 친구요청을 받았습니다.").toString(), "성공^^"));
            }
        } catch (MyException e){
            return ResponseEntity.ok(new HttpResponseBody<String>("[Exception]유감..", "유감.."));
        }
        return ResponseEntity.ok(new HttpResponseBody<String>("유감..", "유감.."));
    }


}
