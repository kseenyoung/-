package com.ssafy.backend.room.service;

import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.domain.Question;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.ConnectionDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    String enterDefaultroom(RoomEnterDto roomEnterDto) throws Exception;
    ConnectionDto enterRandomroom(RoomEnterDto roomEnterDto) throws Exception;
    String enterMoccojiroom(RoomEnterDto roomEnterDto) throws Exception;

    QuestionDto askQuestion(QuestionDto questionDto) throws Exception;

    AnswerDto answerQuestion(AnswerDto answerDto) throws Exception;

    Answer saveAnswer(AnswerDto answerDto) throws Exception;
    Question saveQuestion(QuestionDto questionDto) throws Exception;
    List<AnswerDto> findAnswerByQuestionId(String questionId) throws Exception;


}
