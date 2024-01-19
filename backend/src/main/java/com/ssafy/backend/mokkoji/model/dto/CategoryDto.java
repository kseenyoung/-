package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    private String categoryName;

    @Builder
    public CategoryDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
