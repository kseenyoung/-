package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@Builder
@RedisHash("answer")
public class Answer {
    @Id
    String answerId;

    @Indexed
    String questionId;
    String answer;
    String session;
    String userId;

    public Answer(String answerId, String questionId, String answer, String session, String userId) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answer = answer;
        this.session = session;
        this.userId = userId;
    }

    public Answer() {
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
