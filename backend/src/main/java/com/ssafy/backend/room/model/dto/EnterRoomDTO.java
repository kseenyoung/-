package com.ssafy.backend.room.model.dto;

import java.util.HashMap;

public class EnterRoomDTO {
    private String userId;
    private String sessionName;
    private String videoCodec;
    private String prevConnectionId;
    private String prevSession;

    public String getPrevConnectionId() {
        return prevConnectionId;
    }

    public String getPrevSession() {
        return prevSession;
    }

    public EnterRoomDTO(String userId, String sessionName, String videoCodec) {
        this.userId = userId;
        this.sessionName = sessionName;
        this.videoCodec = videoCodec;
    }

    public EnterRoomDTO(String userId, String sessionName, String videoCodec, String prevConnectionId, String prevSession ) {
        this.userId = userId;
        this.sessionName = sessionName;
        this.videoCodec = videoCodec;
        this.prevConnectionId = prevConnectionId;
        this.prevSession = prevSession;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public HashMap<String,String> toSessionPropertyJson(){
        HashMap<String,String> SessionPropertyJson = new HashMap<>();
        SessionPropertyJson.put("customSessionId",this.sessionName);
        SessionPropertyJson.put("forcedVideoCodec",this.videoCodec);
        return SessionPropertyJson;
    }
}
