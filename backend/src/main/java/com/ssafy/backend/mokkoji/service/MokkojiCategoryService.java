package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;

public interface MokkojiCategoryService {
    void createMokkjiCategory(Mokkoji mokkoji, Category category);
}
