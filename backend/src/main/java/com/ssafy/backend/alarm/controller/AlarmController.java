package com.ssafy.backend.alarm.controller;

import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;
import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("alarms")
@Slf4j
public class AlarmController {

    @Autowired
    AlarmService alarmService;

    @Autowired
    UserService userService;

    @PostMapping("")
    public BaseResponse<?> alarm(@RequestBody Map<String, String> body, HttpServletRequest response){

        String sign = body.get("sign");
        HttpSession session = response.getSession(false);

        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "request":
                String tagId = body.get("tagId");
                String requestedUserId = body.get("requestedUserId");
//                User user = (User) session.getAttribute("session");
//                String requestUserId = user.getUserId();
                String requestUserId = "ssafy";  // session ID

                ReqestAlarmDto reqestAlarmDto = new ReqestAlarmDto(requestUserId, requestedUserId, tagId);
                log.info("requestAlarmDto : {}", reqestAlarmDto);

                alarmService.requestAlarm(reqestAlarmDto);

                return new BaseResponse(SUCCESS);
            case "check":
//                User user = (User) session.getAttribute("session");
//                String checkUserId = user.getUserId();
                String checkUserId = "ssafy";  // session ID
                String alarmId = body.get("alarmId");

                CheckAlarmDto checkAlarmDto = new CheckAlarmDto(checkUserId, alarmId);
                alarmService.checkAlarm(checkAlarmDto);

                return new BaseResponse<>(SUCCESS);

        }


        return new BaseResponse(NOT_MATCH_SIGN);
    }

    @GetMapping("listOfAll")
    public BaseResponse<?> listofAllAlarm(HttpServletRequest response){
//        HttpSession session = response.getSession(false);
//        User user = (User) session.getAttribute("User");
//        String userId = user.getUserId();
        String userId = "ssafy";

        userService.isExistUser(userId);
        List<Alarm> listOfAllAlarmVO = alarmService.listofAllAlarm(userId);

        return new BaseResponse<>(listOfAllAlarmVO);
    }

    @GetMapping("listOfUnchecked")
    public BaseResponse<?> listofUncheckedAlarm(HttpServletRequest response){
//        HttpSession session = response.getSession(false);
//        User user = (User) session.getAttribute("User");
//        String userId = user.getUserId();
        String userId = "ssafy";

        List<Alarm> listOfUncheckedAlarmVO = alarmService.listOfUncheckedAlarm(userId);

        return new BaseResponse<>(listOfUncheckedAlarmVO);
    }


}


