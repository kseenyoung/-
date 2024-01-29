package com.ssafy.backend.product.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.repository.ProductRepository;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Product> getList() throws MyException {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchList(int categoryId) throws MyException {
        return productRepository.findByProductCategory_ProductCategoryId(categoryId);
    }

    @Override
    public void buyProduct(int productId, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMBER);
        }

        int userPoint = user.getUserPoint();


    }
}
