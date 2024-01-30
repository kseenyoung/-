package com.ssafy.backend.inventory.model.dto;

import com.ssafy.backend.category.model.domain.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDto {
    private int productCategoryId;
    private String productCategoryName;

    public ProductCategoryDto(ProductCategory productCategory) {
        this.productCategoryId = productCategory.getProductCategoryId();
        this.productCategoryName = productCategory.getProductCategoryName();
    }
}
