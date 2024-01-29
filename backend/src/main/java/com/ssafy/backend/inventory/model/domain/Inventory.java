package com.ssafy.backend.inventory.model.domain;

import com.ssafy.backend.category.model.domain.ProductCategory;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Inventory {

    @Id
    private int inventoryId;

    private String userId;

    private int isWearing;

//    @ManyToOne()
//    private Store store;

    @ManyToOne()
    private ProductCategory productCategory;

}
