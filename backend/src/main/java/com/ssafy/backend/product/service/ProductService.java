package com.ssafy.backend.product.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.dto.ProductListResDto;

import java.util.List;


public interface ProductService {
    ProductListResDto getList(int page) throws BaseException;

    List<Product> searchList(int categoryId) throws BaseException;

    void buyProduct(int productId, String userId) throws BaseException;

    void sellProduct(int inventoryId, String userId) throws BaseException;
}
