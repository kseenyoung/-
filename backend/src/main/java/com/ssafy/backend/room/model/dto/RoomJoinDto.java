package com.ssafy.backend.room.model.dto;

public class RoomJoinDto {
    private String sessionName;
    private String videoCodec;

    public RoomJoinDto(String sessionName, String videoCodec) {
        this.sessionName = sessionName;
        this.videoCodec = videoCodec;
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
}
