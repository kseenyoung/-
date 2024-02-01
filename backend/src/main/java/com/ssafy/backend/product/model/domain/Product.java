package com.ssafy.backend.product.model.domain;

import com.ssafy.backend.category.model.domain.ProductCategory;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column
    private String productName;

    @ManyToOne()
    @JoinColumn(name="productCategoryId")
    private ProductCategory productCategory;

    @Column
    private int productPrice;

    @Column(columnDefinition = "varchar(200)")
    private String productImage;

    @Column
    private String productDescription;

    public Product(int productId, String productName, ProductCategory productCategory, int productPrice, String productImage, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    public Product() {
    }
}
