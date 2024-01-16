package com.ssafy.backend.board.controller;

import com.ssafy.backend.board.dto.BoardCreateRequestDto;
import com.ssafy.backend.board.dto.BoardDeleteRequestDto;
import com.ssafy.backend.board.dto.BoardModifyRequestDto;
import com.ssafy.backend.board.dto.TagCreateRequestDto;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.TagService;
import com.ssafy.backend.common.utils.HttpResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/boardDelete")
    public ResponseEntity<HttpResponseBody<?>> boardDelete(@RequestBody BoardDeleteRequestDto dto){
        boardService.delete(dto,"test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "성공"), HttpStatus.OK);
    }

    @PostMapping("/boardModify")
    public ResponseEntity<HttpResponseBody<?>> boardModify(@RequestBody BoardModifyRequestDto dto){
        boardService.update(dto,"test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "성공"), HttpStatus.OK);
    }

    @GetMapping("/getList")
    public ResponseEntity<HttpResponseBody<?>> boardGetList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword)
    {
        boardService.getList(page, keyword);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", boardService.getList(page, keyword)), HttpStatus.OK);
    }
}
