package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDTO {

    private long boardId,commentId;

    @Builder
    public CommentDeleteRequestDTO(long boardId, long commentId) {
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
}
