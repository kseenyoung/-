package com.ssafy.backend.board.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @ManyToOne
    @JoinColumn(name="boardId")
    private Board boardId;

    @Column
    private String userId;

    @Column(columnDefinition = "varchar (20)")
    private String comment;

    public long getCommentId() {
        return commentId;
    }
    public String getUserId() {
        return userId;
    }
    public String getComment() {
        return comment;
    }

    public Board getBoardId() {
        return boardId;
    }
}
