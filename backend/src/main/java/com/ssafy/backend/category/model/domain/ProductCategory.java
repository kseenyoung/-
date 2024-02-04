package com.ssafy.backend.category.model.domain;

import lombok.Getter;
import com.ssafy.backend.category.model.dto.ProductCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public ProductCategoryDTO toDto(){
        return ProductCategoryDTO.builder()
                .productCategoryId(this.productCategoryId)
                .productCategoryName(this.productCategoryName)
                .build();
    }
}
