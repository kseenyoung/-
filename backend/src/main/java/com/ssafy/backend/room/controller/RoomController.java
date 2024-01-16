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
        ResponseEntity<HttpResponseBody<?>> response = null;

        switch (sign){
            case "randomRoomEnter": // 랜덤 방 입장
                System.out.println("call randomRoomEnter");
                String randomSessionName = (String) body.get("sessionName");
                String randomVideoCodec = (String) body.get("videoCodec");

                RoomJoinDto randomRoomJoinDto = new RoomJoinDto(randomSessionName, randomVideoCodec);
                String randomRoomToken = roomService.randomRoomEnter(randomRoomJoinDto);

                return new ResponseEntity<>(new HttpResponseBody<>(randomSessionName+"방 토큰을 발급합니다.", randomRoomToken),HttpStatus.OK);
            case "moccojiRoomEnter": // 모꼬지(길드) 방 입장
                System.out.println("call moccojiRoomEnter");
                String moccojiSessionName = (String) body.get("sessionName");
                String moccojiVideoCodec = (String) body.get("videoCodec");

                RoomJoinDto moccojiRoomJoinDto = new RoomJoinDto(moccojiSessionName, moccojiVideoCodec);
                String moccojiRoomToken = roomService.moccojiRoomEnter(moccojiRoomJoinDto);

                return new ResponseEntity<>(new HttpResponseBody<>(moccojiSessionName+"방 토큰을 발급합니다.", moccojiRoomToken),HttpStatus.OK);

        }
        return null;
    }
}
