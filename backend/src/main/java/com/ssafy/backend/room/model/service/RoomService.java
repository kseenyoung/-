package com.ssafy.backend.room.model.service;

import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;

public interface RoomService {
    String enterRandomroom(RoomEnterDto roomEnterDto) throws Exception;
    String enterMoccojiroom(RoomEnterDto roomEnterDto) throws Exception;

    void askQuestion(QuestionDto questionDto) throws Exception;

    void answerQuestion(AnswerDto answerDto) throws Exception;
}
