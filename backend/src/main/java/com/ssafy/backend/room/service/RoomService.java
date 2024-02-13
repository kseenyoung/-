package com.ssafy.backend.room.service;

import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import com.ssafy.backend.room.model.dto.AnswerDTO;
import com.ssafy.backend.room.model.vo.*;
import com.ssafy.backend.room.model.dto.QuestionDTO;
import com.ssafy.backend.room.model.dto.EnterRoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    String enterMyroom(EnterRoomDTO enterRoomDto) throws Exception;
    ConnectionVO enterRandomroom(EnterRoomDTO enterRoomDto) throws Exception;
    String enterMoccojiroom(EnterRoomDTO enterRoomDto) throws Exception;

    QuestionVO askQuestion(QuestionDTO questionDto) throws Exception;

    AnswerVO answerQuestion(AnswerDTO answerDto) throws Exception;

    AnswerRedis saveAnswer(AnswerDTO answerDto) throws Exception;
    QuestionRedis saveQuestion(QuestionDTO questionDto) throws Exception;
    List<AnswerVO> findAnswerByQuestionId(String questionId) throws Exception;
    void leaveSession(EnterRoomDTO enterRoomDTO) throws Exception;

    ConnectionVO changeSubject(EnterRoomDTO changeSubjectDTO) throws Exception;

    SessionQnAVO getSessionQnA(String studyRoom) throws Exception;

    UserQnAVO getUserQnA(String userId) throws Exception;

    List<StudyRoomVO> getSessionRanking() throws Exception;
}
