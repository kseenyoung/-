package com.ssafy.backend.room.model.repository.redis;


import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRedisRepository extends CrudRepository<AnswerRedis, String> {
    List<AnswerRedis> findByQuestionId(String questionId) throws Exception;

}
