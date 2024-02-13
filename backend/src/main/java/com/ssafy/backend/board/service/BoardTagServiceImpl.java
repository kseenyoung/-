package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.BoardTag;
import com.ssafy.backend.board.model.dto.TagCreateRequestDTO;
import com.ssafy.backend.board.model.repository.TagRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardTagServiceImpl implements BoardTagService {
    private final TagRepository tagRepository;

    public BoardTagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public int addTag(TagCreateRequestDTO dto){
        tagRepository.findById(dto.getTagId()).ifPresent( e-> {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_TAG);
        });
        return tagRepository.save(dto.toEntity()).getBoardTagId();
    }

    @Override
    public List<BoardTag> getTagList() {
        return tagRepository.findAll();

    }
}
