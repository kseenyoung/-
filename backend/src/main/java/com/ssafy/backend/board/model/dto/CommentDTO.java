package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDTO {
    private long commentId;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String userId;
    public CommentDTO(Comment commentEntity) {
        this.commentId = commentEntity.getCommentId();
        this.comment = commentEntity.getComment();
        this.createdDate = commentEntity.getCreatedDate();
        this.updatedDate = commentEntity.getUpdatedDate();
        this.userId = commentEntity.getUserId();
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
