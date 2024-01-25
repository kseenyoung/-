package com.ssafy.backend.room.controller;

import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.service.RoomService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.user.model.domain.User;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public ResponseEntity<HttpResponseBody<?>> room(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        ResponseEntity<HttpResponseBody<?>> response = null;
        HttpSession session = request.getSession(false);
//        User user = (User) session.getAttribute("User");
//        String userId = user.getUserId();
        String userId="yj";
        String sessionName;
        String videoCodec;
        String token;

        switch (sign){
            case "enterRandomroom": // 랜덤 방 입장
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");
		System.out.println("SessionName / VideoCodec"+sessionName+","+videoCodec);

                RoomEnterDto randomRoomEnterDto = new RoomEnterDto(sessionName, videoCodec);
                token = roomService.enterRandomroom(randomRoomEnterDto);

                return new ResponseEntity<>(new HttpResponseBody<>(sessionName+"방 토큰을 발급합니다.", token),HttpStatus.OK);
            case "enterMyRoom":
                userId = (String) body.get("userId");
		System.out.println("userId :"+userId );
                RoomEnterDto defaultRoomEnterDto = new RoomEnterDto(userId, "VP8");
                token = roomService.enterDefaultroom(defaultRoomEnterDto);
                return new ResponseEntity<>(new HttpResponseBody<>("기본방 토큰을 발급합니다.", token),HttpStatus.OK);
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
                QuestionDto questionDto = new QuestionDto(userId, sessionName, qustionData);
                questionDto = roomService.askQuestion(questionDto);
                return new ResponseEntity<>(new HttpResponseBody<>("질문을 등록합니다.",questionDto),HttpStatus.OK);
            case "answerQuestion": // 답변하기
                sessionName = (String) body.get("session");
                String answerData = (String) body.get("data");
                String questionId = (String) body.get("questionId");
                AnswerDto answerDto = new AnswerDto(userId,sessionName,answerData,questionId);
                answerDto = roomService.answerQuestion(answerDto);
                return new ResponseEntity<>(new HttpResponseBody<>("답변을 등록합니다.",answerDto),HttpStatus.OK);
            case "findAnswer": // 답변 찾기
                questionId = (String) body.get("questionId");
                List<AnswerDto> answerDtos = roomService.findAnswerByQuestionId(questionId);
                return new ResponseEntity<>(new HttpResponseBody<>("답변을 불러옵니다.",answerDtos),HttpStatus.OK);
        }
        return null;
    }
}
