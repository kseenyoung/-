package com.ssafy.backend.room.model.repository.redis;


import com.ssafy.backend.room.model.domain.redis.AnswerRedis;
import com.ssafy.backend.room.model.domain.redis.StudyRoomRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRoomRedisRepository extends CrudRepository<StudyRoomRedis, String> {
    StudyRoomRedis findByName(String name);
}
