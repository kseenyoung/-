package com.ssafy.backend.product.model.dto;

import com.ssafy.backend.category.model.dto.ProductCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDto {
    private int productId;
    private String productName;
    private ProductCategoryDto productCategoryDto;
    private int productPrice;
    private String productImage;
    private String productDescription;
}
