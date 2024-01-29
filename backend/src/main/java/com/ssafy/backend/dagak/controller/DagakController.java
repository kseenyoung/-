package com.ssafy.backend.dagak.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.service.DagakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public BaseResponse<?> dagak(@RequestBody Map<String, String> body, HttpServletRequest request){
        String sign = body.get("sign");
        HttpSession session = request.getSession(false);
        ObjectMapper om = new ObjectMapper();

        if(session == null)
            return new BaseResponse<>(EMPTY_SESSION);

        if(sign == null)
            return new BaseResponse(EMPTY_SIGN);

        switch (sign){
            case "createDagak":
                try{
                    List<GakDto> gaks = om.readValue(body.get("gaks"), ArrayList.class);
                    System.out.println(gaks);
                } catch(JsonProcessingException e){
                    throw new BaseException(JSON_PARSING_ERROR);
                }

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
