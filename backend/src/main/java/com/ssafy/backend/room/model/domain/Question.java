package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@RedisHash("question")
public class Question {
    @Id
    String questionId;
    String session;
    String question;
    String userId;
}
