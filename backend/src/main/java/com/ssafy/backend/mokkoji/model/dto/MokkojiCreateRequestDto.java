package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MokkojiCreateRequestDto {
    private String mokkojiName;
    private String leaderId;
    private List<Integer> mokkojiCategories = new ArrayList<Integer>();
    private String mokkojiStatus;
    public Mokkoji toEntity(){
        return Mokkoji.builder()
                .leaderId(leaderId)
                .mokkojiName(mokkojiName)
                .build();
    }

    @Builder

    public MokkojiCreateRequestDto(String mokkojiName, String leaderId, List<Integer> mokkojiCategories, String mokkojiStatus) {
        this.mokkojiName = mokkojiName;
        this.leaderId = leaderId;
        this.mokkojiCategories = mokkojiCategories;
        this.mokkojiStatus = mokkojiStatus;
    }

    public void setMokkojiName(String mokkojiName) {
        this.mokkojiName = mokkojiName;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public void setMokkojiCategories(List<Integer> mokkojiCategories) {
        this.mokkojiCategories = mokkojiCategories;
    }

    public void setMokkojiStatus(String mokkojiStatus) {
        this.mokkojiStatus = mokkojiStatus;
    }
}
