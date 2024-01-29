package com.ssafy.backend.inventory.service;

import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.InventoryDto;
import com.ssafy.backend.inventory.model.dto.InventoryResponseDto;
import com.ssafy.backend.inventory.model.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    @Override
    public List<Inventory> getInventory(String userId, int page, int category) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        List<Inventory> list = new ArrayList<Inventory>();
        int total_page = 0;
        if(category == 0){
            Page<Inventory> all = inventoryRepository.findAllByUserId(pageable, userId);
            total_page = all.getTotalPages();
            list = all.getContent();
        }
        else{
              Page<Inventory> all = inventoryRepository.findAllByUserIdAndProduct_ProductCategory_ProductCategoryId
                    (pageable, userId, category);
            total_page = all.getTotalPages();
            list = all.getContent();
        }
//        List<InventoryDto> inventories = new ArrayList<InventoryDto>();
//        for (Inventory inventory : list) {
//            inventories.add(new InventoryDto(inventory));
//        }
//
//        return InventoryResponseDto.builder()
//                .userId(userId)
//                .inventories(inventories)
//                .totalPage(total_page).build();
        return list;
    }

}
