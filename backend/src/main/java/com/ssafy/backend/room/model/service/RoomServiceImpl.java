package com.ssafy.backend.room.model.service;

import com.ssafy.backend.room.model.dto.RoomJoinDto;
import com.ssafy.backend.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public String randomRoomEnter(RoomJoinDto roomJoinDto) throws Exception {
        return null;
    }
}
