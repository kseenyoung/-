package com.ssafy.backend.room.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.service.RoomService;
import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
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
        int questionNumber;

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
                sessionName = (String) body.get("sessionName");
                String question = (String) body.get("question");

                QuestionDto questionDto = new QuestionDto(sessionName, question);
                roomService.askQuestion(questionDto);
            case "answerQuestion": // 답변하기
                sessionName = (String) body.get("sessionName");
                String answer = (String) body.get("answer");
                questionNumber = (int) body.get("questionNumber");

                AnswerDto answerDto = new AnswerDto(sessionName, answer, questionNumber);
                roomService.answerQuestion(answerDto);
            case "findAnswer": // 답변 찾기
                questionNumber = (int) body.get("answer");
                List<AnswerDto> answerDtos = roomService.findAnswerByQuestionId(questionNumber);
                return new ResponseEntity<>(new HttpResponseBody<>("답변을 불러옵니다.",answerDtos),HttpStatus.OK);
        }
        return null;
    }
}
