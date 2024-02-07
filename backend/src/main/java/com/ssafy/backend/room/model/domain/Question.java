package com.ssafy.backend.room.model.domain;

import lombok.Builder;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Entity
public class Question {

    @Id
    String questionId;

    @Column(columnDefinition = "varchar(20)")
    String userId;

    @Column(columnDefinition = "varchar(45)")
    String session;

    @Column(columnDefinition = "varchar(100)")
    String questionContent;

    @Column(columnDefinition = "varchar(200)")
    String createdDate;

    @Column(columnDefinition = "varchar(200)")
    String updatedDate;

    @Builder
    public Question(String questionId, String session, String questionContent, String userId) {
        setQuestionId(questionId);
        setSession(session);
        setQuestionContent(questionContent);
        setUserId(userId);
    }

    public Question() {
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
