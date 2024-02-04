package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.Comment;
import com.ssafy.backend.board.model.dto.CommentCreateRequestDTO;
import com.ssafy.backend.board.model.dto.CommentDeleteRequestDTO;
import com.ssafy.backend.board.model.dto.CommentModifyRequestDTO;
import com.ssafy.backend.board.model.repository.BoardRepository;
import com.ssafy.backend.board.model.repository.CommentRepository;
import com.ssafy.backend.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_FOUND_BOARD;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_FOUND_COMMENT;

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
    public long addComment(CommentCreateRequestDTO dto, String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        return commentRepository.save(dto.toEntity(board, userId)).getCommentId();

    }

    @Override
    public long modifyComment(CommentModifyRequestDTO dto, String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        Comment comment = commentRepository.findByCommentIdAndUserId(dto.getCommentId(),userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
        comment.modifyComment(dto.getComment());
        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public void deleteComment(CommentDeleteRequestDTO dto, String userId) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        Comment comment = commentRepository.findByCommentIdAndUserId(dto.getCommentId(),userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
         commentRepository.delete(comment);
    }
}
