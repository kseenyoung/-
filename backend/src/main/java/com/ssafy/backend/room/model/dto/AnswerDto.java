package com.ssafy.backend.room.model.dto;

import com.google.gson.JsonObject;
import com.ssafy.backend.room.model.domain.Answer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Integer answerId;
    private String sessionName;
    private String message;
    private Integer question;

    public String toJsonString(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("session", this.sessionName);
        jsonObject.addProperty("data", this.answerId);
        return jsonObject.toString();
    }

    public AnswerDto(String sessionName, String message, Integer question) {
        this.sessionName = sessionName;
        this.message = message;
        this.question = question;
    }

    public Answer toEntity(){
        return Answer.builder()
                .answer(this.message)
                .questionId(this.question)
                .build();
    }
}
