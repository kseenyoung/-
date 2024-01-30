package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Question;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private String questionId;
    private String userId;
    private String session;
    private String data;

    public QuestionDto(String userId, String session, String data) {
        this.userId = userId;
        this.session = session;
        this.data = data;
    }

    public Question toEntity(){
        return Question.builder()
                .userId(this.userId)
                .session(this.session)
                .question(this.data)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                " questionId : '" + questionId + '\'' +
                ", userId : '" + userId + '\'' +
                ", session: '" + session + '\'' +
                ", data : '" + data + '\'' +
                '}';
    }
}
