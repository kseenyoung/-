package com.ssafy.backend.category.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories(List<Integer> categoriesId) {
        return categoryRepository.findAllById(categoriesId);
    }
}
