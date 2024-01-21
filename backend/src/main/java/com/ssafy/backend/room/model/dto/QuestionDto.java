package com.ssafy.backend.room.model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private String session;
    private String data;


}
