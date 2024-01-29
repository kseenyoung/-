package com.ssafy.backend.dagak.controller;

import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.service.DagakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("dagak")
public class DagakController {

    @Autowired
    private DagakService dagakService;

    @PostMapping("")
    public BaseResponse<?> dagak(@RequestBody Map<String, Object> body, HttpServletRequest request){
        String sign = (String) body.get("sign");
//        HttpSession session = request.getSession(false);
//
//        User user = (User) session.getAttribute("User");
//        if(session == null)
//            return new BaseResponse<>(EMPTY_SESSION);

//        String userId = user.getUserId();
        String userId = "ssafy";

        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "createDagak":
                List<Map<String, String>> json = (List<Map<String, String>>) body.get("gaks");
                List<GakDto> gaks = new ArrayList<>();

                int order = 1, totalTime = 0;

                for(Map<String, String> gak: json){
                    String runningTime = gak.get("runningTime");
                    String category = gak.get("category");
                    GakDto gakDto = new GakDto(category, order++, runningTime, userId);

                    totalTime += gakDto.getRunningTime();
                    gaks.add(gakDto);
                }

                // 다각 생성
                DagakDto dagakDto = new DagakDto(userId, totalTime);
                int dagakId = dagakService.createDagak(dagakDto);

                // 각 생성
                for(GakDto gak: gaks){
                    gak.setDagakId(dagakId);
                }

                dagakService.createGak(gaks);

                return new BaseResponse<>(SUCCESS);

            case "registerDagak":

                break;

            case "modifyDagak":
                break;

            case "deleteDagak":
                break;

            case "updateEndTime":
                break;

            case "getCalendarAll":
                break;

            case "getGaks":
                break;

        }


        return new BaseResponse(NOT_MATCH_SIGN);
    }
}
