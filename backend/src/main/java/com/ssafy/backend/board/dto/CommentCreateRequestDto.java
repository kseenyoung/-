package com.ssafy.backend.board.dto;

import com.ssafy.backend.board.domain.Board;
import com.ssafy.backend.board.domain.Comment;
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
}
