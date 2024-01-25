package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Question;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private String userId;
    private String session;
    private String data;

    public Question toEntity(){
        return Question.builder()
                .userId(this.userId)
                .session(this.session)
                .question(this.data)
                .build();
    }
}
