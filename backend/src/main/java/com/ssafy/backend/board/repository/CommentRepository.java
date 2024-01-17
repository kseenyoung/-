package com.ssafy.backend.board.repository;


import com.ssafy.backend.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByCommentIdAndUserId(Long id, String userId);

}
