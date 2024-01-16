package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.TagCreateRequestDto;
import com.ssafy.backend.common.exception.MyException;

public interface TagService {
    int tagCreate(TagCreateRequestDto dto) throws MyException;
}
