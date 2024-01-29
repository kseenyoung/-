package com.ssafy.backend.product.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.service.ProductService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("product")

public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("")
    public BaseResponse<?> store(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws MyException {
        String sign = (String) body.get("sign");
        if(sign == null){
            throw new BaseException(EMPTY_SESSION);
        }

        switch (sign){
            case("getList"):
                List<Product> productList = productService.getList();
                return new BaseResponse<>(productList);
            case("search"):
                Integer categoryId = -1;
                try{
                    categoryId = Integer.parseInt((String) body.get("categoryId"));
                } catch (Exception e){
                    throw new BaseException(WRONG_TYPE);
                }
                List<Product> searchList = productService.searchList(categoryId);
                return new BaseResponse<>(searchList);



        }
        throw new BaseException(EMPTY_SIGN);
    }

}
