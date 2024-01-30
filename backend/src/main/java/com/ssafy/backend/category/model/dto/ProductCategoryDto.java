package com.ssafy.backend.category.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductCategoryDto {
    private int productCategoryId;
    private String productCategoryName;
}
