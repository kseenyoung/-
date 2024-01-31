package com.ssafy.backend.category.model.domain;

import lombok.Getter;
import com.ssafy.backend.category.model.dto.ProductCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    private int productCategoryId;

    @Column
    private String productCategoryName;

    public ProductCategoryDto toDto(){
        return ProductCategoryDto.builder()
                .productCategoryId(this.productCategoryId)
                .productCategoryName(this.productCategoryName)
                .build();
    }
}
