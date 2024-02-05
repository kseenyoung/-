package com.ssafy.backend.board.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BoardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardTagId;

    @Column(columnDefinition = "varchar (10)")
    private String boardTagName;


    @Builder
    public BoardTag(int tagId, String tagName) {
        this.boardTagId = tagId;
        this.boardTagName = tagName;
    }
}
