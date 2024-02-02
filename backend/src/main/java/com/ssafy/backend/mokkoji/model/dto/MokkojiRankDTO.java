package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import lombok.Getter;

@Getter
public class MokkojiRankDTO {
    private int mokkojiId;

    private String mokkojiName;

    private String leaderId;
    private int rank;
    public MokkojiRankDTO(MokkojiRankings entity) {
        this.mokkojiId = entity.getMokkojiId();
        this.mokkojiName = entity.getMokkojiName();
        this.leaderId = entity.getLeaderId();
        this.rank = entity.getRank();
    }

    public void setMokkojiId(int mokkojiId) {
        this.mokkojiId = mokkojiId;
    }

    public void setMokkojiName(String mokkojiName) {
        this.mokkojiName = mokkojiName;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
