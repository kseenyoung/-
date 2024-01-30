package com.ssafy.backend.inventory.model.dto;

import com.ssafy.backend.inventory.model.domain.Inventory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InventoryResponseDto {
    private String userId;
    private int totalPage;
    List<InventoryDto> inventories = new ArrayList<>();

    @Builder
    public InventoryResponseDto(String userId,int totalPage, List<InventoryDto> inventories) {
        this.userId = userId;
        this.totalPage = totalPage;
        this.inventories = inventories;
    }
}
