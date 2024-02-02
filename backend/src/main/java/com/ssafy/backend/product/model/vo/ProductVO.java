package com.ssafy.backend.product.model.vo;

import com.ssafy.backend.category.model.dto.ProductCategoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductVO {
    public ProductVO() {
    }

    public ProductVO(int productId, String productName, ProductCategoryDto productCategoryDto, int productPrice, String productImage, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productCategoryDto = productCategoryDto;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    private int productId;
    private String productName;
    private ProductCategoryDto productCategoryDto;
    private int productPrice;
    private String productImage;
    private String productDescription;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCategoryDto(ProductCategoryDto productCategoryDto) {
        this.productCategoryDto = productCategoryDto;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
