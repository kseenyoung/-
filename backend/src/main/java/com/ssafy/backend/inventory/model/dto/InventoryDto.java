package com.ssafy.backend.inventory.model.dto;

import com.ssafy.backend.category.model.domain.ProductCategory;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.product.model.domain.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private int inventoryId;
    private int isWearing;
    private String productImage;
    private ProductCategoryDto category;
    private String productName;
    private String productDescription;

    public InventoryDto(Inventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.isWearing = inventory.getIsWearing();
        this.productImage = inventory.getProduct().getProductImage();
        this.productName = inventory.getProduct().getProductName();
        this.productDescription = inventory.getProduct().getProductDescription();
        this.category = new ProductCategoryDto(inventory.getProduct().getProductCategory());

    }
}
