package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentModifyRequestDTO {
    private long commentId,boardId;
    private String comment;


    @Builder
    public CommentModifyRequestDTO(long commentId, long boardId, String comment) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.comment = comment;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
