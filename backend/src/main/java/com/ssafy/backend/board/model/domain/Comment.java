package com.ssafy.backend.board.model.domain;

import com.ssafy.backend.common.model.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @ManyToOne
    @JoinColumn(name="boardId")
    private Board boardId;

    @Column
    private String userId;

    @Column
    private String comment;


    @Builder
    public Comment(Board boardId, String userId, String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }

    public void modifyComment(String comment){
        this.comment = comment;
    }

}
