package com.ssafy.backend.board.dto;

import com.ssafy.backend.board.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private long commentId;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String userId;
    public CommentDto(Comment commentEntity) {
        this.commentId = commentEntity.getCommentId();
        this.comment = commentEntity.getComment();
        this.createdDate = commentEntity.getCreatedDate();
        this.updatedDate = commentEntity.getUpdatedDate();
        this.userId = commentEntity.getUserId();
    }
}
