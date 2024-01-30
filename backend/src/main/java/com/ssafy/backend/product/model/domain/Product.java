package com.ssafy.backend.product.model.domain;

import com.ssafy.backend.category.model.domain.ProductCategory;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column
    private String productName;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne()
    @JoinColumn(name="productCategoryId")
    private ProductCategory productCategory;

    @Column
    private int productPrice;

    @Column(columnDefinition = "varchar(200)")
    private String productImage;

    @Column
    private String productDescription;
}
