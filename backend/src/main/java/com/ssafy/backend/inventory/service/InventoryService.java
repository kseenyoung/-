package com.ssafy.backend.inventory.service;

import com.ssafy.backend.inventory.model.domain.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> getInventory(String userId, int page, int category);
}
