package com.ssafy.backend.board.model.dto;


import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.BoardTag;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.utils.BoardValidator;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import com.ssafy.backend.common.exception.MyException;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

public class BoardAddRequestDTO {

    private String boardTitle;
    private String boardContent;
    private Integer tagId;


    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) throws MyException {
        if(!BoardValidator.isValidTitle(boardTitle))
            throw new BaseException(INVALIDATE_TITLE);
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) throws MyException{
        if(!BoardValidator.isValidContent(boardContent))
            throw new BaseException(INVALIDATE_CONTENT);
        this.boardContent = boardContent;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        if(tagId == null || "".equals(tagId)){
            throw new BaseException(INVALIDATE_TAG);
        }
        this.tagId = Integer.parseInt(tagId);
    }

    public Board toEntity(BoardTag boardTag, String userId) {
        return Board.builder()
                .boardContent(boardContent)
                .boardTitle(boardTitle)
                .boardTagId(boardTag)
                .userId(userId)
                .build();
    }

    public BoardAddRequestDTO(){

    }
    @Builder
    public BoardAddRequestDTO(String boardTitle, String boardContent, String tagId){
        setBoardTitle(boardTitle);
        setBoardContent(boardContent);
        setTagId(tagId);
    }
}
