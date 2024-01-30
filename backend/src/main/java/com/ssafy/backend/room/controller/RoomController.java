package com.ssafy.backend.room.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.ConnectionDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.service.RoomService;
import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.EMPTY_SIGN;

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
    public BaseResponse<?> room(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        String userId="yj";
        String sessionName;
        String videoCodec;
        String token;

        switch (sign){
            case "enterRandomroom": // 랜덤 방 입장
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");
                RoomEnterDto randomRoomEnterDto = new RoomEnterDto(sessionName, videoCodec);
                ConnectionDto connectionDto = roomService.enterRandomroom(randomRoomEnterDto);

                return new BaseResponse<>(connectionDto);
            case "enterMyRoom":
                userId = (String) body.get("userId");
                RoomEnterDto defaultRoomEnterDto = new RoomEnterDto(userId, "VP8");
                token = roomService.enterDefaultroom(defaultRoomEnterDto);

                return new BaseResponse<>(token);
            case "enterMoccojiroom": // 모꼬지(길드) 방 입장
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                RoomEnterDto moccojiRoomEnterDto = new RoomEnterDto(sessionName, videoCodec);
                token = roomService.enterMoccojiroom(moccojiRoomEnterDto);

                return new BaseResponse<>(token);
            case "askQuestion": // 질문하기
                sessionName = (String) body.get("session");
                String qustionData = (String) body.get("data");

                System.out.println("sessionName: " + sessionName);
                QuestionDto questionDto = new QuestionDto(userId, sessionName, qustionData);
                questionDto = roomService.askQuestion(questionDto);

                return new BaseResponse<>(questionDto);
            case "answerQuestion": // 답변하기
                sessionName = (String) body.get("session");
                String answerData = (String) body.get("data");
                String questionId = (String) body.get("questionId");

                AnswerDto answerDto = new AnswerDto(userId,sessionName,answerData,questionId);
                answerDto = roomService.answerQuestion(answerDto);

                return new BaseResponse<>(answerDto);
            case "findAnswer": // 답변 찾기
                questionId = (String) body.get("questionId");
                List<AnswerDto> answerDtos = roomService.findAnswerByQuestionId(questionId);

                return new BaseResponse<>(answerDtos);
        }
        throw new BaseException(EMPTY_SIGN);
    }
}
