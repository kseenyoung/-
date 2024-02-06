package com.ssafy.backend.room.model.repository;


import com.ssafy.backend.room.model.domain.Question;
import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
