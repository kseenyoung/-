package com.ssafy.backend.room.service;

import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.domain.Question;
import com.ssafy.backend.room.model.dto.AnswerDTO;
import com.ssafy.backend.room.model.vo.AnswerVO;
import com.ssafy.backend.room.model.vo.ConnectionVO;
import com.ssafy.backend.room.model.dto.QuestionDTO;
import com.ssafy.backend.room.model.dto.EnterRoomDTO;
import com.ssafy.backend.room.model.vo.QuestionVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    String enterMyroom(EnterRoomDTO enterRoomDto) throws Exception;
    ConnectionVO enterRandomroom(EnterRoomDTO enterRoomDto) throws Exception;
    String enterMoccojiroom(EnterRoomDTO enterRoomDto) throws Exception;

    QuestionVO askQuestion(QuestionDTO questionDto) throws Exception;

    AnswerVO answerQuestion(AnswerDTO answerDto) throws Exception;

    Answer saveAnswer(AnswerDTO answerDto) throws Exception;
    Question saveQuestion(QuestionDTO questionDto) throws Exception;
    List<AnswerVO> findAnswerByQuestionId(String questionId) throws Exception;
    void leaveSession(String userId,String token) throws Exception;
}
