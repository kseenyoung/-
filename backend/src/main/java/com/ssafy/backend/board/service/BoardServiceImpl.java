package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.BoardComment;
import com.ssafy.backend.board.model.domain.BoardTag;

import com.ssafy.backend.board.model.dto.*;

import com.ssafy.backend.board.model.repository.BoardRepository;
import com.ssafy.backend.board.model.repository.CommentRepository;
import com.ssafy.backend.board.model.repository.TagRepository;

import com.ssafy.backend.board.model.vo.BoardDetailVO;
import com.ssafy.backend.board.model.vo.BoardListVO;
import com.ssafy.backend.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_FOUND_BOARD;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_FOUND_TAG;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;



    @Override
    public void addBoard(BoardAddRequestDTO dto, String userId){
        BoardTag boardTag = tagRepository.findByBoardTagId(dto.getTagId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_TAG));
        boardRepository.save(dto.toEntity(boardTag,userId));
    }

    @Override
    @Transactional
    public void deleteBoard(BoardDeleteRequestDTO dto, String userId) {
        Board board = boardRepository.findByBoardIdAndUserId(dto.getBoardId(),userId)
                .orElseThrow(() ->new BaseException(NOT_FOUND_BOARD));
        List<BoardComment> allByBoardId = commentRepository.findAllByBoardId(board);
        commentRepository.deleteAll(allByBoardId);
        boardRepository.delete(board);
    }

    @Override
    public void modifyBoard(BoardModifyRequestDTO dto, String userId) {
        Board board = boardRepository.findByBoardIdAndUserId(dto.getBoardId(),userId)
                .orElseThrow(() ->new BaseException(NOT_FOUND_BOARD));
        BoardTag boardTag = tagRepository.findByBoardTagId(dto.getTagId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_TAG));

        board.updateBoard(boardTag,dto.getBoardTitle(),dto.getBoardContent());
        boardRepository.save(board);

    }

    @Override
    public BoardListVO getBoardList(int page, String keyword) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 9,Sort.by(sorts));
        Page<Board> boards = boardRepository.findByBoardTitleContaining(keyword, pageable);

        List<BoardDTO> boardDtoList = new ArrayList<>();
        for (Board board : boards){
            boardDtoList.add(new BoardDTO(board));
        }

        return BoardListVO.builder()
                .totalPages(boards.getTotalPages())
                .boards(boardDtoList)
                .build();
    }

    //게시글 확인 후 존재하면 댓글 불러와서 보여주기
    @Override
    @Transactional(readOnly = true)
    public BoardDetailVO getBoardDetail(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        List<BoardComment> boardCommentEntities = commentRepository.findAllByBoardIdOrderByCommentIdDesc(board);
        for(BoardComment boardComment : boardCommentEntities){
            comments.add(new CommentDTO(boardComment));
        }
        BoardDTO boardDto = new BoardDTO(board);
        return new BoardDetailVO(boardDto, comments);
    }
}
