package com.ssafy.backend.inventory.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsWearingDTO {
    private int inventoryId;
    private int isWearing;

    public IsWearingDTO(){

    }
    public IsWearingDTO(int inventoryId, int isWearing) {
        this.inventoryId = inventoryId;
        this.isWearing = isWearing;
    }
}
