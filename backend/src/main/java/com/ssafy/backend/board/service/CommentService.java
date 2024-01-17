package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.CommentCreateRequestDto;
import com.ssafy.backend.board.dto.CommentDeleteRequestDto;
import com.ssafy.backend.board.dto.CommentModifyRequestDto;

public interface CommentService {
    public long commentCreate(CommentCreateRequestDto dto,String userId);

    long modify(CommentModifyRequestDto dto, String userId);
    void delete(CommentDeleteRequestDto dto, String userId);
}
