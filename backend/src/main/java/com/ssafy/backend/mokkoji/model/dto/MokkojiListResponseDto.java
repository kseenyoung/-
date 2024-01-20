package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MokkojiListResponseDto {
    private List<MokkojiCategoryDto> list;
    private int totalPages;

    @Builder
    public MokkojiListResponseDto(List<MokkojiCategoryDto> list, int totalPages) {
        this.list = list;
        this.totalPages = totalPages;
    }
}
