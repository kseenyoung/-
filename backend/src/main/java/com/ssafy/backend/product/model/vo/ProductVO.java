package com.ssafy.backend.product.model.vo;

import com.ssafy.backend.category.model.dto.ProductCategoryDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductVO {

    public ProductVO(int productId, String productName, ProductCategoryDTO productCategoryDto, int productPrice, String productImage, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productCategoryDto = productCategoryDto;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    private int productId;
    private String productName;
    private ProductCategoryDTO productCategoryDto;
    private int productPrice;
    private String productImage;
    private String productDescription;
}
