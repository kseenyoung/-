package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MokkojiCategoryDTO {
    private List<Category> categories;
    private Mokkoji mokkoji;


    @Builder
    public MokkojiCategoryDTO(List<Category> categories, Mokkoji mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setMokkoji(Mokkoji mokkoji) {
        this.mokkoji = mokkoji;
    }
}
