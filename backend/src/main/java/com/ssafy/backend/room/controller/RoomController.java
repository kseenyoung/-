package com.ssafy.backend.room.controller;

import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.model.service.RoomService;
import com.ssafy.backend.utils.HttpResponseBody;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequestMapping("room")
@CrossOrigin(origins = "*")
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
        String sessionName;
        String videoCodec;
        String token;

        switch (sign){
            case "enterRandomroom": // 랜덤 방 입장
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                RoomEnterDto randomRoomEnterDto = new RoomEnterDto(sessionName, videoCodec);
                token = roomService.enterRandomroom(randomRoomEnterDto);

                return new ResponseEntity<>(new HttpResponseBody<>(sessionName+"방 토큰을 발급합니다.", token),HttpStatus.OK);
            case "enterMoccojiroom": // 모꼬지(길드) 방 입장
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                RoomEnterDto moccojiRoomEnterDto = new RoomEnterDto(sessionName, videoCodec);
                token = roomService.enterMoccojiroom(moccojiRoomEnterDto);

                return new ResponseEntity<>(new HttpResponseBody<>(sessionName+"방 토큰을 발급합니다.", token),HttpStatus.OK);
            case "askQuestion": // 질문하기
                String questionAskSessionName = (String) body.get("sessionName");
                String questionAskMessage = (String) body.get("message");

                QuestionDto questionDto = new QuestionDto(questionAskSessionName, questionAskMessage);
                roomService.askQuestion(questionDto);

        }
        return null;
    }
}
