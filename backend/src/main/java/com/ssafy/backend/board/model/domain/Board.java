package com.ssafy.backend.board.model.domain;

import com.ssafy.backend.common.model.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(columnDefinition = "varchar (20)")
    private String userId;

    @ManyToOne()
    @JoinColumn(name="tagId")
    private Tag tagId;

    @Column(columnDefinition = "char (40)")
    private String boardTitle;

    @Column(columnDefinition = "char (255)")
    private String boardContent;


    @Builder
    public Board(String userId, Tag tagId, String boardTitle, String boardContent) {
        this.userId = userId;
        this.tagId = tagId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void updateBoard(Tag tagId, String boardTitle, String boardContent) {
        this.tagId = tagId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
