package com.ssafy.backend.inventory.controller;


import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.InventoryResponseDto;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDto;
import com.ssafy.backend.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory_test")
@RequiredArgsConstructor
public class InventoryTestController {
    private final InventoryService inventoryService;
    @GetMapping("/get")
    private BaseResponse<?> getInventory(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "category", defaultValue = "0") int category) {
        InventoryResponseDto inventory = inventoryService.getInventory(userId, page, category);

        return new BaseResponse<>(inventory);
    }

    @PostMapping("/save")
    private BaseResponse<?> saveInventory(@RequestBody InventorySaveRequestDto dto){
        inventoryService.saveInventory(dto);
        return new BaseResponse<>("저장 완료");
    }
}
