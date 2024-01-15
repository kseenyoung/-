package com.ssafy.backend.room.controller;

import com.ssafy.backend.room.model.dto.RoomJoinDto;
import com.ssafy.backend.room.model.service.RoomService;
import com.ssafy.backend.utils.HttpResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> room(@RequestBody Map<String, Object> body){
        String sign = (String) body.get("sign");
        switch (sign){
            case "randomEnter": // 랜덤 방 입장
                String sessionName = (String) body.get("sessionName");
                String videoCodec = (String) body.get("videoCodec");
                RoomJoinDto roomJoinDto = new RoomJoinDto(sessionName, videoCodec);

                return null;
        }
        return null;
    }
}
