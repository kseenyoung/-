package com.ssafy.backend.store.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.store.model.domain.Product;

import java.util.List;


public interface ProductService {
    List<Product> getList() throws MyException;

}
