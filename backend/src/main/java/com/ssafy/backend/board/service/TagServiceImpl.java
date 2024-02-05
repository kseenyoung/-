package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.dto.TagCreateRequestDTO;
import com.ssafy.backend.board.model.repository.TagRepository;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public int addTag(TagCreateRequestDTO dto){
        tagRepository.findById(dto.getTagId()).ifPresent( e-> {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_TAG);
        });
        return tagRepository.save(dto.toEntity()).getBoardTagId();
    }
}
