package com.ssafy.backend.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateResponseDto {

    private long CommentId;
    private String comment;
    private long boardId;

    @Builder
    public CommentCreateResponseDto(long commentId, String comment, long boardId) {
        CommentId = commentId;
        this.comment = comment;
        this.boardId = boardId;
    }
}
