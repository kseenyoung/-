package com.ssafy.backend.room.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@RedisHash("answer")
public class Answer {
    @Id
    String answerId;

    @Indexed
    String questionId;
    String answer;
    String session;
    String userId;
}
