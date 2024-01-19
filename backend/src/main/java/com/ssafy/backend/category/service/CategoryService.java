package com.ssafy.backend.category.service;

import com.ssafy.backend.category.model.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories(List<Integer> categoriesId);
}
