package com.ssafy.backend.inventory.model.dto;
import java.util.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventorySaveRequestDto {
    private String userId;
    private List<IsWearingDto> isWearingDto;

    public InventorySaveRequestDto() {
    }

    @Builder
    public InventorySaveRequestDto(String userId, List<IsWearingDto> isWearingDto) {
        this.userId = userId;
        this.isWearingDto = isWearingDto;
    }
}
