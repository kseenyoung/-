package com.ssafy.backend.inventory.service;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDto;
import com.ssafy.backend.inventory.model.dto.IsWearingDto;
import com.ssafy.backend.inventory.model.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ssafy.backend.common.response.BaseResponseStatus.DUPLICATE_INVENTORY_ID;
import static com.ssafy.backend.common.response.BaseResponseStatus.TWO_UP_PUT_ON_CLOTH;

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

    @Override
    public void saveInventory(InventorySaveRequestDto dto) {
        List<Inventory> inventories = inventoryRepository.findAllByUserId(dto.getUserId());
        if(inventories.size() == 0) return;

        List<IsWearingDto> isWearingDto = dto.getIsWearingDto();
        Map<Integer, IsWearingDto> wearingDtoMap = isWearingDto.stream()
                .collect(Collectors.toMap(IsWearingDto::getInventoryId, Function.identity(),
                        (existing, replacement) -> {
                            throw new BaseException(DUPLICATE_INVENTORY_ID);
                        }));

        HashSet<Integer> set = new HashSet<>();
        List<Inventory> list = inventories.stream()
                .filter(inventory -> wearingDtoMap.containsKey(inventory.getInventoryId()))
                .peek(inventory -> {
                    IsWearingDto wearingDto = wearingDtoMap.get(inventory.getInventoryId());
                    if (wearingDto.getIsWearing() == 1) {
                        int categoryId = inventory.getProduct().getProductCategory().getProductCategoryId();
                        if (set.contains(categoryId)) {
                            throw new BaseException(TWO_UP_PUT_ON_CLOTH);
                        }
                        set.add(categoryId);
                    }
                    inventory.changeCloth();
                })
                .collect(Collectors.toList());

        inventoryRepository.saveAll(list);
    }

}
