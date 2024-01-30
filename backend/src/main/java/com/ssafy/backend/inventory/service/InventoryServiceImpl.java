package com.ssafy.backend.inventory.service;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.InventoryDto;
import com.ssafy.backend.inventory.model.dto.InventoryResponseDto;
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
    public InventoryResponseDto getInventory(String userId, int page, int category) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdDate")));
        Page<Inventory> all;
        if(category == 0){
            all = inventoryRepository.findAllByUserId(pageable, userId);
        } else {
            all = inventoryRepository.findAllByUserIdAndProduct_ProductCategory_ProductCategoryId(pageable, userId, category);
        }

        List<InventoryDto> inventories = all.getContent().stream()
                .map(InventoryDto::new)
                .collect(Collectors.toList());

        return InventoryResponseDto.builder()
                .userId(userId)
                .inventories(inventories)
                .totalPage(all.getTotalPages())
                .build();
    }

    /* 장비 착용을 처리
       클라이언트 요청은 착용한 장비만 보내준다.
       기존 장착된 장비를 초기화하고 사용자가 착용한 장비만 보내서 처리한다.
     */
    @Override
    public void saveInventory(InventorySaveRequestDto dto) {
        //장비들 진짜 있는지 확인
        List<Inventory> inventories = inventoryRepository.findAllByUserId(dto.getUserId());
        //size == 0 이면 아무 장비도 없음
        if(inventories.size() == 0) return;

        //실제 장착할 장비들을 초기화
        inventories.forEach(Inventory::resetCloth);
        //입력받은 착용할 장비들
        List<IsWearingDto> isWearingDto = dto.getIsWearingDto();

        //map으로 중복체크
        Map<Integer, IsWearingDto> wearingDtoMap = isWearingDto.stream()
                .collect(Collectors.toMap(IsWearingDto::getInventoryId, Function.identity(),
                        (existing, replacement) -> {
                            throw new BaseException(DUPLICATE_INVENTORY_ID);
                        }));

        HashSet<Integer> set = new HashSet<>();

        //장비 착용 로직 ->
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
