package com.ssafy.backend.product.model.dto;

import com.ssafy.backend.category.model.dto.ProductCategoryDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDTO {
    private int productId;
    private String productName;
    private ProductCategoryDTO productCategoryDto;
    private int productPrice;
    private String productImage;
    private String productDescription;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCategoryDto(ProductCategoryDTO productCategoryDto) {
        this.productCategoryDto = productCategoryDto;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
