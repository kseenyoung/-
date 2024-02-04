package com.ssafy.backend.category.model.dto;

import com.ssafy.backend.category.model.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;

    @Builder
    public CategoryDTO(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
