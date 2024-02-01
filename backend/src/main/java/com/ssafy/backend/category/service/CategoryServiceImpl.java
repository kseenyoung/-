package com.ssafy.backend.category.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.repository.CategoryRepository;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories(List<Integer> categoriesId) {
        return categoryRepository.findAllById(categoriesId);
    }

    @Override
    public List<Category> getCategoryList(){
        return categoryRepository.findAll();
    }

    @Override
    public Page<Mokkoji> getMokkojiList(List<Integer> categories, int page, String keyword) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        return categoryRepository.findByCategoryIds(categories, keyword,pageable);

    }
}
