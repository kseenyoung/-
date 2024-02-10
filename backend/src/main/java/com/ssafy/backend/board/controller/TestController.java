package com.ssafy.backend.board.controller;


import com.ssafy.backend.board.model.dto.*;
import com.ssafy.backend.board.model.vo.BoardDetailVO;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.CommentService;
import com.ssafy.backend.board.service.BoardTagService;
import com.ssafy.backend.common.response.BaseResponse;
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
    private final BoardTagService tagService;
    private final CommentService commentService;

    @PostMapping("/tagCreate")
    public BaseResponse<?> Tagcreate(@RequestBody TagCreateRequestDTO dto){
        //when 원래 dto 말고 httpSession에서 관리자인지 체크해야됨
        //then
        int idx = tagService.addTag(dto);
        return new BaseResponse<>(SUCCESS_TAG_CREATE);
    }

    @PostMapping("/boardCreate")
    public ResponseEntity<HttpResponseBody<?>> boardCreate(@RequestBody BoardAddRequestDTO dto) {
        boardService.addBoard(dto,"test1");
        HttpResponseBody<String> responseBody = new HttpResponseBody<>("OK","성공");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
    @PostMapping("/boardDelete")
    public ResponseEntity<HttpResponseBody<?>> boardDelete(@RequestBody BoardDeleteRequestDTO dto){
        boardService.deleteBoard(dto,"test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "성공"), HttpStatus.OK);
    }

    @PostMapping("/boardModify")
    public ResponseEntity<HttpResponseBody<?>> boardModify(@RequestBody BoardModifyRequestDTO dto){
        boardService.modifyBoard(dto,"test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", "성공"), HttpStatus.OK);
    }

    @GetMapping("/getList")
    public ResponseEntity<HttpResponseBody<?>> boardGetList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword)
    {
        boardService.getBoardList(page, keyword);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", boardService.getBoardList(page, keyword)), HttpStatus.OK);
    }

    @PostMapping("/commentCreate")
    public ResponseEntity<HttpResponseBody<?>> commentCreate(@RequestBody CommentCreateRequestDTO dto) {
        long id = commentService.addComment(dto, "test1");

        return new ResponseEntity<>(new HttpResponseBody<>("OK", "" + id), HttpStatus.OK);
    }

    @PostMapping("/commentEdit")
    public ResponseEntity<HttpResponseBody<?>> commentEdit(@RequestBody CommentModifyRequestDTO dto) {
        long id = commentService.modifyComment(dto, "test1");

        return new ResponseEntity<>(new HttpResponseBody<>("OK", "" + id), HttpStatus.OK);
    }

    @PostMapping("/commentDelete")
    public ResponseEntity<HttpResponseBody<?>> commenDelete(@RequestBody CommentDeleteRequestDTO dto) {
        commentService.deleteComment(dto, "test1");
        return new ResponseEntity<>(new HttpResponseBody<>("OK", new String("게시글 삭제 성공")), HttpStatus.OK);
    }

    @GetMapping("/detail/{id:[\\d]+}")
    public ResponseEntity<HttpResponseBody<?>> getDetailBoard(@PathVariable("id") long id) {
        BoardDetailVO responseDto = boardService.getBoardDetail(id);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", responseDto), HttpStatus.OK);
    }

}
