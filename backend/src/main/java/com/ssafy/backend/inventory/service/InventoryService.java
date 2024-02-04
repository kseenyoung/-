package com.ssafy.backend.inventory.service;

import com.ssafy.backend.inventory.model.vo.InventoryPageVO;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDTO;

public interface InventoryService {
    InventoryPageVO getInventory(String userId, int page, int category);

    void equipItem(InventorySaveRequestDTO dto);

    void unEquipItem(int unEquipItemId,String userId);
}
