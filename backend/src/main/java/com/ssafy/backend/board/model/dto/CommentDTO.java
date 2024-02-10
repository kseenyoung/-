package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.BoardComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDTO {
    private long commentId;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String userId;
    public CommentDTO(BoardComment boardCommentEntity) {
        this.commentId = boardCommentEntity.getCommentId();
        this.comment = boardCommentEntity.getComment();
        this.createdDate = boardCommentEntity.getCreatedDate();
        this.updatedDate = boardCommentEntity.getUpdatedDate();
        this.userId = boardCommentEntity.getUserId();
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
