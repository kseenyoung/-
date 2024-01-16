package com.ssafy.backend.board.controller;

import com.ssafy.backend.board.dto.BoardCreateRequestDto;
import com.ssafy.backend.board.dto.TagCreateRequestDto;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.TagService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boardTest")
@RequiredArgsConstructor
public class TestController {

    private final BoardService boardService;
    private final TagService tagService;

    @PostMapping("/tagCreate")
    public ResponseEntity<HttpResponseBody<?>> Tagcreate(@RequestBody  TagCreateRequestDto dto){
        //when 원래 dto 말고 httpSession에서 관리자인지 체크해야됨

        //then
        int idx = tagService.tagCreate(dto);
        HttpResponseBody<String> responseBody = new HttpResponseBody<>("OK",""+idx);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/boardCreate")
    public ResponseEntity<HttpResponseBody<?>> boardCreate(@RequestBody BoardCreateRequestDto dto) {
        boardService.boardCreate(dto,"test1");
        HttpResponseBody<String> responseBody = new HttpResponseBody<>("OK","성공");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
