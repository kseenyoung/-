package com.ssafy.backend.board.model.dto;


import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.BoardTag;
import com.ssafy.backend.common.utils.BoardValidator;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import com.ssafy.backend.common.exception.MyException;

public class BoardAddRequestDTO {

    private String boardTitle;
    private String boardContent;
    private int tagId;


    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) throws MyException {
        if(!BoardValidator.isValidTitle(boardTitle))
            throw new MyException("잘못된 제목 요청", HttpStatus.BAD_REQUEST);
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) throws MyException{
        if(!BoardValidator.isValidContent(boardContent))
            throw new MyException("잘못된 내용 요청", HttpStatus.BAD_REQUEST);
        this.boardContent = boardContent;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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
    public BoardAddRequestDTO(String boardTitle, String boardContent, int tagId){
        setBoardTitle(boardTitle);
        setBoardContent(boardContent);
        setTagId(tagId);

    }
}
