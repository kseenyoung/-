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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> room(@RequestBody Map<String, Object> body) throws OpenViduJavaClientException, OpenViduHttpException {
        String sign = (String) body.get("sign");
        switch (sign){
            case "randomRoomEnter": // 랜덤 방 입장
                System.out.println("call randomRoomEnter");
                String sessionName = (String) body.get("sessionName");
                String videoCodec = (String) body.get("videoCodec");
                Session session;

                // 세션에 해당하는 방이 존재하는지 확인
                session = openvidu.getActiveSession(sessionName);
                if(session == null){
                    // 방이 없다면 하나 생성하라
                    System.out.println("기존에"+sessionName+"방이 없으므로 새롭게 생성합니다.");
                    SessionProperties properties = SessionProperties.fromJson(body).build();
                    session = openvidu.createSession(properties);
                }
                if(session != null){
                    System.out.println("기존에"+sessionName+"방이 있으므로 기존 방에 입장합니다.");
                    // 방이 있다면 기존방의 갯수 / 인원수를 확인하고 해당 방에 입장한다

                }

                ConnectionProperties properties = new ConnectionProperties.Builder().build();
                Connection connection = session.createConnection(properties);
                HttpResponseBody httpResponseBody = new HttpResponseBody<>("방 토큰을 발급합니다.", connection.getToken());
                return new ResponseEntity<>(httpResponseBody,HttpStatus.OK);
        }
        return null;
    }
}
