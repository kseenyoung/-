package com.ssafy.backend.mokkoji.model.vo;

import com.ssafy.backend.category.model.dto.CategoryDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class MokkojiRankingsVO {
    private List<CategoryDto> categories;
    private MokkojiRankDTO mokkoji;

    public MokkojiRankingsVO(List<CategoryDto> categories, MokkojiRankDTO mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public void setMokkoji(MokkojiRankDTO mokkoji) {
        this.mokkoji = mokkoji;
    }
}
