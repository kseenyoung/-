package com.ssafy.backend.board.service;

import com.ssafy.backend.board.model.dto.CommentCreateRequestDTO;
import com.ssafy.backend.board.model.dto.CommentDeleteRequestDTO;
import com.ssafy.backend.board.model.dto.CommentModifyRequestDTO;

public interface CommentService {
    long addComment(CommentCreateRequestDTO dto, String userId);
    long modifyComment(CommentModifyRequestDTO dto, String userId);
    void deleteComment(CommentDeleteRequestDTO dto, String userId);
}
