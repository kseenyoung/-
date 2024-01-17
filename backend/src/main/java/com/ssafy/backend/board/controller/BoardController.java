package com.ssafy.backend.board.controller;

import com.ssafy.backend.board.dto.*;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.CommentService;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import com.ssafy.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.ssafy.backend.common.utils.BoardValidator.isNumeric;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<HttpResponseBody<?>> boardGetList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword)
    {
        boardService.getList(page, keyword);
        return new ResponseEntity<>(new HttpResponseBody<>("OK", boardService.getList(page, keyword)), HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> boardHideURL(@RequestBody Map<String, Object> body,
                                                       HttpServletRequest request) {
        String sign = (String) body.get("sign");

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MyException("로그인해주세요", HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("session");
        String userId = user.getUserId();
        //보드 생성
        if ("addPost".equals(sign)) {
            String boardTitle = (String) body.get("boardTitle");
            String boardContent = (String) body.get("boardContent");
            Object tagId = body.get("tagId");
            //tagId가 숫자일때
            if (tagId == null) throw new MyException("숫자입력 아니야", HttpStatus.BAD_REQUEST);
            if (isNumeric((String) tagId)) {
                BoardCreateRequestDto dto = BoardCreateRequestDto.builder()
                        .boardContent(boardContent)
                        .boardTitle(boardTitle)
                        .tagId((int) tagId).build();
                //dto 만들고 insert boardData
                boardService.boardCreate(dto, "test입니다");
                return new ResponseEntity<>(new HttpResponseBody<>("게시글 만들기 성공", ""), HttpStatus.OK);
            }
        } else if ("deletePost".equals(sign)) {
            String boardId = (String) body.get("boardId");
            if (boardId == null) throw new MyException("숫자입력 아니야", HttpStatus.BAD_REQUEST);
            if (isNumeric(boardId)) {
                BoardDeleteRequestDto dto = BoardDeleteRequestDto
                        .builder().boardId(Long.parseLong(boardId)).build();
                boardService.delete(dto, userId);
                return new ResponseEntity<>(new HttpResponseBody<>("게시글 삭제 성공", ""), HttpStatus.OK);
            }
        }
        else if ("modifyPost".equals(sign)) {
            String boardTitle = (String) body.get("boardTitle");
            String boardContent = (String) body.get("boardContent");
            String tagId = (String) body.get("tagId");
            String boardId = (String) body.get("boardId");
            //tagId가 숫자일때
            if (tagId == null) throw new MyException("태그 숫자 아니야", HttpStatus.BAD_REQUEST);
            if (boardId == null) throw new MyException("보드 숫자 아니야", HttpStatus.BAD_REQUEST);
            if (isNumeric(tagId) && isNumeric(boardId)) {
                BoardModifyRequestDto dto = BoardModifyRequestDto.builder()
                        .boardId(Long.parseLong(boardId))
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .build();
                boardService.update(dto, userId);
                return new ResponseEntity<>(new HttpResponseBody<>("게시글 수정 성공", ""), HttpStatus.OK);
            }
        }
        throw new MyException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/comment")
    public ResponseEntity<HttpResponseBody<?>> commentHideURL(@RequestBody Map<String, Object> body,
                                                       HttpServletRequest request) {
        String sign = (String) body.get("sign");
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MyException("로그인해주세요", HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("session");
        String userId = user.getUserId();
        //보드 생성
        if ("addComment".equals(sign)) {
            String comment = (String) body.get("comment");
            String boardId =(String) body.get("boardId");
            //boardId 가 숫자 일 때 
            if (boardId == null) throw new MyException("NULL", HttpStatus.BAD_REQUEST);
            if (isNumeric( boardId)) {
                //request dto
                CommentCreateRequestDto requestDto = CommentCreateRequestDto.builder()
                        .boardId(Long.parseLong(boardId))
                        .comment(comment).build();
                long commentId = commentService.commentCreate(requestDto, userId);

                //response dto
                CommentCreateResponseDto responseDto = CommentCreateResponseDto.builder()
                        .commentId(commentId)
                        .boardId(Long.parseLong(boardId))
                        .comment(comment).build();
                return new ResponseEntity<>(new HttpResponseBody<>("댓글 작성 성공", responseDto), HttpStatus.OK);
            }
        } else if ("modifyComment".equals(sign)) {
            String commentId = (String) body.get("commentId");
            String comment = (String) body.get("comment");
            String boardId =(String) body.get("boardId");

            if (boardId == null) throw new MyException("boardId NULL", HttpStatus.BAD_REQUEST);
            if (commentId == null) throw new MyException("commentId NULL", HttpStatus.BAD_REQUEST);
            if (isNumeric(boardId) && isNumeric(commentId)) {
                //request dto
                CommentModifyRequestDto dto = CommentModifyRequestDto.builder()
                        .boardId(Long.parseLong(boardId))
                        .comment(comment)
                        .commentId(Long.parseLong(commentId))
                        .build();
                //response dto
                long commentId2 = commentService.modify(dto, userId);
                CommentCreateResponseDto responseDto = CommentCreateResponseDto.builder()
                        .commentId(commentId2)
                        .boardId(Long.parseLong(boardId))
                        .comment(comment).build();
                return new ResponseEntity<>(new HttpResponseBody<>("댓글 수정 성공", responseDto), HttpStatus.OK);
            }
        }

        throw new MyException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }

}


