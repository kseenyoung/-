package com.ssafy.backend.room.model.dto;

import com.google.gson.JsonObject;
import com.ssafy.backend.room.model.domain.Answer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private String session;
    private String data;
    private Integer questionId;

    public Answer toEntity(){
        return Answer.builder()
                .answer(this.data)
                .questionId(this.questionId)
                .build();
    }
}
