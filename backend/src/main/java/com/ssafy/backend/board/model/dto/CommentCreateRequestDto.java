package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequestDto {

    private long boardId;
    private String comment;

    public Comment toEntity(Board board,String userId) {
        return Comment.builder()
                .comment(comment)
                .boardId(board)
                .userId(userId)
                .build();
    }

    @Builder
    public CommentCreateRequestDto(long boardId, String comment) {
        this.boardId = boardId;
        this.comment = comment;
    }
}
