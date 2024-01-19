package com.ssafy.backend.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//	"boardId" : 3,
//	"boardTitle":  "친구 구해요 ^^",
//	"boradContent":  "공부 잘하는 사람 댓글 달아라",
//	"boardTag":  "친구",
@Getter
@Setter
public class BoardModifyRequestDto {

    private long boardId;

    private String boardTitle;
    private String boardContent;
    private int tagId;


    public BoardModifyRequestDto(){

    }

    @Builder
    public BoardModifyRequestDto(long boardId, String boardTitle, String boardContent, int tagId) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.tagId = tagId;
    }
}
