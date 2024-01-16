package com.ssafy.backend.board.repository;

import com.ssafy.backend.board.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
    Optional<Tag> findByTagId(int id);
}
