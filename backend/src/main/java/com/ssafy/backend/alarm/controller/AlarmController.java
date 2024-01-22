package com.ssafy.backend.alarm.controller;

import com.ssafy.backend.alarm.mode.dto.CheckAlarmDto;
import com.ssafy.backend.alarm.model.domain.Alarm;
import com.ssafy.backend.alarm.model.dto.ListOfAllAlarmDto;
import com.ssafy.backend.alarm.model.dto.ListOfUncheckedtAlarmDto;
import com.ssafy.backend.alarm.model.dto.ReqestAlarmDto;
import com.ssafy.backend.alarm.service.AlarmService;
import com.ssafy.backend.common.utils.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.utils.response.BaseResponseStatus.*;

@RestController
@RequestMapping("alarms")
public class AlarmController {

    @Autowired
    AlarmService alarmService;

    @PostMapping("")
    public BaseResponse<?> alarm(@RequestBody Map<String, String> body){

        String sign = body.get("sign");
        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "request":

                ReqestAlarmDto reqestAlarmDto = new ReqestAlarmDto();
                alarmService.requestAlarm(reqestAlarmDto);

                return new BaseResponse(SUCCESS);
            case "check":

                CheckAlarmDto checkAlarmDto = new CheckAlarmDto();
                alarmService.checkAlarm(checkAlarmDto);

                return new BaseResponse<>(SUCCESS);

        }


        return new BaseResponse(NOT_MATCH_SIGN);
    }

    @GetMapping("listOfAll")
    public BaseResponse<?> listofAllAlarm(){
        ListOfAllAlarmDto listOfAllAlarmDto = new ListOfAllAlarmDto();
        List<Alarm> listOfAllAlarmVO = alarmService.listofAllAlarm(listOfAllAlarmDto);

        return new BaseResponse<>(listOfAllAlarmVO);
    }

    @GetMapping("listOfUnchecked")
    public BaseResponse<?> listofUncheckedAlarm(){
        ListOfUncheckedtAlarmDto listOfUncheckedAlarmDto = new ListOfUncheckedtAlarmDto();
        List<Alarm> listOfUncheckedAlarmVO = alarmService.listOfUncheckedAlarm(listOfUncheckedAlarmDto);

        return new BaseResponse<>(listOfUncheckedAlarmVO);
    }


}


