package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.domain.Question;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private String session;
    private String data;

    public Question toEntity(){
        return Question.builder()
                .question(data)
                .build();
    }
}
