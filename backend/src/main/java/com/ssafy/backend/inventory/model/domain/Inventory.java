package com.ssafy.backend.inventory.model.domain;

import com.ssafy.backend.category.model.domain.ProductCategory;
import com.ssafy.backend.product.model.domain.Product;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    private int inventoryId;

    private String userId;

    private int isWearing;

    @ManyToOne()
    @JoinColumn(name = "productId   ")
    private Product product;

    @CreatedDate
    private LocalDateTime createdDate;

}
