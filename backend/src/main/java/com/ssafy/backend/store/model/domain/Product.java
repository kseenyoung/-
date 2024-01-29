package com.ssafy.backend.store.model.domain;

import com.ssafy.backend.category.model.domain.ProductCategory;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private int productId;

    @Column
    private String productName;

    @ManyToOne()
    private ProductCategory productCategory;

    @Column
    private int productPrice;

    @Column(columnDefinition = "varchar(200)")
    private String productImage;

    @Column
    private String productDescription;
}
