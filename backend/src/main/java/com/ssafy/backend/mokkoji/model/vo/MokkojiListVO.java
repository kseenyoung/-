package com.ssafy.backend.mokkoji.model.vo;

import com.ssafy.backend.mokkoji.model.dto.MokkojiCategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MokkojiListVO {
    private List<MokkojiCategoryDto> list;
    private int totalPages;

    @Builder
    public MokkojiListVO(List<MokkojiCategoryDto> list, int totalPages) {
        this.list = list;
        this.totalPages = totalPages;
    }

    public void setList(List<MokkojiCategoryDto> list) {
        this.list = list;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
