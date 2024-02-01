package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDeleteRequestDTO {
    private long boardId;

    public BoardDeleteRequestDTO(){

    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @Builder
    public BoardDeleteRequestDTO(long boardId){
        setBoardId(boardId);
    }
}
