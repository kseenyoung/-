package com.ssafy.backend.room.model.repository.redis;


import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRedisRepository extends CrudRepository<QuestionRedis, Integer> {
    QuestionRedis findById(String questionId);
}
