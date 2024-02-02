package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import lombok.Getter;

import java.util.HashMap;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Getter
public class EnterRoomDTO {
    private String userId;
    private String sessionName;
    private String videoCodec;
    private String prevConnectionId;
    private String prevSession;

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

    public EnterRoomDTO(String prevConnectionId, String prevSession) {
        this.prevConnectionId = prevConnectionId;
        this.prevSession = prevSession;
    }

    public void setSessionName(String sessionName) {
        if(sessionName == null || sessionName.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else {
            this.sessionName = sessionName;
        }
    }

    public HashMap<String,String> toSessionPropertyJson(){
        HashMap<String,String> SessionPropertyJson = new HashMap<>();
        SessionPropertyJson.put("customSessionId",this.sessionName);
        SessionPropertyJson.put("forcedVideoCodec",this.videoCodec);
        return SessionPropertyJson;
    }
}
