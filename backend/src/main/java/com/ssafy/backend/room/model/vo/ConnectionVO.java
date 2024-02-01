package com.ssafy.backend.room.model.vo;

import lombok.*;

@Getter
@Builder
public class ConnectionVO {
    private String connectionId;
    private String session;
    private String token;

    public ConnectionVO() {
    }

    public ConnectionVO(String connectionId, String session, String token) {
        this.connectionId = connectionId;
        this.session = session;
        this.token = token;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
