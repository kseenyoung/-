package com.ssafy.backend.room.model.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Entity
public class Answer {
    @Id
    @Column(columnDefinition = "varchar(40)")
    private String answerId;

    @Column(columnDefinition = "varchar(40)")
    private String questionId;

    @Column(columnDefinition = "varchar(200)")
    private String answerContent;

    @Column(columnDefinition = "varchar(45)")
    private String session;

    @Column(columnDefinition = "varchar(20)")
    private String userId;

    @Column(columnDefinition = "varchar(200)")
    private String createdDate;

    @Column(columnDefinition = "varchar(200)")
    private String updatedDate;

    @Builder
    public Answer(String answerId, String questionId, String answerContent, String session, String userId) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerContent = answerContent;
        this.session = session;
        this.userId = userId;
    }

    public Answer() {
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
