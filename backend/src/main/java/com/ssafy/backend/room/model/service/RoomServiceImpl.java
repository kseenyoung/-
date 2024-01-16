package com.ssafy.backend.room.model.service;

import com.ssafy.backend.room.model.dto.RoomJoinDto;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Random;

@Service
public class RoomServiceImpl implements RoomService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @Override
    public String randomRoomEnter(RoomJoinDto roomJoinDto) throws Exception {
        String sessionName = roomJoinDto.getSessionName();
        Session session;

        // 과목당 3개방(20명씩)
        String[] rooms = new String[]{sessionName+"_1", sessionName+"_2", sessionName+"_3"};

        // 세션에 해당하는 방이 존재하는지 확인
        sessionName = getRandomRoom(sessionName);
        roomJoinDto.setSessionName(sessionName);
        session = openvidu.getActiveSession(sessionName);

        if(session == null){
            // 방이 존재하지 않다면 생성하라
            HashMap<String,String> SessionPropertyJson = roomJoinDto.toSessionPropertyJson();
            SessionProperties properties = SessionProperties.fromJson(SessionPropertyJson).build();
            session = openvidu.createSession(properties);
        } else if (session.getActiveConnections().size() > 20) {
            System.out.println(sessionName+"방에 총 20명이 넘습니다");
        }

        System.out.println("기존에"+sessionName+"방이 있으므로 기존 방에 입장합니다.");
        ConnectionProperties properties = new ConnectionProperties.Builder().build();
        Connection connection = session.createConnection(properties);
        System.out.println("방에 입장할 수 있는 토큰을 발급합니다."+connection.getToken());
        return connection.getToken();
    }

    public String getRandomRoom(String sessionName){
        Random random = new Random();
        int roomNumber = random.nextInt( 3) + 1; // 1-3
        return sessionName+roomNumber;
    }
}
