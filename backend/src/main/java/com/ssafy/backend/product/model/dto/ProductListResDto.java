package com.ssafy.backend.product.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
public class ProductListResDto {
    private int totalPage;
    private List<ProductDto> productList;
}
