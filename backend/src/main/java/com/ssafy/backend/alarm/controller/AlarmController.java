package com.ssafy.backend.alarm.controller;

import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.CheckAlarmDTO;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDTO;
import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.user.model.domain.User;
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
        User user = (User) session.getAttribute("session");
        String userId = user.getUserId();

        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "requestAlarm":
                String tagId = body.get("tagId");
                String requestedUserId = body.get("requestedUserId");

                ReqestAlarmDTO reqestAlarmDTO = new ReqestAlarmDTO(userId, requestedUserId, tagId);
                log.info("requestAlarmDto : {}", reqestAlarmDTO);

                alarmService.requestAlarm(reqestAlarmDTO);

                return new BaseResponse(SUCCESS);
            case "checkAlarm":
                String alarmId = body.get("alarmId");

                CheckAlarmDTO checkAlarmDTO = new CheckAlarmDTO(userId, alarmId);
                alarmService.checkAlarm(checkAlarmDTO);

                return new BaseResponse<>(SUCCESS);
        }

        return new BaseResponse(NOT_MATCH_SIGN);
    }

    @GetMapping("getAllList")
    public BaseResponse<?> getAllList(HttpServletRequest response){
        HttpSession session = response.getSession(false);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        userService.isExistUser(userId);
        List<Alarm> getAllListVO = alarmService.getAllList(userId);

        return new BaseResponse<>(getAllListVO);
    }

    @GetMapping("getUncheckList")
    public BaseResponse<?> getUncheckList(HttpServletRequest response){
        HttpSession session = response.getSession(false);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        List<Alarm> getUncheckListVO = alarmService.getUncheckList(userId);

        return new BaseResponse<>(getUncheckListVO);
    }


}


