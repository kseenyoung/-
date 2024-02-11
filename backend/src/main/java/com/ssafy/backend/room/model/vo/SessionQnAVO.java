package com.ssafy.backend.room.model.vo;

import lombok.Getter;
import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;

@Getter
public class SessionQnAVO {
    private List<AnswerVO> answerVOList;
    private List<QuestionVO> questionVOList;

    public SessionQnAVO() {
    }

    public SessionQnAVO(List<AnswerVO> answerVOList, List<QuestionVO> questionVOList) {
        this.answerVOList = answerVOList;
        this.questionVOList = questionVOList;
    }
}
