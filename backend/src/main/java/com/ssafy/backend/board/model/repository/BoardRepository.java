package com.ssafy.backend.board.model.repository;

import com.ssafy.backend.board.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByBoardIdAndUserId(Long boardId, String userId);
    Page<Board> findByBoardTitleContaining(String boardTitle, Pageable pageable);
}
