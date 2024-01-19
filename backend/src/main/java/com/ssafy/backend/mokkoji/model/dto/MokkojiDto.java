package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MokkojiDto {
    private int mokkojiId;

    private String mokkojiName;

    private String leaderId;
    private int rank;
    public MokkojiDto(MokkojiRankings entity) {
        this.mokkojiId = entity.getMokkojiId();
        this.mokkojiName = entity.getMokkojiName();
        this.leaderId = entity.getLeaderId();
        this.rank = entity.getRank();
    }
}
