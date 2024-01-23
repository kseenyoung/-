package com.ssafy.backend.user.model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenviduRequestDto {
    private String session;
    private String data;
}
