package com.ssafy.backend.room.model.repository.redis;


import com.ssafy.backend.room.model.domain.redis.QuestionRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRedisRepository extends CrudRepository<QuestionRedis, String> {

    @Override
    Optional<QuestionRedis> findById(String questionId);
}
