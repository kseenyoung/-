package com.ssafy.backend.room.controller;

import com.ssafy.backend.room.model.dto.RoomJoinDto;
import com.ssafy.backend.room.model.service.RoomService;
import com.ssafy.backend.utils.HttpResponseBody;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> room(@RequestBody Map<String, Object> body) throws Exception {
        String sign = (String) body.get("sign");
        switch (sign){
            case "randomRoomEnter": // 랜덤 방 입장
                System.out.println("call randomRoomEnter");
                String sessionName = (String) body.get("sessionName");
                String videoCodec = (String) body.get("videoCodec");

                RoomJoinDto roomJoinDto = new RoomJoinDto(sessionName, videoCodec);
                String token = roomService.randomRoomEnter(roomJoinDto);

                HttpResponseBody<String> httpResponseBody = new HttpResponseBody<>("방 토큰을 발급합니다.", token);
                return new ResponseEntity<>(httpResponseBody,HttpStatus.OK);
        }
        return null;
    }
}
