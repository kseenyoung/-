package com.ssafy.backend.room.model.dto;


import lombok.Builder;

public class SessionPropertyDTO {
    private String customSessionId;
    private String forcedVideoCodec;


    @Builder
    public SessionPropertyDTO(String customSessionId, String forcedVideoCodec) {
        this.customSessionId = customSessionId;
        this.forcedVideoCodec = forcedVideoCodec;
    }

    public SessionPropertyDTO() {
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
