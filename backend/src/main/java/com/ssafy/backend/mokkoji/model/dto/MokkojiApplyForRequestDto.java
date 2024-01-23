package com.ssafy.backend.mokkoji.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MokkojiApplyForRequestDto {
    private String userId;
    private int mokkojiId;

    @Builder
    public MokkojiApplyForRequestDto(String userId, int mokkojiId) {
        this.userId = userId;
        this.mokkojiId = mokkojiId;
    }
}
