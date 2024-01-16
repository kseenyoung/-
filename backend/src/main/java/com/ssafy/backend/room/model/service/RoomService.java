package com.ssafy.backend.room.model.service;

import com.ssafy.backend.room.model.dto.RoomJoinDto;

public interface RoomService {
    String randomRoomEnter(RoomJoinDto roomJoinDto) throws Exception;
}
