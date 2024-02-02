package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.dto.*;
import com.ssafy.backend.board.model.vo.BoardDetailVO;
import com.ssafy.backend.board.model.vo.BoardListVO;


public interface BoardService {
    void addBoard(BoardAddRequestDTO dto, String userId);

    void deleteBoard(BoardDeleteRequestDTO dto, String userId);

    void modifyBoard(BoardModifyRequestDTO dto, String userId);

    BoardListVO getBoardList(int page, String keyword);

    BoardDetailVO getBoardDetail(long id);
}
