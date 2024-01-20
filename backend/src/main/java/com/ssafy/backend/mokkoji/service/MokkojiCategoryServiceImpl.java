package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import com.ssafy.backend.mokkoji.model.repository.MokkojiCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MokkojiCategoryServiceImpl implements MokkojiCategoryService{
    private final MokkojiCategoryRepository mokkojiCategoryRepository;


    @Override
    public void createMokkjiCategory(Mokkoji mokkoji, Category category) {
        mokkojiCategoryRepository.save(MokkojiCategory.builder()
                .category(category)
                .mokkoji(mokkoji)
                .build());
    }
}
