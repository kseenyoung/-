package com.ssafy.backend.product.model.vo;

import com.ssafy.backend.product.model.dto.ProductDTO;
import lombok.*;

import java.util.List;


@Getter
@Builder
public class ProductListVO {
    private int totalPage;
    private List<ProductDTO> productList;
}
