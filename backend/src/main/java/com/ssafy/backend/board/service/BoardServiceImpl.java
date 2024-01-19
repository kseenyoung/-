package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Comment;
import com.ssafy.backend.board.model.domain.Tag;

import com.ssafy.backend.board.model.dto.*;

import com.ssafy.backend.board.model.repository.BoardRepository;
import com.ssafy.backend.board.model.repository.CommentRepository;
import com.ssafy.backend.board.model.repository.TagRepository;

import com.ssafy.backend.common.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;



    @Override
    public void boardCreate(BoardCreateRequestDto dto, String userId){
        Tag tag = tagRepository.findByTagId(dto.getTagId())
                .orElseThrow(() -> new MyException("없는 태그입니다.", HttpStatus.BAD_REQUEST));
        boardRepository.save(dto.toEntity(tag,userId));
    }

    @Override
    @Transactional
    public void delete(BoardDeleteRequestDto dto, String userId) {
        Board board = boardRepository.findByBoardIdAndUserId(dto.getBoardId(),userId)
                .orElseThrow(() ->new MyException("삭제할 글이 없습니다",HttpStatus.BAD_REQUEST));
        List<Comment> allByBoardId = commentRepository.findAllByBoardId(board);
        commentRepository.deleteAll(allByBoardId);
        boardRepository.delete(board);
    }

    @Override
    public void update(BoardModifyRequestDto dto, String userId) {
        Board board = boardRepository.findByBoardIdAndUserId(dto.getBoardId(),userId)
                .orElseThrow(() ->new MyException("수정할 글이 없습니다.",HttpStatus.BAD_REQUEST));
        Tag tag = tagRepository.findByTagId(dto.getTagId())
                .orElseThrow(() -> new MyException("없는 태그입니다.", HttpStatus.BAD_REQUEST));

        board.updateBoard(tag,dto.getBoardTitle(),dto.getBoardContent());
        boardRepository.save(board);

    }

    @Override
    public BoardListResponseDto getList(int page, String keyword) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        Page<Board> boards = boardRepository.findByBoardTitleContaining(keyword, pageable);

        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boards){
            boardDtoList.add(new BoardDto(board));
        }

        return BoardListResponseDto.builder()
                .totalPages(boards.getTotalPages())
                .boards(boardDtoList)
                .build();
    }

    //게시글 확인 후 존재하면 댓글 불러와서 보여주기
    @Override
    @Transactional(readOnly = true)
    public BoardDetailResponseDto getDetail(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new MyException("게시글이 존재하지 않습니다", HttpStatus.BAD_REQUEST));

        List<CommentDto> comments = new ArrayList<CommentDto>();
        List<Comment> commentEntities = commentRepository.findAllByBoardIdOrderByCommentIdDesc(board);
        for(Comment comment : commentEntities){
            comments.add(new CommentDto(comment));
        }
        BoardDto boardDto = new BoardDto(board);
        return new BoardDetailResponseDto(boardDto, comments);
    }
}
