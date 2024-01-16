package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.CommentCreateRequestDto;

public interface CommentService {
    public long commentCreate(CommentCreateRequestDto dto,String userId);
}
