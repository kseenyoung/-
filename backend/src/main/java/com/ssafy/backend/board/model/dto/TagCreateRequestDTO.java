package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.BoardTag;

public class TagCreateRequestDTO {

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

    public BoardTag toEntity(){
        return BoardTag.builder().tagId(tagId).tagName(tagName).build();
    }
}
