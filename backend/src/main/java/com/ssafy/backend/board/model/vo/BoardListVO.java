package com.ssafy.backend.board.model.vo;

import com.ssafy.backend.board.model.dto.BoardDTO;
import lombok.Builder;

import java.util.List;

public class BoardListVO {
    private List<BoardDTO> boards;
    private int totalPages;

    @Builder
    public BoardListVO(List<BoardDTO> boards, int totalPages) {
        this.boards = boards;
        this.totalPages = totalPages;
    }

    public List<BoardDTO> getBoards() { return boards; }
    public int getTotalPages() { return totalPages; }

    public void setBoards(List<BoardDTO> boards) {
        this.boards = boards;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
