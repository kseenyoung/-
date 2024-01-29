package com.ssafy.backend.inventory.service;

import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDto;

import java.util.List;

public interface InventoryService {
    List<Inventory> getInventory(String userId, int page, int category);

    void saveInventory(InventorySaveRequestDto dto);
}
