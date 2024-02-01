package com.ssafy.backend.mokkoji.model.vo;

import com.ssafy.backend.category.model.dto.CategoryDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankDto;
import lombok.Getter;

import java.util.List;

@Getter
public class MokkojiRankingsVO {
    private List<CategoryDto> categories;
    private MokkojiRankDto mokkoji;

    public MokkojiRankingsVO(List<CategoryDto> categories, MokkojiRankDto mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public void setMokkoji(MokkojiRankDto mokkoji) {
        this.mokkoji = mokkoji;
    }
}
