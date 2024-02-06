package com.ssafy.backend.board.model.repository;

import com.ssafy.backend.board.model.domain.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<BoardTag,Integer> {
    Optional<BoardTag> findByBoardTagId(int id);
}
