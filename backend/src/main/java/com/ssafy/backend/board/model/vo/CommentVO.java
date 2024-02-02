package com.ssafy.backend.board.model.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentVO {

    private long CommentId;
    private String comment;
    private long boardId;

    @Builder
    public CommentVO(long commentId, String comment, long boardId) {
        CommentId = commentId;
        this.comment = comment;
        this.boardId = boardId;
    }

    public void setCommentId(long commentId) {
        CommentId = commentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
