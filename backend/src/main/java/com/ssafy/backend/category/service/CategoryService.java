package com.ssafy.backend.category.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories(List<Integer> categoriesId);

    List<Category> getCategoryList();

    Page<Mokkoji> getMokkojiList(List<Integer> categories, int page, String keyword);
}
