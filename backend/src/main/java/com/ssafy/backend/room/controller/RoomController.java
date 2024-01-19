package com.ssafy.backend.room.controller;

import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.QuestionDto2;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.model.service.RoomService;
import com.ssafy.backend.utils.HttpResponseBody;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Base64;
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
		        break;
            case "answerQuestion": // 답변하기
                sessionName = (String) body.get("sessionName");
                String answer = (String) body.get("answer");
                questionNumber = (int) body.get("questionNumber");

                AnswerDto answerDto = new AnswerDto(sessionName, answer, questionNumber);
                roomService.answerQuestion(answerDto);
		        break;
            case "findAnswer": // 답변 찾기
                questionNumber = (int) body.get("answer");
                List<AnswerDto> answerDtos = roomService.findAnswerByQuestionId(questionNumber);
                return new ResponseEntity<>(new HttpResponseBody<>("답변을 불러옵니다.",answerDtos),HttpStatus.OK);
            case "test":
                URI uri = UriComponentsBuilder
                        .fromUriString("https://capstone-6.shop:4443")
                        .path("/openvidu/api/signal")
                        .encode()
                        .build()
                        .toUri();

                QuestionDto2 questionDto2 = new QuestionDto2();
                questionDto2.setSession("SessionA");
                questionDto2.setData("안녕 애두라!");
                String secret = "Basic "+OPENVIDU_SECRET;
                secret = Base64.getEncoder().encodeToString(secret.getBytes());

                RequestEntity<QuestionDto2> requestEntity = RequestEntity
                        .post(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", secret)
                        .header("Access-Control-Allow-Origin", "*")
                        .body(questionDto2);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                ResponseEntity<QuestionDto2> responseEntity = restTemplate.exchange(
                        requestEntity, QuestionDto2.class
                );

                break;

        }
        return null;
    }
}
