package com.ssafy.backend.board.service;

import com.ssafy.backend.board.dto.TagCreateRequestDto;
import com.ssafy.backend.board.repository.TagRepository;
import com.ssafy.backend.common.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public int tagCreate(TagCreateRequestDto dto){
        tagRepository.findById(dto.getTagId()).ifPresent( e-> {
             throw new MyException("이미 존재하는 태그입니다",HttpStatus.BAD_REQUEST);
        });
        return tagRepository.save(dto.toEntity()).getTagId();
    }
}
