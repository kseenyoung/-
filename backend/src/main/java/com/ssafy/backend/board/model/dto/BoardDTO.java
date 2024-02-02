package com.ssafy.backend.board.model.dto;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Tag;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardDTO {
    private long boardId;
    private String userId;
    private Tag tag;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 생성자
    public BoardDTO(Board board) {
        this.boardId = board.getBoardId();
        this.userId = board.getUserId();
        this.tag = board.getTagId();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.createdDate = board.getUpdatedDate();
        this.updatedDate = board.getCreatedDate();
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
