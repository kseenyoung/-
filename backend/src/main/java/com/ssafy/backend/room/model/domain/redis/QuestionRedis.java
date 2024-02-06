package com.ssafy.backend.room.model.domain.redis;

import com.ssafy.backend.room.model.domain.Question;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Builder
@RedisHash("question")
public class QuestionRedis {
    @Id
    String questionId;
    String userId;
    String session;
    String questionContent;


    public QuestionRedis(String questionId, String session, String questionContent, String userId) {
        this.questionId = questionId;
        this.session = session;
        this.questionContent = questionContent;
        this.userId = userId;
    }

    public QuestionRedis() {
    }

    public Question toEntity(){
        return Question.builder()
                .questionId(this.questionId)
                .questionContent(this.questionContent)
                .session(this.session)
                .build();
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
