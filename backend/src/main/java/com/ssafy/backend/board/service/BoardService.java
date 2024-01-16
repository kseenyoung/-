package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.BoardCreateRequestDto;

public interface BoardService {
    void boardCreate(BoardCreateRequestDto dto,String userId);

}
