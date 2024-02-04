package com.ssafy.backend.mokkoji.model.vo;

import com.ssafy.backend.category.model.dto.CategoryDTO;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class MokkojiRankingsVO {
    private List<CategoryDTO> categories;
    private MokkojiRankDTO mokkoji;

    public MokkojiRankingsVO(List<CategoryDTO> categories, MokkojiRankDTO mokkoji) {
        this.categories = categories;
        this.mokkoji = mokkoji;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public void setMokkoji(MokkojiRankDTO mokkoji) {
        this.mokkoji = mokkoji;
    }
}
