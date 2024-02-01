package com.ssafy.backend.mokkoji.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MokkojiApplyForRequestDto {
    private String userId;
    private int mokkojiId;

    @Builder
    public MokkojiApplyForRequestDto(String userId, int mokkojiId) {
        this.userId = userId;
        this.mokkojiId = mokkojiId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMokkojiId(int mokkojiId) {
        this.mokkojiId = mokkojiId;
    }
}
