package com.ssafy.backend.board.repository;

import com.ssafy.backend.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByBoardIdAndUserId(Long boardId, String userId);
}
