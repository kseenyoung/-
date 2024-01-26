package com.ssafy.backend.board.controller;


import com.ssafy.backend.board.model.dto.*;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.CommentService;
import com.ssafy.backend.board.service.TagService;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.common.utils.HttpResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.backend.common.response.BaseResponseStatus.SUCCESS_TAG_CREATE;

@RestController
@RequestMapping("/boardTest")
@RequiredArgsConstructor
public class TestController {

    private final BoardService boardService;
    private final TagService tagService;
    private final CommentService commentService;

    @PostMapping("/tagCreate")
    public BaseResponse<?> Tagcreate(@RequestBody TagCreateRequestDto dto){
        //when 원래 dto 말고 httpSession에서 관리자인지 체크해야됨
        //then
        int idx = tagService.tagCreate(dto);
        return new BaseResponse<>(SUCCESS_TAG_CREATE);
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

    @PostMapping("/commentCreate")
    public ResponseEntity<HttpResponseBody<?>> commentCreate(@RequestBody CommentCreateRequestDto dto) {
        long id = commentService.commentCreate(dto, "test1");

        return new ResponseEntity<>(new HttpResponseBody<>("OK", "" + id), HttpStatus.OK);
    }

    @PostMapping("/commentEdit")
    public ResponseEntity<HttpResponseBody<?>> commentEdit(@RequestBody CommentModifyRequestDto dto) {
        long id = commentService.modify(dto, "test1");

        return new ResponseEntity<>(new HttpResponseBody<>("OK", "" + id), HttpStatus.OK);
    }

    @PostMapping("/commentDelete")
    public ResponseEntity<HttpResponseBody<?>> commenDelete(@RequestBody CommentDeleteRequestDto dto) {
        commentService.delete(dto, "test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", new String("게시글 삭제 성공")), HttpStatus.OK);
    }

    @GetMapping("/detail/{id:[\\d]+}")
    public ResponseEntity<HttpResponseBody<?>> getDetailBoard(@PathVariable("id") long id) {
        BoardDetailResponseDto responseDto = boardService.getDetail(id);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", responseDto), HttpStatus.OK);
    }

}
