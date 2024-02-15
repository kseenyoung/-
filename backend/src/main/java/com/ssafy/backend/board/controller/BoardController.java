package com.ssafy.backend.board.controller;


import com.ssafy.backend.board.model.dto.*;
import com.ssafy.backend.board.model.vo.BoardDetailVO;
import com.ssafy.backend.board.model.vo.BoardListVO;
import com.ssafy.backend.board.model.vo.CommentVO;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.board.service.BoardTagService;
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
    private final BoardTagService boardTagService;

    @GetMapping("/tag/list")
    public BaseResponse<?> getBoardTag(){
        return new BaseResponse<>(boardTagService.getTagList());
    }


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
                String tagId = String.valueOf(body.get("tagId"));
                BoardAddRequestDTO addRequestDTO = BoardAddRequestDTO.builder()
                        .boardContent(boardContent)
                        .boardTitle(boardTitle)
                        .tagId(tagId)
                        .build();
                boardService.addBoard(addRequestDTO, userId);
                return new BaseResponse<>(SUCCESS);
            case "deletePost":
                long boardId = Long.valueOf((int) body.get("boardId"));
                BoardDeleteRequestDTO dto = BoardDeleteRequestDTO
                        .builder()
                        .boardId(boardId).build();
                boardService.deleteBoard(dto, userId);
                    return new BaseResponse<>(SUCCESS);
            case "modifyPost":
                boardTitle = (String) body.get("boardTitle");
                boardContent = (String) body.get("boardContent");
                tagId =(String) body.get("tagId");
                boardId = Long.valueOf((int) body.get("boardId"));
                //tagId가 숫자일때
                BoardModifyRequestDTO modifyRequestDTO = BoardModifyRequestDTO.builder()
                        .boardId((boardId))
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .tagId(tagId)
                        .build();
                boardService.modifyBoard(modifyRequestDTO, userId);
                return new BaseResponse<>(SUCCESS);
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
                long boardId =Long.valueOf((int) body.get("boardId"));
                //boardId 가 숫자 일 때
                //request dto
                CommentCreateRequestDTO requestDTO = CommentCreateRequestDTO.builder()
                        .boardId(boardId)
                        .comment(comment).build();
                long commentId = commentService.addComment(requestDTO, userId);

                //response dto
                CommentVO commentVO = CommentVO.builder()
                        .commentId(commentId)
                        .boardId(boardId)
                        .comment(comment).build();
                return new BaseResponse<>(commentVO);
            case "modifyComment":
                commentId = Long.valueOf((int) body.get("commentId"));
                comment = (String) body.get("comment");
                boardId =Long.valueOf((int) body.get("boardId"));

                //request dto
                CommentModifyRequestDTO dto = CommentModifyRequestDTO.builder()
                        .boardId(boardId)
                        .comment(comment)
                        .commentId(commentId)
                        .build();
                //response dto
                long responseCommentId = commentService.modifyComment(dto, userId);
                commentVO = CommentVO.builder()
                        .commentId(responseCommentId)
                        .boardId(boardId)
                        .comment(comment)
                        .build();
                return new BaseResponse(commentVO);
            case "deleteComment":
                commentId = Long.valueOf((int) body.get("commentId"));
                boardId = Long.valueOf((int) body.get("boardId"));
                CommentDeleteRequestDTO commentDeleteRequestDTO= CommentDeleteRequestDTO.builder()
                        .boardId(boardId)
                        .commentId(commentId)
                        .build();
                commentService.deleteComment(commentDeleteRequestDTO, userId);
                return new BaseResponse(SUCCESS);
        }
        throw new BaseException(NOT_MATCH_SIGN);
    }


}


