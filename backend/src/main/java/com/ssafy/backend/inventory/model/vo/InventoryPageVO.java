package com.ssafy.backend.inventory.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InventoryPageVO {
    private String userId;
    private int totalPage;
    List<InventoryVO> inventories = new ArrayList<>();

    @Builder
    public InventoryPageVO(String userId, int totalPage, List<InventoryVO> inventories) {
        this.userId = userId;
        this.totalPage = totalPage;
        this.inventories = inventories;
    }
}
