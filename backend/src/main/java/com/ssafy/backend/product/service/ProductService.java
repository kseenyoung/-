package com.ssafy.backend.product.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.product.model.domain.Product;

import java.util.List;


public interface ProductService {
    List<Product> getList() throws MyException;

}
