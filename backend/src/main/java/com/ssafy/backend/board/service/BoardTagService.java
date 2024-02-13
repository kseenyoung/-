package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.BoardTag;
import com.ssafy.backend.board.model.dto.TagCreateRequestDTO;
import com.ssafy.backend.common.exception.MyException;

import java.util.List;

public interface BoardTagService {
    int addTag(TagCreateRequestDTO dto) throws MyException;

    List<BoardTag> getTagList();
}
