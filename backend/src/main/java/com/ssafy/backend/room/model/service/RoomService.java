package com.ssafy.backend.room.model.service;

import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;

public interface RoomService {
    String randomRoomEnter(RoomEnterDto roomEnterDto) throws Exception;
    String moccojiRoomEnter(RoomEnterDto roomEnterDto) throws Exception;

    void questionAsk(QuestionDto questionDto) throws Exception;

}
