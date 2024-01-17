package com.ssafy.backend.room.model.dto;

import com.google.gson.JsonObject;
import com.ssafy.backend.room.domain.Answer;

public class AnswerDto {
    private String sessionName;
    private String message;

    private Integer question;

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getMessage() {
        return message;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getQuestion() {
        return question;
    }

    public AnswerDto() {
    }

    public AnswerDto(String sessionName, String message, Integer question) {
        this.sessionName = sessionName;
        this.message = message;
        this.question = question;
    }

    public String toJsonString(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("session", this.sessionName);
        jsonObject.addProperty("data", this.message);
        return jsonObject.toString();
    }

    public Answer toEntity(){
        return Answer.builder()
                .answer(this.message)
                .questionId(this.question)
                .build();
    }
}
