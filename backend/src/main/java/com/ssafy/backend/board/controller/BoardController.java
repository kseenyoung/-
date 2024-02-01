package com.ssafy.backend.board.controller;


import com.ssafy.backend.board.model.dto.*;
import com.ssafy.backend.board.model.vo.BoardDetailVO;
import com.ssafy.backend.board.model.vo.BoardListVO;
import com.ssafy.backend.board.model.vo.CommentVO;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.CommentService;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;
import static com.ssafy.backend.common.utils.BoardValidator.isNumeric;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/detail/{id:[\\d]+}")
    public BaseResponse<?> getBoardDetail(@PathVariable("id") long id) {
        BoardDetailVO responseDTO = boardService.getBoardDetail(id);
        return new BaseResponse<>(responseDTO);
    }
    @GetMapping("/list")
    public BaseResponse<?> getBoardList(
            @RequestParam(value = "page", defaultValue = "0") int page
            ,@RequestParam(value = "keyword", defaultValue = "") String keyword)
    {
        BoardListVO boardList = boardService.getBoardList(page, keyword);
        return new BaseResponse<>(boardList);
    }
    @PostMapping("")
    public BaseResponse<?> boardHideURL(@RequestBody Map<String, Object> body,
                                                       HttpServletRequest request) {
        String sign = (String) body.get("sign");

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BaseException(BaseResponseStatus.NEED_LOGIN);
        }
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();
        //보드 생성
        switch (sign) {
            case "addBoard":
                String boardTitle = (String) body.get("boardTitle");
                String boardContent = (String) body.get("boardContent");
                Object tagId = body.get("tagId");
                //tagId가 숫자일때
                if (tagId == null) throw new BaseException(JSON_PROCESSING_ERROR);
                if (isNumeric((String) tagId)) {
                    BoardAddRequestDTO DTO = BoardAddRequestDTO.builder()
                            .boardContent(boardContent)
                            .boardTitle(boardTitle)
                            .tagId((int) tagId).build();
                    boardService.addBoard(DTO, userId);
                    return new BaseResponse<>(SUCCESS);
                }
                break;
            case "deletePost":
                String boardId = (String) body.get("boardId");
                if (boardId == null) throw new MyException("숫자입력 아니야", HttpStatus.BAD_REQUEST);
                if (isNumeric(boardId)) {
                    BoardDeleteRequestDTO dto = BoardDeleteRequestDTO
                            .builder().boardId(Long.parseLong(boardId)).build();
                    boardService.deleteBoard(dto, userId);
                    return new BaseResponse<>(SUCCESS);
                }
                break;
            case "modifyPost":
                boardTitle = (String) body.get("boardTitle");
                boardContent = (String) body.get("boardContent");
                tagId = body.get("tagId");
                boardId = (String) body.get("boardId");
                //tagId가 숫자일때
                if (tagId == null) throw new BaseException(JSON_PROCESSING_ERROR);
                if (boardId == null) throw new BaseException(JSON_PROCESSING_ERROR);
                if (isNumeric((String) tagId) && isNumeric(boardId)) {
                    BoardModifyRequestDTO dto = BoardModifyRequestDTO.builder()
                            .boardId(Long.parseLong(boardId))
                            .boardTitle(boardTitle)
                            .boardContent(boardContent)
                            .build();
                    boardService.modifyBoard(dto, userId);
                    return new BaseResponse<>(SUCCESS);
                }
                break;
        }
        throw new BaseException(NOT_MATCH_SIGN);
    }
    @PostMapping("/comment")
    public BaseResponse commentHideURL(@RequestBody Map<String, Object> body,
                                                       HttpServletRequest request) {
        String sign = (String) body.get("sign");
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BaseException(NEED_LOGIN);
        }
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        switch (sign){
            case "addComment":
                String comment = (String) body.get("comment");
                String boardId =(String) body.get("boardId");
                //boardId 가 숫자 일 때
                if (boardId == null) throw new BaseException(JSON_PARSING_ERROR);
                if (isNumeric( boardId)) {
                    //request dto
                    CommentCreateRequestDTO requestDTO = CommentCreateRequestDTO.builder()
                            .boardId(Long.parseLong(boardId))
                            .comment(comment).build();
                    long commentId = commentService.addComment(requestDTO, userId);

                    //response dto
                    CommentVO commentVO = CommentVO.builder()
                            .commentId(commentId)
                            .boardId(Long.parseLong(boardId))
                            .comment(comment).build();
                    return new BaseResponse<>(commentVO);
                }
                break;
            case "modifyComment":
                String commentId = (String) body.get("commentId");
                comment = (String) body.get("comment");
                boardId =(String) body.get("boardId");

                if (boardId == null) throw new BaseException(JSON_PARSING_ERROR);
                if (commentId == null) throw new BaseException(JSON_PARSING_ERROR);
                if (isNumeric(boardId) && isNumeric(commentId)) {
                    //request dto
                    CommentModifyRequestDTO dto = CommentModifyRequestDTO.builder()
                            .boardId(Long.parseLong(boardId))
                            .comment(comment)
                            .commentId(Long.parseLong(commentId))
                            .build();
                    //response dto
                    long responseCommentId = commentService.modifyComment(dto, userId);
                    CommentVO commentVO = CommentVO.builder()
                            .commentId(responseCommentId)
                            .boardId(Long.parseLong(boardId))
                            .comment(comment).build();
                    return new BaseResponse(commentVO);
                }
                break;
            case "deleteComment":
                commentId = (String) body.get("commentId");
                boardId =(String) body.get("boardId");
                if (boardId == null) throw new BaseException(JSON_PARSING_ERROR);
                if (commentId == null) throw new BaseException(JSON_PARSING_ERROR);
                if (isNumeric(boardId) && isNumeric(commentId)) {
                    CommentDeleteRequestDTO dto = CommentDeleteRequestDTO.builder()
                            .boardId(Long.parseLong(boardId))
                            .commentId(Long.parseLong(commentId))
                            .build();
                    commentService.deleteComment(dto, userId);
                    return new BaseResponse(SUCCESS);
                }

        }
        throw new BaseException(NOT_MATCH_SIGN);
    }

}


