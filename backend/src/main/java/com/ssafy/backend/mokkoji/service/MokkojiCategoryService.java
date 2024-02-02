package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MokkojiCategoryService {
    void addMokkjiCategory(Mokkoji mokkoji, Category category);
    Map<Mokkoji, List<Category>> getMokkojis(Page<Mokkoji> mokkojiList);

    void deleteMokkojiCategory(Mokkoji mokkojiId);

    List<Category> getCategories(Mokkoji mokkoji);
}
