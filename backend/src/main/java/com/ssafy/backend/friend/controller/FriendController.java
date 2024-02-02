package com.ssafy.backend.friend.controller;

import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.friend.model.domain.UserId;
import com.ssafy.backend.friend.model.vo.FriendListVO;
import com.ssafy.backend.friend.service.FriendFacade;
import com.ssafy.backend.friend.service.FriendService;
import com.ssafy.backend.user.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    FriendFacade friendFacade;

    @PostMapping("")
    public BaseResponse<?> friend(@RequestBody Map<String, Object> body, HttpServletRequest request) throws MyException {
        String sign = (String) body.get("sign");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        if (sign == null) {
            throw new BaseException(EMPTY_SESSION);
        }

        switch (sign) {
            /**
             * [POST] /requestFriend
             * @return BaseResponse<BaseResponseStatus>
             * 친구 요청을 처리
             **/
            case "requestFriend":
                String userId2 = (String) body.get("userId");  // 요청하고싶은 친구 userId

                friendFacade.requestFriend(userId, userId2);

                return new BaseResponse<>(SUCCESS);
            /**
             * [POST] /accessFriend
             * @return BaseResponse<BaseResponseStatus>
             * 친구 요청에 대해서 승인
             **/
            case "accessFriend":
                String accessUserId2 = (String) body.get("userId");

                // 이미 요청 승인을 눌렀는지 확인
//                alarmService.aVoidDuplicateAlaram(new ReqestAlarmDTO(accessUserId2, userId, 5));
                if(friendService.isFriend(new UserId(userId, accessUserId2)))
                    throw new BaseException(ALREADY_EXIST_FRIEND);

                friendFacade.accessFriend(userId, accessUserId2);

                return new BaseResponse<>(SUCCESS);

            case "quitFriend":
                String quitUserId2 = (String) body.get("userId");

                friendService.quitFriend(userId, quitUserId2);

                return new BaseResponse<>(SUCCESS);

        }
        throw new BaseException(NOT_MATCH_SIGN);
    }

    /**
     * [GET] /friend/count
     *
     * @return ResponseEntity<HttpResponseBody < String>>
     * 친구 요청에 대해서 승인
     **/
    @GetMapping("countFriend")
    public BaseResponse<?> countFriend(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        Integer friends = friendService.countFriend(userId);

        return new BaseResponse(friends);
    }

    /**
     * [GET] /friend/list
     *
     * @return ResponseEntity<HttpResponseBody < FriendListVO>>
     * 친구 요청에 대해서 승인
     * 아이디, 닉네임, 상태메시지, 이메일, 랭킹, 총 공부 시간, 모꼬지명
     **/
    @GetMapping("getFriendList")
    public BaseResponse<?> getFriendList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        FriendListVO friendListVO = new FriendListVO(friendService.countFriend(userId), friendService.listFriends(userId));

        return new BaseResponse(friendListVO);
    }


}
