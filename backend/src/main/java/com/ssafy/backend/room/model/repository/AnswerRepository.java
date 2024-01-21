package com.ssafy.backend.room.model.repository;


import com.ssafy.backend.room.model.domain.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findAllByQuestionId(int questionId) throws Exception;
}
