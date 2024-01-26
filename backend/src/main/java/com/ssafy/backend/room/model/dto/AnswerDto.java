package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private String userId;
    private String session;
    private String data;
    private String questionId;


    public Answer toEntity(){
        return Answer.builder()
                .userId(this.userId)
                .session(this.session)
                .answer(this.data)
                .questionId(this.questionId)
                .build();
    }
}
