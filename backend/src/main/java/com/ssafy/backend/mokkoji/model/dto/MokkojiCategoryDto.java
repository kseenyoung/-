package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MokkojiCategoryDto {
    private List<Category> categories;
    private Mokkoji mokkoji;


    @Builder
    public MokkojiCategoryDto(List<Category> categories, Mokkoji mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }
}
