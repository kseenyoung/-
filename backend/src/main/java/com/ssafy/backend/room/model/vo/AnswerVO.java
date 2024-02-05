package com.ssafy.backend.room.model.vo;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Getter
@Builder
public class AnswerVO {
    private String answerId;
    private String userId;
    private String session;
    private String data;
    private String questionId;

    public AnswerVO(String userId, String session, String data, String questionId) {
        this.userId = userId;
        this.session = session;
        this.data = data;
        this.questionId = questionId;
    }

    public AnswerVO() {
    }

    public AnswerVO(String answerId, String userId, String session, String data, String questionId) {
        setAnswerId(answerId);
        setUserId(userId);
        setSession(session);
        setData(data);
        setQuestionId(questionId);
    }

    public AnswerRedis toEntity(){
        return AnswerRedis.builder()
                .userId(this.userId)
                .session(this.session)
                .answerContent(this.data)
                .questionId(this.questionId)
                .build();
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setUserId(String userId) {
        if(userId == null || userId.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else{
            this.userId = userId;
        }
    }

    public void setSession(String session) {
        if(session == null || session.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else{
            this.session = session;
        }
    }

    public void setData(String data) {
        if(data == null || data.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else{
            this.data = data;
        }
    }

    public void setQuestionId(String questionId) {
        if(questionId == null || questionId.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
        } else{
            this.questionId = questionId;
        }
    }
}
