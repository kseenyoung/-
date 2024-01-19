package com.ssafy.backend.mokkoji.model.dto;

import lombok.Getter;
import lombok.Setter;

// DTO 클래스
@Getter
@Setter
public class MokkojiRankDto {
    private Long mokkojiId;
    private String mokkojiName;
    private String leaderId;
    private Long totalMemoryTime;
    private String categories;
    private Long rank;

    // getter, setter

}




