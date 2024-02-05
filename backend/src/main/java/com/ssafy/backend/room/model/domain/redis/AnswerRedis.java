package com.ssafy.backend.room.model.domain.redis;

import com.ssafy.backend.room.model.domain.Answer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@Builder
@RedisHash("answer")
public class AnswerRedis {
    @Id
    String answerId;

    @Indexed
    String questionId;
    String answerContent;
    String session;
    String userId;

    public AnswerRedis(String answerId, String questionId, String answerContent, String session, String userId) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerContent = answerContent;
        this.session = session;
        this.userId = userId;
    }

    public AnswerRedis() {
    }

    public Answer toEntity(){
        return Answer.builder()
                .answerId(this.answerId)
                .answerContent(this.answerContent)
                .session(this.session)
                .questionId(this.questionId)
                .userId(this.userId)
                .build();
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
