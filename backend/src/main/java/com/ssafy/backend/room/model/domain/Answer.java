package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer answerId;
    String sessionName;
    Integer questionId;
    String answer;
}
