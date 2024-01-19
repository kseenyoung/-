package com.ssafy.backend.room.model.dto;

import com.google.gson.JsonObject;

public class QuestionDto {
    private String sessionName;
    private String message;

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

    public QuestionDto() {
    }

    public QuestionDto(String sessionName, String message) {
        this.sessionName = sessionName;
        this.message = message;
    }

    public String toJsonString(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("session", this.sessionName);
        jsonObject.addProperty("data", this.message);
        return jsonObject.toString();
    }
}
