package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Question;
import lombok.*;

@Getter
@Builder
public class QuestionDTO {
    private String questionId;
    private String userId;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionId, String userId, String session, String data) {
        this.questionId = questionId;
        this.userId = userId;
        this.session = session;
        this.data = data;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    private String session;
    private String data;

    public QuestionDTO(String userId, String session, String data) {
        this.userId = userId;
        this.session = session;
        this.data = data;
    }

    public Question toEntity(){
        return Question.builder()
                .userId(this.userId)
                .session(this.session)
                .question(this.data)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                " questionId : '" + questionId + '\'' +
                ", userId : '" + userId + '\'' +
                ", session: '" + session + '\'' +
                ", data : '" + data + '\'' +
                '}';
    }
}
