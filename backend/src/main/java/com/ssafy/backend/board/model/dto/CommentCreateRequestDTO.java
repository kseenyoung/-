package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequestDTO {

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
