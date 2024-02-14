package com.ssafy.backend.room.model.vo;

import lombok.Getter;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Getter
public class StudyRoomVO implements Comparable<StudyRoomVO>{
    String name;
    int count;

    public StudyRoomVO(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public StudyRoomVO() {
    }


    @Override
    public int compareTo(StudyRoomVO o) {
        return o.count-this.count;
    }
}
