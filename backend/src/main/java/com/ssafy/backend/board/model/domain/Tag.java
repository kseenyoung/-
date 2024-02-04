package com.ssafy.backend.board.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column(columnDefinition = "varchar (10)")
    private String tagName;


    @Builder
    public Tag(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
