package com.ssafy.backend.room.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.room.model.dto.AnswerDTO;
import com.ssafy.backend.room.model.vo.*;
import com.ssafy.backend.room.model.dto.QuestionDTO;
import com.ssafy.backend.room.model.dto.EnterRoomDTO;
import com.ssafy.backend.room.service.RoomService;
import com.ssafy.backend.user.model.domain.User;
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

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

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
        String sessionName="";
        String videoCodec="";
        String connectionId="";
        String studyRoom="";
        String token="";
        String questionId="";
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("User");
        String userId = user.getUserId();
        String isLeave="";
        ConnectionVO connectionVO;

        switch (sign){
            case "enterRandomroom":
                if (session != null) {
                    connectionId = (String) session.getAttribute("connectionId");
                    studyRoom = (String) session.getAttribute("studyRoom");
                }

                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");
                EnterRoomDTO randomEnterRoomDTO = new EnterRoomDTO(userId,sessionName,videoCodec,connectionId,studyRoom);
                connectionVO = roomService.enterRandomroom(randomEnterRoomDTO);

                session.setAttribute("connectionId", connectionVO.getConnectionId());
                session.setAttribute("studyRoom", connectionVO.getSession());

                return new BaseResponse<>(connectionVO);
            case "changeSession":
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");

                EnterRoomDTO changeSubjectDTO = new EnterRoomDTO(userId,sessionName,videoCodec,connectionId,studyRoom);
                connectionVO = roomService.changeSubject(changeSubjectDTO);

                session.setAttribute("connectionId", connectionVO.getConnectionId());
                session.setAttribute("studyRoom", connectionVO.getSession());

                return new BaseResponse<>(connectionVO);
            case "enterMyroom":
                EnterRoomDTO enterMyRoom = new EnterRoomDTO(userId, userId,"VP8");
                token = roomService.enterMyroom(enterMyRoom);

                if (session != null) {
                    session.setAttribute("token", token);
                }

                return new BaseResponse<>(token);
            case "enterMoccojiroom":
                sessionName = (String) body.get("sessionName");
                videoCodec = (String) body.get("videoCodec");
                EnterRoomDTO moccojiEnterRoomDTO = new EnterRoomDTO(userId,sessionName, videoCodec);
                token = roomService.enterMoccojiroom(moccojiEnterRoomDTO);

                return new BaseResponse<>(token);
            case "askQuestion":
                sessionName = (String) body.get("session");
                String qustionData = (String) body.get("data");
                QuestionDTO questionDto = new QuestionDTO(userId, sessionName, qustionData);
                QuestionVO questionVO = roomService.askQuestion(questionDto);

                return new BaseResponse<>(questionVO);
            case "answerQuestion":
                sessionName = (String) body.get("session");
                String answerData = (String) body.get("data");
                questionId = (String) body.get("questionId");

                AnswerDTO answerDTO = new AnswerDTO(userId,sessionName,answerData,questionId);
                AnswerVO answerVO = roomService.answerQuestion(answerDTO);

                return new BaseResponse<>(answerVO);
            case "findAnswer":
                questionId = (String) body.get("questionId");
                List<AnswerVO> answerVOS = roomService.findAnswerByQuestionId(questionId);

                return new BaseResponse<>(answerVOS);
            case "getSessionQnA":
                if (session != null) {
                    studyRoom = (String) session.getAttribute("studyRoom");
                }

                SessionQnAVO sessionQnAVO = roomService.getSessionQnA(studyRoom);
                return new BaseResponse<>(sessionQnAVO);
            case "getUserQnA":
                UserQnAVO userQnAVO = roomService.getUserQnA(userId);
                return new BaseResponse<>(userQnAVO);
            case "getSessionRanking":
                System.out.println("call getSessionRanking");
                List<StudyRoomVO> studyRoomVOList = roomService.getSessionRanking();
                return new BaseResponse<>(studyRoomVOList);
            case "leaveSession":
                System.out.println("세션을 나갑니다!");
                if (session != null) {
                    connectionId = (String) session.getAttribute("connectionId");
                    studyRoom = (String) session.getAttribute("studyRoom");
                }
                isLeave = (String) body.get("leave");

                EnterRoomDTO enterRoomDTO = new EnterRoomDTO(connectionId, studyRoom);
                roomService.leaveSession(enterRoomDTO);

                if (session != null) {
                    session.setAttribute("connectionId", null);
                    if("leave".equals(isLeave)){
                        session.setAttribute("studyRoom", null);
                    }
                }
                return new BaseResponse<>(SUCCESS);

        }
        throw new BaseException(EMPTY_SIGN);
    }
}
