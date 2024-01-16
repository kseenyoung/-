package com.ssafy.backend.board.dto;

import com.ssafy.backend.board.domain.Board;
import com.ssafy.backend.board.domain.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {
    private long boardId;
    private String userId;
    private Tag tag;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 생성자
    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.userId = board.getUserId();
        this.tag = board.getTagId();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.createdDate = board.getUpdatedDate();
        this.updatedDate = board.getCreatedDate();
    }
}
