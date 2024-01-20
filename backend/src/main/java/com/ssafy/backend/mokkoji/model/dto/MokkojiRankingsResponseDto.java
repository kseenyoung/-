package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.category.model.dto.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MokkojiRankingsResponseDto {
    private List<CategoryDto> categories;
    private MokkojiRankDto mokkoji;

    public MokkojiRankingsResponseDto(List<CategoryDto> categories, MokkojiRankDto mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }
}
