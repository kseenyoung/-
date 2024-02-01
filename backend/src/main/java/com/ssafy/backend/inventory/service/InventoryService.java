package com.ssafy.backend.inventory.service;

import com.ssafy.backend.inventory.model.dto.InventoryResponseDto;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDto;

public interface InventoryService {
    InventoryResponseDto getInventory(String userId, int page, int category);

    void saveInventory(InventorySaveRequestDto dto);

    void takeOffItem(int takeOffItem,String userId);
}
