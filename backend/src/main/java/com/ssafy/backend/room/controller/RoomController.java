package com.ssafy.backend.room.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.service.RoomService;
import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
                sessionName = (String) body.get("session");
                String qustionData = (String) body.get("data");

                System.out.println("sessionName: "+sessionName);
                QuestionDto questionDto = new QuestionDto(sessionName, qustionData);
                roomService.askQuestion(questionDto);
		        break;
            case "answerQuestion": // 답변하기
                sessionName = (String) body.get("session");
                String answerData = (String) body.get("data");
                String questionId = (String) body.get("questionId");

                AnswerDto answerDto = new AnswerDto(sessionName, answerData, questionId);
                roomService.answerQuestion(answerDto);
		        break;
            case "findAnswer": // 답변 찾기
                sessionName = (String) body.get("session");
                questionId = (String) body.get("questionId");
                List<AnswerDto> answerDtos = roomService.findAnswerByQuestionId(questionId);
                return new ResponseEntity<>(new HttpResponseBody<>("답변을 불러옵니다.",answerDtos),HttpStatus.OK);
            case "test":
                URI uri = UriComponentsBuilder
                        .fromUriString("https://capstone-6.shop:4443")
                        .path("/openvidu/api/signal")
                        .encode()
                        .build()
                        .toUri();

                QuestionDto questionDto2 = new QuestionDto();
                questionDto2.setSession("SessionA1");
                questionDto2.setData("안녕 애두라!");
                String secret = "Basic "+OPENVIDU_SECRET;
                secret = Base64.getEncoder().encodeToString(secret.getBytes());

                RequestEntity<QuestionDto> requestEntity = RequestEntity
                        .post(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                        .body(questionDto2);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                ResponseEntity<QuestionDto> responseEntity = restTemplate.exchange(
                        uri,HttpMethod.POST,requestEntity, QuestionDto.class
                );
                break;
        }
        return null;
    }
}
