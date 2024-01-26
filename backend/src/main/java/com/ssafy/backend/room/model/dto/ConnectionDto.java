package com.ssafy.backend.room.model.dto;

import com.ssafy.backend.room.model.domain.Question;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConnectionDto {
    private String session;
    private String token;
}
