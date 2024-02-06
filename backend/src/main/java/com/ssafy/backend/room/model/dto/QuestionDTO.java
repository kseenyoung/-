package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import lombok.*;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Getter
public class QuestionDTO {
    private String questionId;
    private String userId;
    private String session;
    private String data;

    public void setUserId(String userId) {
        if(userId == null || userId.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else {
            this.userId = userId;
        }

    }

    public void setSession(String session) {
        if(session == null || session.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else {
            this.session = session;
        }
    }

    public void setData(String data) {
        if(data == null){
            throw new BaseException(FAIL_TO_CONNECT);
        } else {
            this.data = data;
        }
    }

    public QuestionDTO(String userId, String session, String data) {
        setUserId(userId);
        setSession(session);
        setData(data);
    }

    public QuestionRedis toEntity(){
        return QuestionRedis.builder()
                .userId(this.userId)
                .session(this.session)
                .questionContent(this.data)
                .build();
    }
}
