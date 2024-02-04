package com.ssafy.backend.category.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList(List<Integer> categoriesId);

    List<Category> getAllCategoryList();

    Page<Mokkoji> getMokkojiList(List<Integer> categories, int page, String keyword);
}
