package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.BoardComment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequestDTO {

    private long boardId;
    private String comment;

    public BoardComment toEntity(Board board, String userId) {
        return BoardComment.builder()
                .comment(comment)
                .boardId(board)
                .userId(userId)
                .build();
    }

    @Builder
    public CommentCreateRequestDTO(long boardId, String comment) {
        this.boardId = boardId;
        this.comment = comment;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
