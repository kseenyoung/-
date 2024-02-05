package com.ssafy.backend.inventory.model.domain;

import com.ssafy.backend.product.model.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    private int inventoryId;

    private String userId;

    private int isWearing;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Inventory(String userId, int isWearing, Product product) {
        this.userId = userId;
        this.isWearing = isWearing;
        this.product = product;
    }

    public void changeCloth(){
        if(isWearing == 1){
            this.isWearing =0;
        }else{
            this.isWearing = 1;
        }
    }

    public void resetCloth() {
        this.isWearing = 0;
    }
}
