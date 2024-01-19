package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MokkojiRankingsResponseDto {
    private List<CategoryDto> categories;
    private MokkojiDto mokkoji;

    public MokkojiRankingsResponseDto(List<CategoryDto> categories, MokkojiDto mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }
}
