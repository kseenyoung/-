package com.ssafy.backend.user.model.repository.redis;


import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import com.ssafy.backend.user.model.domain.redis.LoginRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRedisRepository extends CrudRepository<LoginRedis, String> {
    LoginRedis findByUserId(String userId);
}
