package com.ssafy.backend.category.model.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class ProductCategory {
    @Id
    private int productCategoryId;

    @Column
    private String productCategoryName;
}
