package com.ssafy.backend.store.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.store.model.domain.Product;
import com.ssafy.backend.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("product")

public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("")
    public BaseResponse<?> store(@RequestBody Map<String, String> body, HttpServletRequest httpServletRequest) throws MyException {
        String sign = body.get("sign");
        if(sign == null){
            throw new BaseException(EMPTY_SESSION);
        }

        switch (sign){
            case("getList"):
                List<Product> productList = productService.getList();
                return new BaseResponse<>(productList);
        }
        throw new BaseException(EMPTY_SIGN);
    }

}
