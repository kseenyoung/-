package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDeleteRequestDto {

    private long boardId,commentId;

    @Builder
    public CommentDeleteRequestDto(long boardId, long commentId) {
        this.boardId = boardId;
        this.commentId = commentId;
    }
}
