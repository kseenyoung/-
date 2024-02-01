package com.ssafy.backend.inventory.model.vo;

import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.dto.ProductCategoryDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryVO {
    private int inventoryId;
    private int isWearing;
    private String productImage;
    private ProductCategoryDTO category;
    private String productName;
    private String productDescription;

    public InventoryVO(Inventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.isWearing = inventory.getIsWearing();
        this.productImage = inventory.getProduct().getProductImage();
        this.productName = inventory.getProduct().getProductName();
        this.productDescription = inventory.getProduct().getProductDescription();
        this.category = new ProductCategoryDTO(inventory.getProduct().getProductCategory());

    }
}
