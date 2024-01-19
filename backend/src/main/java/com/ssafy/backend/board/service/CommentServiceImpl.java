package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Comment;
import com.ssafy.backend.board.model.dto.CommentCreateRequestDto;
import com.ssafy.backend.board.model.dto.CommentDeleteRequestDto;
import com.ssafy.backend.board.model.dto.CommentModifyRequestDto;
import com.ssafy.backend.board.model.repository.BoardRepository;
import com.ssafy.backend.board.model.repository.CommentRepository;
import com.ssafy.backend.common.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public long commentCreate(CommentCreateRequestDto dto,String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new MyException("존재하지 않는 글입니다.", HttpStatus.BAD_REQUEST));
        return commentRepository.save(dto.toEntity(board, userId)).getCommentId();

    }

    @Override
    public long modify(CommentModifyRequestDto dto, String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new MyException("존재하지 않는 글입니다.", HttpStatus.BAD_REQUEST));
        Comment comment = commentRepository.findByCommentIdAndUserId(dto.getCommentId(),userId)
                .orElseThrow(() -> new MyException("존재하지 않는 댓글입니다,", HttpStatus.BAD_REQUEST));
        comment.modifyComment(dto.getComment());
        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public void delete(CommentDeleteRequestDto dto, String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new MyException("존재하지 않는 글입니다.", HttpStatus.BAD_REQUEST));
        Comment comment = commentRepository.findByCommentIdAndUserId(dto.getCommentId(),userId)
                .orElseThrow(() -> new MyException("이미 삭제된 글입니다,", HttpStatus.BAD_REQUEST));
         commentRepository.delete(comment);
    }
}
