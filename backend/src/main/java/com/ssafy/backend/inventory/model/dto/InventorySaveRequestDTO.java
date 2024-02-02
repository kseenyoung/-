package com.ssafy.backend.inventory.model.dto;
import java.util.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventorySaveRequestDTO {
    private String userId;
    private List<Integer> itemList;

    public InventorySaveRequestDTO() {
    }

    @Builder
    public InventorySaveRequestDTO(String userId, List<Integer> itemList) {
        this.userId = userId;
        this.itemList = itemList;
    }
}
