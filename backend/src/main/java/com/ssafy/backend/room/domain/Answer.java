package com.ssafy.backend.room.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;

@RedisHash("answer")
public class Answer {


    String sessionName;
    Integer questionId;
    String answer;

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }


    @Builder
    public Answer(String sessionName, Integer questionId, String answer) {
        this.sessionName = sessionName;
        this.questionId = questionId;
        this.answer = answer;
    }
}
