package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;

//	"boardId" : 3,
//	"boardTitle":  "친구 구해요 ^^",
//	"boradContent":  "공부 잘하는 사람 댓글 달아라",
//	"boardTag":  "친구",
@Getter
public class BoardModifyRequestDTO {

    private long boardId;

    private String boardTitle;
    private String boardContent;
    private Integer tagId;


    public BoardModifyRequestDTO(){

    }

    @Builder
    public BoardModifyRequestDTO(long boardId, String boardTitle, String boardContent, String tagId) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        setTagId(tagId);
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public void setTagId(String tagId) {

        this.tagId = Integer.parseInt(tagId);
    }
}
