package com.ssafy.backend.category.controller;

import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAllCategoryList")
    public BaseResponse<?> getAllCategoryList(){
        return new BaseResponse<>(categoryService.getAllCategoryList());
    }

}
