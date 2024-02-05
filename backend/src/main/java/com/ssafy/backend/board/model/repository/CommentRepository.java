package com.ssafy.backend.board.model.repository;


import com.ssafy.backend.board.model.domain.Board;
import com.ssafy.backend.board.model.domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<BoardComment,Long> {
    Optional<BoardComment> findByCommentIdAndUserId(Long id, String userId);
    List<BoardComment> findAllByBoardIdOrderByCommentIdDesc(Board board);

    List<BoardComment> findAllByBoardId(Board board);
}
