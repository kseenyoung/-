package com.ssafy.backend.room.model.domain.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@RedisHash("studyRoom")
public class StudyRoomRedis {
    @Id
    String studyRoomId;

    @Indexed
    String name;
    int count;

    public StudyRoomRedis(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public StudyRoomRedis() {
    }

    public void setStudyRoomId(String studyRoomId) {
        this.studyRoomId = studyRoomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
