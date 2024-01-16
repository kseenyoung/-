package com.ssafy.backend.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDeleteRequestDto {
    private long boardId;

    public BoardDeleteRequestDto(){

    }
    @Builder
    public BoardDeleteRequestDto(long boardId){
        setBoardId(boardId);
    }
}
