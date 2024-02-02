package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.dto.TagCreateRequestDTO;
import com.ssafy.backend.common.exception.MyException;

public interface TagService {
    int addTag(TagCreateRequestDTO dto) throws MyException;
}
