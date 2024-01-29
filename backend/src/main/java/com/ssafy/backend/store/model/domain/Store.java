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
@RequiredArgsConstructor
//@NoArgsConstructor
@Builder
public class Store {
    @Id
    private int storeId;

    @Column
    private String productName;

//    @Column
    @ManyToOne()
    private ProductCategory productCategory;

    @Column
    private int productPrice;

    @Column(columnDefinition = "varchar(200)")
    private String productImage;







}
