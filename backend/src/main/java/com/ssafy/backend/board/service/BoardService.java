package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.dto.*;


public interface BoardService {
    void boardCreate(BoardCreateRequestDto dto, String userId);

    void delete(BoardDeleteRequestDto dto, String userId);

    void update(BoardModifyRequestDto dto, String userId);

    BoardListResponseDto getList(int page, String keyword);

    BoardDetailResponseDto getDetail(long id);
}
