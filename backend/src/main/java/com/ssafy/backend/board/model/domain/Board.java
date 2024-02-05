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
    private BoardTag boardTagId;

    @Column(columnDefinition = "char (40)")
    private String boardTitle;

    @Column(columnDefinition = "char (255)")
    private String boardContent;


    @Builder
    public Board(String userId, BoardTag boardTagId, String boardTitle, String boardContent) {
        this.userId = userId;
        this.boardTagId = boardTagId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void updateBoard(BoardTag boardTagId, String boardTitle, String boardContent) {
        this.boardTagId = boardTagId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
