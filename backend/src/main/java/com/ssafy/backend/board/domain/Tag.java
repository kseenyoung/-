package com.ssafy.backend.board.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column(columnDefinition = "varchar (10)")
    private String tagName;

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }


    @Builder
    public Tag(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
