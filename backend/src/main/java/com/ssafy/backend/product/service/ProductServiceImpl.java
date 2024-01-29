package com.ssafy.backend.product.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getList() throws MyException {
        return productRepository.findAll();
    }
}
