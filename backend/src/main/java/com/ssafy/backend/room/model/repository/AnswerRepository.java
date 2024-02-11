package com.ssafy.backend.room.model.repository;


import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
}
