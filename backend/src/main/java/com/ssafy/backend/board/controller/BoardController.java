package com.ssafy.backend.board.controller;

import com.ssafy.backend.board.dto.BoardCreateRequestDto;
import com.ssafy.backend.board.service.BoardService;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.utils.HttpResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ssafy.backend.common.utils.BoardValidator.isNumeric;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping("")
    public ResponseEntity<HttpResponseBody<?>> hideURL(@RequestBody Map<String,Object> body){
        String sign = (String) body.get("sign");

        //보드 생성
        if("addPost".equals(sign)){
            String boardTitle =(String) body.get("boardTitle");
            String boardContent = (String) body.get("boardContent");
            Object tagId =  body.get("tagId");
            //tagId가 숫자일때
            if(tagId == null) throw new MyException("숫자입력 아니야", HttpStatus.BAD_REQUEST);
            if(isNumeric((String) tagId)){
                BoardCreateRequestDto dto = BoardCreateRequestDto.builder()
                        .boardContent(boardContent)
                        .boardTitle(boardTitle)
                        .tagId((int) tagId).build();
                //dto 만들고 insert boardData
                boardService.boardCreate(dto,"test입니다");
                return new ResponseEntity<>(new HttpResponseBody<>("게시글 만들기 성공",""),HttpStatus.OK);
            }
        }

        throw new MyException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }

}
