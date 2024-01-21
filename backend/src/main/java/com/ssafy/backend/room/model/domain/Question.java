package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer questionId;
    String session;
    String question;

}
