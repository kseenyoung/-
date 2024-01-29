package com.ssafy.backend.inventory.model.dto;

import com.ssafy.backend.category.model.domain.ProductCategory;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.product.model.domain.Product;

public class InventoryDto {
    private int inventoryId;
    private int isWearing;
    private String productImage;
    private ProductCategory category;
    private String productName;
    private String productDescription;

    public InventoryDto(Inventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.isWearing = inventory.getIsWearing();
        this.productImage = inventory.getProduct().getProductImage();
        this.productName = inventory.getProduct().getProductName();
        this.productDescription = inventory.getProduct().getProductDescription();
        this.category = inventory.getProduct().getProductCategory();
    }
}
