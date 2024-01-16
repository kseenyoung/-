package com.ssafy.backend.board.dto;

import com.ssafy.backend.board.domain.Tag;

public class TagCreateRequestDto {

    private int tagId;
    private String tagName;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Tag toEntity(){
        return Tag.builder().tagId(tagId).tagName(tagName).build();
    }
}
