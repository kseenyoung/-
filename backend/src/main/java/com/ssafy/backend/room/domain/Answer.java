package com.ssafy.backend.room.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("answer")
public class Answer {
    @Id
    Integer questionId;
    String answer;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    @Builder
    public Answer(Integer questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }
}
