package com.ssafy.backend.room.service;

import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    String enterRandomroom(RoomEnterDto roomEnterDto) throws Exception;
    String enterMoccojiroom(RoomEnterDto roomEnterDto) throws Exception;

    void askQuestion(QuestionDto questionDto) throws Exception;

    void answerQuestion(AnswerDto answerDto) throws Exception;

    Answer saveAnswer(AnswerDto answerDto) throws Exception;
    List<AnswerDto> findAnswerByQuestionId(int questionId) throws Exception;
}
