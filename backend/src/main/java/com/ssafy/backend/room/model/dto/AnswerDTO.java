package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.room.model.domain.Answer;
import lombok.Getter;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Getter
public class AnswerDTO {
    private String answerId;
    private String userId;
    private String session;
    private String data;
    private String questionId;

    public AnswerDTO(String userId, String session, String data, String questionId) {
        this.userId = userId;
        this.session = session;
        this.data = data;
        this.questionId = questionId;
    }

    public AnswerDTO() {
    }

    public AnswerDTO(String answerId, String userId, String session, String data, String questionId) {
        this.answerId = answerId;
        this.userId = userId;
        this.session = session;
        this.data = data;
        this.questionId = questionId;
    }

    public void setAnswerId(String answerId) {
        if(answerId == null || answerId.isEmpty()){
            throw new BaseException(FAIL_TO_CONNECT);
         }else{
            this.answerId = answerId;
        }
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
        if(data == null){
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

    public Answer toEntity(){
        return Answer.builder()
                .userId(this.userId)
                .session(this.session)
                .answer(this.data)
                .questionId(this.questionId)
                .build();
    }
}
