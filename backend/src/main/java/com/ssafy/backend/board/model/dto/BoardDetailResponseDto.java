package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDetailResponseDto {
    BoardDto board;
    List<CommentDto> comments;

    @Builder
    public BoardDetailResponseDto(BoardDto board, List<CommentDto> comments) {
        this.board = board;
        this.comments = comments;
    }


}
