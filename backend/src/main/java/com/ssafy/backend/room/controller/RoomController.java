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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.EMPTY_SIGN;
import static com.ssafy.backend.common.response.BaseResponseStatus.SUCCESS_LEAVE_SESSION;

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
    public BaseResponse<?> room(@RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        String sign = (String) body.get("sign");
        String userId="yj";
        String sessionName;
        String videoCodec;
        String connectionId="";
        String studyRoom="";
        String token="";
        HttpSession session = request.getSession();

        switch (sign){
            case "enterRandomroom": // 랜덤 방 입장
                if (session != null) { // 토큰 불러오기
                    connectionId = (String) session.getAttribute("connectionId");
                    studyRoom = (String) session.getAttribute("studyRoom");
                }
                userId = (String) body.get("userId");
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                RoomEnterDto randomRoomEnterDto = new RoomEnterDto(userId,sessionName,videoCodec,connectionId,studyRoom);
                ConnectionDto connectionDto = roomService.enterRandomroom(randomRoomEnterDto);
                if (session != null) { // 토큰이랑, 현재 내가 참여하고 있는 스터디룸 들어감
                    session.setAttribute("connectionId",connectionDto.getToken());
                    session.setAttribute("studyRoom",connectionDto.getSession());
                }

                return new BaseResponse<>(connectionDto);
            case "enterMyRoom":
                System.out.println("call enterMyRoom");
                userId = (String) body.get("userId");
                System.out.println("userId: "+userId);
                RoomEnterDto defaultRoomEnterDto = new RoomEnterDto(userId, userId,"VP8");
                token = roomService.enterDefaultroom(defaultRoomEnterDto);
                if (session != null) {
                    session.setAttribute("token", token);
                }
                System.out.println(session.getAttribute("token"));
                return new BaseResponse<>(token);
            case "enterMoccojiroom": // 모꼬지(길드) 방 입장
                userId = (String) body.get("userId");
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                RoomEnterDto moccojiRoomEnterDto = new RoomEnterDto(userId,sessionName, videoCodec);
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
            case "leaveRoom":
                System.out.println("call leaveroom");
                System.out.println("request: "+request.getSession(false));
                session = request.getSession();
                if(session!=null){
                    System.out.println("session이 존재합니다");
                    token = (String) session.getAttribute("token");
                    roomService.leaveSession("ssafy12345","wss://i10a404.p.ssafy.io?sessionId=ssafy12345&token=tok_LU5E0G0Ec3OeMzEJ");
                }
                return new BaseResponse<>(SUCCESS_LEAVE_SESSION);
        }
        throw new BaseException(EMPTY_SIGN);
    }
}
