package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Builder
@RedisHash("question")
public class Question {
    @Id
    String questionId;
    String session;
    String question;
    String userId;

    public Question(String questionId, String session, String question, String userId) {
        this.questionId = questionId;
        this.session = session;
        this.question = question;
        this.userId = userId;
    }

    public Question() {
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
