package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Answer;
import lombok.Getter;

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

    public Answer toEntity(){
        return Answer.builder()
                .userId(this.userId)
                .session(this.session)
                .answer(this.data)
                .questionId(this.questionId)
                .build();
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
