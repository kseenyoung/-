package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.BoardCreateRequestDto;
import com.ssafy.backend.board.dto.BoardDeleteRequestDto;
import com.ssafy.backend.board.dto.BoardModifyRequestDto;

public interface BoardService {
    void boardCreate(BoardCreateRequestDto dto,String userId);

    void delete(BoardDeleteRequestDto dto, String userId);

    void update(BoardModifyRequestDto dto, String userId);
}
