package com.ssafy.backend.inventory.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsWearingDto {
    private int inventoryId;
    private int isWearing;

    public IsWearingDto(){

    }
    public IsWearingDto(int inventoryId, int isWearing) {
        this.inventoryId = inventoryId;
        this.isWearing = isWearing;
    }
}
