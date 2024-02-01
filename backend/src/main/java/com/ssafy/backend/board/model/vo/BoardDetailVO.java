package com.ssafy.backend.board.model.vo;

import com.ssafy.backend.board.model.dto.BoardDTO;
import com.ssafy.backend.board.model.dto.CommentDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardDetailVO {
    BoardDTO board;
    List<CommentDTO> comments;

    @Builder
    public BoardDetailVO(BoardDTO board, List<CommentDTO> comments) {
        this.board = board;
        this.comments = comments;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
