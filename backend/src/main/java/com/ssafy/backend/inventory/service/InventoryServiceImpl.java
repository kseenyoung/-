package com.ssafy.backend.inventory.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.vo.InventoryVO;
import com.ssafy.backend.inventory.model.vo.InventoryPageVO;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDTO;
import com.ssafy.backend.inventory.model.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryPageVO getInventory(String userId, int page, int category) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdDate")));
        Page<Inventory> all;
        if(category == 0){
            all = inventoryRepository.findAllByUserId(pageable, userId);
        } else {
            all = inventoryRepository.findAllByUserIdAndProduct_ProductCategory_ProductCategoryId(pageable, userId, category);
        }

        List<InventoryVO> inventories = all.getContent().stream()
                .map(InventoryVO::new)
                .collect(Collectors.toList());

        return InventoryPageVO.builder()
                .userId(userId)
                .inventories(inventories)
                .totalPage(all.getTotalPages())
                .build();
    }

    /* 장비 착용을 처리
       클라이언트 요청은 착용한 장비만 보내준다.
       기존 장착된 장비를 초기화하고 사용자가 착용한 장비만 보내서 처리한다.
     */
    @SneakyThrows
    @Override
    @Transactional
    public void equipItem(InventorySaveRequestDTO dto) {
        //장비들 진짜 있는지 확인
        List<Inventory> inventories = inventoryRepository.findAllByUserIdAndInventoryIdIn(dto.getUserId(),dto.getItemList());
        //size == 0 이면 아무 장비도 없음
        if(inventories.isEmpty()) throw new BaseException(EMPTY_INVENTORY);

        //입력받은 착용할 장비들
        List<Integer> isWearingDto = dto.getItemList();

        //실제 장착할 장비들을 초기화
        List<Inventory> saveItems = new ArrayList();
        List<Integer> categories = new ArrayList();
        //현재 DB에서 장비 착용 벗겨야하는 애들 saveItems에 추가
        inventories.stream().filter(
                        inventory -> isWearingDto.contains(inventory.getInventoryId()))
                .forEach(inventory ->{
                    categories.add(inventory.getProduct().getProductCategory().getProductCategoryId());
                });
        inventories.stream().filter(inventory -> categories.contains(inventory.getProduct().getProductCategory().getProductCategoryId()))
                .forEach(inventory ->  {
                    inventory.resetCloth();
                    saveItems.add(inventory);
                });
        //입력 중복체크 + 장비 착용할 애들 토글 하기
        HashSet<Integer> checkItems = new HashSet<Integer>();
        inventories.stream().filter(inventory -> isWearingDto.contains(inventory.getInventoryId()) && inventory.getIsWearing() == 0)
                .forEach(inventory ->  {
                    if (checkItems.contains(inventory.getProduct().getProductCategory().getProductCategoryId())) {
                        throw new BaseException(DUPLICATE_INVENTORY_ID);
                    }
                    checkItems.add(inventory.getProduct().getProductCategory().getProductCategoryId());
                    inventory.changeCloth();
                    saveItems.add(inventory);
                });
        inventoryRepository.saveAll(saveItems);
    }

    @Override
    @Transactional
    public void unEquipItem(int unEquipItemId,String userId) {
        Inventory inventory = inventoryRepository.findByUserIdAndInventoryId(userId, unEquipItemId)
                .orElseThrow(() -> {
                    throw new BaseException(NOT_EXIST_INVENTORY);
                });
        inventory.resetCloth();
        inventoryRepository.save(inventory);
    }

}
