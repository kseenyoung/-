package com.ssafy.backend.room.model.dto;


import lombok.Builder;


public class SessionPropertyDto {
    private String customSessionId;
    private String forcedVideoCodec;


    @Builder
    public SessionPropertyDto(String customSessionId, String forcedVideoCodec) {
        this.customSessionId = customSessionId;
        this.forcedVideoCodec = forcedVideoCodec;
    }

    public SessionPropertyDto() {
    }

    public String getCustomSessionId() {
        return customSessionId;
    }

    public void setCustomSessionId(String customSessionId) {
        this.customSessionId = customSessionId;
    }

    public String getForcedVideoCodec() {
        return forcedVideoCodec;
    }

    public void setForcedVideoCodec(String forcedVideoCodec) {
        this.forcedVideoCodec = forcedVideoCodec;
    }
}
