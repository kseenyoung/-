package com.ssafy.backend.room.model.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class UserQnAVO {
    private List<AnswerVO> answerVOList;
    private List<QuestionVO> questionVOList;

    public UserQnAVO() {
    }

    public UserQnAVO(List<AnswerVO> answerVOList, List<QuestionVO> questionVOList) {
        this.answerVOList = answerVOList;
        this.questionVOList = questionVOList;
    }
}
