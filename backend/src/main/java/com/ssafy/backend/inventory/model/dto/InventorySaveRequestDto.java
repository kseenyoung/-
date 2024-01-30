package com.ssafy.backend.inventory.model.dto;
import java.util.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventorySaveRequestDto {
    private String userId;
    private List<Integer> itemList;

    public InventorySaveRequestDto() {
    }

    @Builder
    public InventorySaveRequestDto(String userId, List<Integer> itemList) {
        this.userId = userId;
        this.itemList = itemList;
    }
}
