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
}
