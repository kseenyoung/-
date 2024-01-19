package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentModifyRequestDto {
    private long commentId,boardId;
    private String comment;


    @Builder

    public CommentModifyRequestDto(long commentId, long boardId, String comment) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.comment = comment;
    }
}
